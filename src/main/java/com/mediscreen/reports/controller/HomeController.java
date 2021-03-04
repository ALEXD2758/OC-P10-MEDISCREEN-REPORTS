package com.mediscreen.reports.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Return the ModelAndView home
     *
     * @param model Model Interface
     * @return a string to the address "home", returning the associated view
     */
    @GetMapping("/")
    public String home(Model model)	{
        logger.info("GET / (home) : OK");
        return "home";
    }
}