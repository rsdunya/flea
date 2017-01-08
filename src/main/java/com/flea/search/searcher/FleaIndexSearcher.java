package com.flea.search.searcher;

import com.flea.search.SearchResult;
import com.flea.search.SearchResults;

import java.util.List;

/**
 * Created by bilgi on 5/21/2015.
 */
public interface FleaIndexSearcher {
    public SearchResults search(String query, String language, int page, int maxResults);

}
