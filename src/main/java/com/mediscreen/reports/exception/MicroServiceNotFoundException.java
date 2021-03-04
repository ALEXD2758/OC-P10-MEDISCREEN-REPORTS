package com.mediscreen.reports.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MicroServiceNotFoundException extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(MicroServiceNotFoundException.class);

    public MicroServiceNotFoundException() {
        super("Microservices are not running");
        logger.error("Microservices are not running");
    }
}