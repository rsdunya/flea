package com.flea.controller;

import com.flea.search.SearchResults;
import com.flea.search.searcher.FleaIndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by bilgi on 5/25/2015.
 */
@Controller
public class SearchController {

    @Autowired
    FleaIndexSearcher searcher;

    int defaultPageSize = 20;
    int defaultPage = 1;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView doSearch(Model model, @RequestParam(value = "q") String query,
                                 @RequestParam(value = "locale", required = false) String locale,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                 HttpServletRequest request) {
        pageSize = initPageSize(pageSize);
        page = initPage(page);
        SearchResults searchResults = searcher.search(query, locale, (page-1), pageSize);
        model.addAttribute("locale_selections", Locale.getAvailableLocales());
        model.addAttribute("locale", request.getLocale());
        model.addAttribute("searchResults", searchResults.getSearchResults());
        model.addAttribute("totalHits", searchResults.getTotalHits());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", calculateTotalPages(searchResults.getTotalHits(), pageSize));
        model.addAttribute("pagerLinks", createPageableUrl(request, searchResults.getTotalHits(), pageSize));
        return new ModelAndView("search_results");
    }

    private List<String> createPageableUrl(HttpServletRequest request, long totalHits, Integer pageSize) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL());
        Map map = new LinkedHashMap(request.getParameterMap());
        if(map.size() > 0){
            requestURL.append("?");
        }

        String pageHash = UUID.randomUUID().toString();
        if(map.get("page") != null){
            map.remove("page");
        }
        map.put(pageHash, new String[]{pageHash +"_val"});
        for (Object key: map.keySet())
        {
            requestURL.append("&" + key + "=" + Arrays.toString((String[]) map.get(key)).replaceAll("\\[", "").replaceAll("]", ""));
        }

        long pages = calculateTotalPages(totalHits, pageSize);
        List<String> pagerLinks = new LinkedList<>();
        if(totalHits > 0){
            for(int i=1; i < pages+1;i++){
                pagerLinks.add(requestURL.toString().replaceAll(pageHash + "=" + pageHash + "_val", "page=" + i));
            }
        }

        return pagerLinks;
    }

    private long calculateTotalPages(Long totalHits, Integer pageSize) {
        if(totalHits == null){
            return 0;
        }
        pageSize = initPageSize(pageSize);

        return totalHits > pageSize? totalHits/pageSize: 0;
    }

    private int initPageSize(Integer pageSize){
        if(pageSize == null || pageSize <= 0){
            return defaultPageSize;
        }
        return pageSize > 50?defaultPageSize:pageSize;
    }

    private int initPage(Integer page){
        if(page == null || page < 1){
            return defaultPage;
        }
        return page> 50?20:page;
    }
}
