package com.mediscreen.reports.controller;

import com.mediscreen.reports.exception.MicroServiceNotFoundException;
import com.mediscreen.reports.exception.PatientIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * Exception handler method for MicroServiceNotFoundException
     * @param req HttpServletRequest
     * @param ex the exception
     * @param model the model interface
     * @return a string of the view "error"
     */
    @ExceptionHandler({MicroServiceNotFoundException.class})
    public String handleError(HttpServletRequest req, Exception ex, Model model) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex.getCause());
        model.addAttribute("url", req.getRequestURL());
        return "error";
    }

    /**
     * Exception handler method for PatientIdNotFoundException
     * @param req HttpServletRequest
     * @param ex the exception
     * @param model the model interface
     * @return a string of the view "error"
     */
    @ExceptionHandler({PatientIdNotFoundException.class})
    public String handleErrorNotFoundException(HttpServletRequest req, Exception ex, Model model) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        model.addAttribute("exception", ex.getCause());
        model.addAttribute("url", req.getRequestURL());
        return "error";
    }
}