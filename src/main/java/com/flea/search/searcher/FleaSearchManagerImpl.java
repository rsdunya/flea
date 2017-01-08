package com.flea.search.searcher;

import com.flea.search.SearchResult;
import com.flea.search.SearchResults;
import com.tinkerpop.blueprints.Vertex;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by bilgi on 5/28/2015.
 */
public class FleaSearchManagerImpl implements FleaSearchManager {

    @Autowired
    FleaIndexSearcher indexSearcher;

    @Autowired
    FleaGraphSearcher graphSearcher;

    @Override
    public SearchResults search(String query, String language, int page, int maxResults) {
        SearchResults indexerResults = indexSearcher.search(query, language, page, maxResults);

        for (SearchResult searchResult : indexerResults.getSearchResults()) {
            Vertex vertex = graphSearcher.search(searchResult.getId());

        }

        return null;
    }

}
