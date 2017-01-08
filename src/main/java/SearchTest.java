import com.flea.search.indexer.ElasticIndexer;
import com.flea.search.searcher.ElasticIndexSearcher;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created by bilgi on 5/21/2015.
 */
public class SearchTest {
    public static void main(String[] args){
        final Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        new ElasticIndexSearcher().searchDocument(client, ElasticIndexer.SITE_PAGES_INDEX, null, null, "conduit", 1, 10);
    }

}
