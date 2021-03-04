package com.mediscreen.reports.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatientIdNotFoundException extends Throwable {

    private final Logger logger = LoggerFactory.getLogger(PatientIdNotFoundException.class);

    public PatientIdNotFoundException(int patientId) {
        super("Patient ID not found : " + patientId);
        logger.error("Patient ID not found : " + patientId);
    }
}