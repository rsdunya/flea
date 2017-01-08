package com.flea.search.indexer;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.exists.ExistsRequest;
import org.elasticsearch.action.exists.ExistsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bilgi on 5/20/2015.
 */
public class ElasticIndexer implements FleaIndexer{

    {
        createIndexIfNotExists(SITE_PAGES_INDEX);
    }

    public static void main(String[] args){
        new ElasticIndexer().testPutIndex();
    }

    public static final String SITE_PAGES_INDEX = "idx_site_pages";

    //TODO @Test
    public void testPutIndex(){
        try {
            final XContentBuilder builder = jsonBuilder().prettyPrint()
                    .startObject()
                    .startObject("web_page")
                    .startObject("document")
                    .field("language", "en")
                    .endObject()
                    .endObject()
                    .endObject();
            putIndex(builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putIndex(String doc){
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        IndexRequest indexRequest = new IndexRequest(SITE_PAGES_INDEX,"web_page", null);
        indexRequest.source(doc);
        IndexResponse response = client.index(indexRequest).actionGet();
        client.close();
    }

    public void putIndex(Map<String, Object> unprocessedDoc){
        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        IndexRequest indexRequest = new IndexRequest(SITE_PAGES_INDEX,"article", null);
        Iterator<Map.Entry<String, Object>> iterator = unprocessedDoc.entrySet().iterator();
        String doc;

        try {
            final XContentBuilder builder = jsonBuilder().prettyPrint()
                    .startObject()
                    .startObject("web_page")
                    .startObject("document");
            while(iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                builder.field(entry.getKey(), entry.getValue());
            }
            builder.endObject().endObject().endObject();
            indexRequest.source(builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.index(indexRequest).actionGet();
        client.close();
    }

    public void createIndexIfNotExists(final String indexName){
        final ExistsRequest request = new ExistsRequest(indexName);
        final Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        ActionListener<ExistsResponse> actionListener = new ActionListener<ExistsResponse>() {
            boolean result;
            @Override
            public void onResponse(ExistsResponse existsResponse) {
                if(existsResponse.exists()){
                    result=true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                createIndex(client,indexName);
            }
        };
        client.exists(request, actionListener);
    }

    public void createIndex(Client client, String indexName){
        client.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();

        try {
            PutMappingResponse putMappingResponse = client.admin().indices()
                    .preparePutMapping(indexName)
                    .setType("web_page")
                    .setSource(jsonBuilder().prettyPrint()
                            .startObject()
                            .startObject("web_page")
                            .startObject("properties")
                            .startObject("document")
                            .field("type", "string")
                            .field("title", "string")
                            .field("language", "not_analyzed")
                            .field("url", "string")
                            .field("parentUrl", "string")
                            .field("domain", "string")
                            .field("subDomain", "string")
                            .field("domain", "string")
                            .field("path", "string")
                            .field("links", "string")
                            .field("attachments", "string")
                            .field("body", "string")
                            .endObject()
                            .endObject()
                            .endObject()
                            .endObject())
                    .execute().actionGet();

            IndexResponse response1 = client.prepareIndex(indexName, "indextype")
                    .setSource(putMappingResponse)
                    .execute()
                    .actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
