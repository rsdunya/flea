package com.flea.search.searcher;

import com.flea.search.SearchResult;
import com.flea.search.SearchResults;
import com.flea.search.indexer.ElasticIndexer;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bilgi on 5/21/2015.
 */
public class ElasticIndexSearcher implements FleaIndexSearcher {

    public SearchResults searchDocument(Client client, String index, String type,
                                             String query, String language, int page, int maxResults) {

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.multiMatchQuery(query, "body", "title"));
        if(language != null && !language.isEmpty()) {
            queryBuilder.should(QueryBuilders.multiMatchQuery(language, "language").field("language", 2.0f));
        }

        SearchResponse response = client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setExplain(true)
                .setFrom(page).setSize(maxResults).setExplain(true).addHighlightedField("body")
                .execute()
                .actionGet();

        SearchHit[] results = response.getHits().getHits();

        for (SearchHit hit : results) {
            Map<String, Object> result = hit.getSource();
        }
        SearchResults searchResults = new SearchResults(buildSearchResult(results));
        searchResults.setTotalHits(response.getHits().totalHits());
        return searchResults;
    }

    private List<SearchResult> buildSearchResult(SearchHit[] results) {
        List<SearchResult> searchResults = new ArrayList<SearchResult>();
        for (SearchHit hit : results) {
            SearchResult result = new SearchResult((Map<String,Object>)((Map<String, Object>)hit.sourceAsMap().get("web_page")).get("document"));
            result.setId(hit.getId());
            if (hit.getHighlightFields() != null && hit.getHighlightFields().size() > 0) {
                StringBuilder sb = new StringBuilder();
                Text[] fragments = hit.getHighlightFields().get("body").getFragments();
                for (int i = 0; i < fragments.length; ++i) {
                    sb.append(fragments[i].toString());
                }
                result.setPreview(sb.toString());
            }
            searchResults.add(result);
        }
        return searchResults;
    }

    @Override
    public SearchResults search(String query, String language, int page, int maxResults) {
        final Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        return  searchDocument(client, ElasticIndexer.SITE_PAGES_INDEX, null, query, language, page, maxResults);
    }
}
