package com.mediscreen.reports.services;

import com.mediscreen.reports.service.AgeCalculatorService;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AgeCalculatorServiceUTests {

    @Autowired
    AgeCalculatorService ageCalculatorService;

    @Test
    @DisplayName("agePatient test functional")
    public void agePatientShouldReturnCorrectAge() {
        String birthdate = "2014-01-01";

        int agePatient = ageCalculatorService.agePatient(birthdate);

        Assert.assertTrue(agePatient == 7);
        Assert.assertFalse(agePatient == 6);

    }

    @Test
    @DisplayName("agePatient test functional: null birthdate returns 0")
    public void agePatientShouldReturn0() {
        String birthdate = null;

        int agePatient = ageCalculatorService.agePatient(birthdate);

        Assert.assertTrue(agePatient == 0);
    }
}