package com.mediscreen.reports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Return the ModelAndView home
     *
     * @param model Model Interface
     * @return a string to the address "home", returning the associated view
     */
    @GetMapping("/")
    public String home(Model model)	{
        return "home";
    }
}