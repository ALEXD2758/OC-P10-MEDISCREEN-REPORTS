package com.mediscreen.reports.controllers;

import com.mediscreen.reports.model.AddressModel;
import com.mediscreen.reports.model.PatientModel;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebAppConfiguration()
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PatientControllerITTest {

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    PatientWebClientService patientWebClientService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    public AddressModel addressModel1() {
        AddressModel addressModel1 = new AddressModel();
        addressModel1.setStreet("StreetTest1");
        addressModel1.setCity("CityTest1");
        addressModel1.setPostcode("112345");
        addressModel1.setDistrict("DistrictTest1");
        addressModel1.setState("StateTest1");
        addressModel1.setCountry("CountryTest1");
        return addressModel1;
    }

    public PatientModel patientModel1() {

        LocalDate date = new LocalDate(2020, 01, 01);
        PatientModel patientModel1 = new PatientModel();
        patientModel1.setGivenName("John");
        patientModel1.setFamilyName("Boyd");
        patientModel1.setBirthdate(date);
        patientModel1.setGender("MALE");
        patientModel1.setAddress(addressModel1());
        patientModel1.setEmailAddress("EmailTest1@email.com");
        patientModel1.setPhoneNumber("004678925899");
        return patientModel1;
    }

    @Test
    public void getRequestPatientListViewShouldReturnSuccess() throws Exception {
        //1. Setup
        List<PatientModel> patientList = new ArrayList<>();
        patientList.add(patientModel1());

        doReturn(patientList)
                .when(patientWebClientService)
                .getListPatients();

        //2. Act
        mockMvc.perform(get("/patient/list"))

        //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("patient/list"))
                .andExpect(model().attributeExists("patients"))
                .andReturn();
        assertTrue(patientList.get(0).getGivenName().equals("John"));
    }
}