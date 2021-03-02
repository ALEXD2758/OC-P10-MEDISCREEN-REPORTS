package com.mediscreen.reports.controllers;

import com.mediscreen.reports.model.*;
import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;
import com.mediscreen.reports.service.AgeCalculatorService;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
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

import java.time.LocalDateTime;
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
public class ReportControllerITTest {

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private ReportService reportService;

    @MockBean
    private PatientWebClientService patientWebClientService;

    @MockBean
    private AgeCalculatorService ageCalculatorService;

    @MockBean
    private RecordWebClientService recordWebClientService;



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

        LocalDate date = new LocalDate(2014, 01, 01);
        PatientModel patientModel1 = new PatientModel();
        patientModel1.setId(2);
        patientModel1.setGivenName("John");
        patientModel1.setFamilyName("Boyd");
        patientModel1.setBirthdate(date);
        patientModel1.setGender("MALE");
        patientModel1.setAddress(addressModel1());
        patientModel1.setEmailAddress("EmailTest1@email.com");
        patientModel1.setPhoneNumber("004678925899");
        return patientModel1;
    }

    public DemographicsModel demographicsModel() {
        DemographicsModel demographicsModel = new DemographicsModel();
        demographicsModel.setGivenName("Alexandre");
        demographicsModel.setFamilyName("Dubois");
        demographicsModel.setAge(30);
        demographicsModel.setGender("MALE");
        demographicsModel.setEmailAddress("Dubois_Alexandre@live.fr");
        demographicsModel.setPhoneNumber("0499779639");
        return demographicsModel;
    }

    public NoteModel noteModel1() {
        LocalDateTime date = LocalDateTime.of(2021,01,01,13,45,30);

        NoteModel noteModel1 = new NoteModel();
        noteModel1.setId("1245567");
        noteModel1.setPatientId("1");
        noteModel1.setCreationDateTime(date);
        noteModel1.setComment("Antibodies");
        return noteModel1;
    }

    public NoteModel noteModel2() {
        LocalDateTime date = LocalDateTime.of(2021,02,02,13,45,30);

        NoteModel noteModel2 = new NoteModel();
        noteModel2.setId("1245568");
        noteModel2.setPatientId("2");
        noteModel2.setCreationDateTime(date);
        noteModel2.setComment("Body Height");
        return noteModel2;
    }

    @Test
    public void getRequestReportingAssessmentViewShouldReturnSuccess() throws Exception {
        //ARRANGE
        List<PatientModel> patientList = new ArrayList<>();
        patientList.add(patientModel1());

        List<NoteModel> listNotes = new ArrayList<>();
        listNotes.add(noteModel1());
        listNotes.add(noteModel2());

        List<ReportModel> listReport = new ArrayList<>();
        ReportModel reportModel = new ReportModel();
        reportModel.setDisease(DiseaseEnum.DIABETES);
        reportModel.setRiskLevel(RiskLevelEnum.NONE);
        listReport.add(reportModel);

        DemographicsModel demographicsModel1 = new DemographicsModel();
        demographicsModel1.setGivenName("Alexandre");
        demographicsModel1.setFamilyName("Dubois");
        demographicsModel1.setAge(30);
        demographicsModel1.setGender("MALE");
        demographicsModel1.setEmailAddress("Dubois_Alexandre@live.fr");
        demographicsModel1.setPhoneNumber("0499779639");

        //ACT
        doReturn(patientModel1())
                .when(patientWebClientService)
                .getPatient(2);

        doReturn(demographicsModel1)
                .when(reportService)
                .getDemographicsFromPatientModel(patientModel1());

        doReturn(listNotes)
                .when(recordWebClientService)
                .getListNotesPatient(2);

        doReturn(listReport)
                .when(reportService)
                .listRiskLevel(demographicsModel(), listNotes);

        mockMvc.perform(get("/reporting/assessment/{patientId}", "2"))

        //ASSERT
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("reporting/assessment"))
                .andExpect(model().attributeExists("demographicsPatient"))
                .andExpect(model().attributeExists("reportPatient"))
                .andReturn();
    }
}