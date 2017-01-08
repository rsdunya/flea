package com.flea.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilgi on 5/25/2015.
 */
public class SearchResults {
    List<SearchResult> searchResults = new ArrayList<>();

    long totalHits;

    public SearchResults(List<SearchResult> results){
        searchResults.addAll(results);
    }

    public long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> results) {
        this.searchResults = results;
    }
}
