package com.flea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by bilgi on 5/21/2015.
 */
@Controller
public class IndexController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping("/welcome")
    public ModelAndView doWelcome() {
        return new ModelAndView("welcome");
    }

    @RequestMapping("/")
    public ModelAndView doIndex(Model model, HttpServletRequest request) {
        Set<String> resourcePaths = servletContext.getResourcePaths("/resources/img/backgrounds");

        if (resourcePaths.size() > 0) {
            Random rand = new Random();
            int randomNum = rand.nextInt((resourcePaths.size() - 1) + 1);
            model.addAttribute("background", resourcePaths.toArray()[randomNum]);
        }

        model.addAttribute("locale_selections", Locale.getAvailableLocales());
        model.addAttribute("locale", request.getLocale());
        return new ModelAndView("index");
    }
}
