package com.flea.crawler;

import com.flea.crawler.language.FleaLanguageDetector;
import com.flea.search.indexer.ElasticIndexer;
import com.flea.search.indexer.FleaIndexer;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by bilgi on 5/21/2015.
 */
public class FleaCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    FleaIndexer indexer;

    FleaLanguageDetector languageDetector;
    {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("fleaCrawlerContext.xml");
        languageDetector = (FleaLanguageDetector)context.getBean("fleaLanguageDetector");
        indexer = (FleaIndexer)context.getBean("fleaIndexer");
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return true;
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        indexDocument(page);
        logger.debug("=============");
    }

    private Map<String, Object> indexDocument(Page page){
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);

        Map<String, Object> doc = new HashMap<String,Object>();
        doc.put("url", url);
        doc.put("domain", domain);
        doc.put("path", path);
        doc.put("subDomain",subDomain);
        doc.put("parentUrl", parentUrl);
        doc.put("anchor", anchor);
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());
            doc.put("language",languageDetector.detectLanguage(htmlParseData.getText()));
            doc.put("body", htmlParseData.getText());
            doc.put("links", htmlParseData.getOutgoingUrls());
            doc.put("title", buildTitle(htmlParseData));
        }

        indexer.putIndex(doc);
        return doc;
    }

    private String buildTitle(HtmlParseData htmlParseData){
        StringBuilder title = new StringBuilder(htmlParseData.getTitle().trim());
        // TODO smart analyze desc maybe to include in search link
//        if(htmlParseData.getMetaTags() != null && htmlParseData.getMetaTags().size()>0 && htmlParseData.getMetaTags().containsKey("description")){
//            title.append(" - " + (String)htmlParseData.getMetaTags().get("description"));
//        }

        return title.toString();
    }
}

