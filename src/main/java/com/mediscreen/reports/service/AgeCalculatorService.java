package com.mediscreen.reports.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class AgeCalculatorService {

    /**
     * Function used for calculating the age of a patient according to the birthdate (originally with joda.time library
     * in patients microservice)
     * Create a DateTimeFormatter of "yyyy-MM-d" pattern to be able to parse through the String of birthdate
     * with that specific formatter
     * DO NOT change format to "yyyy-MM-dd" as it will generate an error of date parsing from the "day"
     *
     * @param birthdate java time LocalDate annotated as such "@DateTimeFormat(pattern = "yyyy-MM-d")"
     * @return int age calculated if birthdate is not null, else int 0
     */
    public int agePatient (String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        if (birthdate != null) {
            LocalDate birthday = LocalDate.parse(birthdate, formatter);
            LocalDate today = LocalDate.now(); //Today's date

            int age = Period.between(birthday, today).getYears();
            return age;
        }
        else {
            return 0;
        }
    }
}