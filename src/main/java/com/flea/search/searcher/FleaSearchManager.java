package com.flea.search.searcher;

import com.flea.search.SearchResults;

/**
 * Created by bilgi on 5/28/2015.
 */
public interface FleaSearchManager {
    public SearchResults search(String query, String language, int page, int maxResults);
}
