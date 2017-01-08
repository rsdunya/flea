package com.flea.search.searcher;

import com.tinkerpop.blueprints.Vertex;

import java.util.List;

/**
 * Created by bilgi on 5/28/2015.
 */
public interface FleaGraphSearcher {

    public Vertex search(String docIndexId);
}
