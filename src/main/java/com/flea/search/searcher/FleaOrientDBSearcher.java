package com.flea.search.searcher;

import com.flea.search.SearchResult;
import com.flea.search.SearchResults;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.db.ODatabaseFactory;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.List;

/**
 * Created by bilgi on 5/28/2015.
 */
public class FleaOrientDBSearcher implements FleaGraphSearcher {

    @Override
    public Vertex search(String docIndexId) {
        TransactionalGraph graph = new OrientGraph("remote:localhost/flea", "admin", "admin");
        Iterable<Vertex> vertices = (Iterable<Vertex>)((OrientGraph) graph).command(new OCommandSQL("SELECT FROM FleaWebVertex WHERE indexDocId = 'AU2UBdFop0YELwoZ6zHd'")).execute();
        for(Vertex v: vertices){
            for (String s : v.getPropertyKeys()) {
                System.out.println(s + ": " + v.getProperty(s));
            }
        }

        return null;
    }

    public static void main(String[] args){
        new FleaOrientDBSearcher().search("");
    }


    public SearchResults sortByRank(SearchResults preRankedResults){
        TransactionalGraph graph = new OrientGraph("remote:localhost/flea", "admin", "admin");
        for(SearchResult sr: preRankedResults.getSearchResults()){

        }

        return null;
    }
}
