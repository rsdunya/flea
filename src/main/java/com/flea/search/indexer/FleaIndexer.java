package com.flea.search.indexer;

import java.util.Map;

/**
 * Created by bilgi on 5/23/2015.
 */
public interface FleaIndexer {

    public void putIndex(String doc);
    public void putIndex(Map<String, Object> doc);
}
