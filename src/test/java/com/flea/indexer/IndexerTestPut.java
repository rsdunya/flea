package com.flea.indexer;

import com.flea.crawler.FleaCrawler;
import com.flea.crawler.language.FleaLanguageDetector;
import com.flea.search.indexer.FleaIndexer;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by bilgi on 7/30/2015.
 */
public class IndexerTestPut {

    FleaCrawler fleaCrawler = new FleaCrawler();

    @Test
    public void testPutIndex(){
        String path = new File(Paths.get("").toAbsolutePath().toString() +"/src/test/resources/html/").toString() +"/";
        for (String s : new File(Paths.get("").toAbsolutePath().toString() +"/src/test/resources/html/").list()) {
            WebURL webUrl = new WebURL();
            webUrl.setURL(path + s);
            Page p = new Page(webUrl);
            fleaCrawler.visit(p);
        }

    }
}
