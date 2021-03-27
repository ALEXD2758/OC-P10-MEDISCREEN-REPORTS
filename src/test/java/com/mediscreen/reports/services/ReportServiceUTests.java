package com.mediscreen.reports.services;

import com.mediscreen.reports.model.*;
import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;
import com.mediscreen.reports.repository.TriggerTermsEnum;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportServiceUTests {

    @Autowired
    ReportService reportService;

    @MockBean
    private PatientWebClientService patientWebClientService;

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

        LocalDate date = new LocalDate(2014, 01,01);
        PatientModel patientModel1 = new PatientModel();
        patientModel1.setId(2);
        patientModel1.setGivenName("John");
        patientModel1.setFamilyName("Boyd");
        patientModel1.setBirthdate(date.toString());
        patientModel1.setGender("MALE");
        patientModel1.setAddress(addressModel1());
        patientModel1.setEmailAddress("EmailTest1@email.com");
        patientModel1.setPhoneNumber("004678925899");
        return patientModel1;
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

    public NoteModel noteModel3() {
        LocalDateTime date = LocalDateTime.of(2021,02,04,13,45,30);

        NoteModel noteModel3 = new NoteModel();
        noteModel3.setId("1245569");
        noteModel3.setPatientId("1");
        noteModel3.setCreationDateTime(date);
        noteModel3.setComment("CHOLESTEROL");
        return noteModel3;
    }

    public NoteModel noteModel4() {
        LocalDateTime date = LocalDateTime.of(2021,02,05,13,45,30);

        NoteModel noteModel4 = new NoteModel();
        noteModel4.setId("1245577");
        noteModel4.setPatientId("2");
        noteModel4.setCreationDateTime(date);
        noteModel4.setComment("Healthy");
        return noteModel4;
    }

    public NoteModel noteModel5() {
        LocalDateTime date = LocalDateTime.of(2021,02,05,13,45,30);

        NoteModel noteModel5 = new NoteModel();
        noteModel5.setId("1245577");
        noteModel5.setPatientId("2");
        noteModel5.setCreationDateTime(date);
        noteModel5.setComment("Antibodies and cholesterol and body weight");
        return noteModel5;
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

    @Test
    @DisplayName("getListDiseases returns List<DiseaseEnum>")
    public void getListDiseasesShouldReturnListDiseases() {
        List<DiseaseEnum> diseaseEnumList = reportService.getListDiseases();

        Assert.assertTrue(diseaseEnumList.get(0).equals(DiseaseEnum.DIABETES));
        Assert.assertTrue(diseaseEnumList.size() == 1);
    }

    @Test
    @DisplayName("getListRiskLevels returns List<RiskLevelEnum>")
    public void getListRiskLevelsShouldReturnListRiskLevel() {
        List<RiskLevelEnum> riskLevelEnumList = reportService.getListRiskLevels();

        Assert.assertTrue(riskLevelEnumList.get(1).equals(RiskLevelEnum.EARLYONSET));
        Assert.assertTrue(riskLevelEnumList.size() == 5);
    }

    @Test
    @DisplayName("getListTriggerTerms returns List<TriggerTermsEnum>")
    public void getListTriggerTermsShouldReturnListTriggerTerms() {
        List<TriggerTermsEnum> triggerTermsEnumList = reportService.getListTriggerTerms();

        Assert.assertTrue(triggerTermsEnumList.get(2).equals(TriggerTermsEnum.BODYHEIGHT));
        Assert.assertTrue(triggerTermsEnumList.size() == 11);
    }


    @Test
    @DisplayName("getDemographicsFromPatientModel returns correct DemographicsModel")
    public void getDemographicsFromPatientModelShouldReturnCorrectDemographicsModel() throws ConnectException {

        doReturn(patientModel1())
                .when(patientWebClientService)
                .getPatient(2);

        DemographicsModel triggerTermsEnumList = reportService.getDemographicsFromPatientModel(2);
        Assert.assertTrue(triggerTermsEnumList.getGivenName().equals("John"));
        Assert.assertTrue(triggerTermsEnumList.getAge() == 7);
        Assert.assertTrue(triggerTermsEnumList.getGender().equals("MALE"));
    }

    @Test
    @DisplayName("numberTriggersDiabetesDisease returns 3 Trigger Terms")
    public void numberTriggersDiabetesDiseaseShouldReturnCorrect3TriggerTerms() {
        //ARRANGE
        List<NoteModel> listReportModel = new ArrayList<>();
        listReportModel.add(noteModel1());
        listReportModel.add(noteModel2());
        listReportModel.add(noteModel3());
        listReportModel.add(noteModel4());
        //ACT
        int nbTriggers = reportService.numberTriggersDiabetesDisease(listReportModel);

        //ASSERT
        Assert.assertTrue(nbTriggers == 3);
    }

    @Test
    @DisplayName("numberTriggersDiabetesDiseaseInNote returns 2 trigger terms inside 1 Note Model")
    public void numberTriggersDiabetesDiseaseInNoteShouldReturn2TriggerTermsInsideOneNoteModel() {
        //ARRANGE
        noteModel5();
        //ACT
        AtomicInteger atomicNbTriggers = reportService.numberTriggersDiabetesDiseaseInNote(noteModel5());
        final int nbTriggers = atomicNbTriggers.intValue();
        //ASSERT
        Assert.assertTrue(nbTriggers == 3);
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level with 0 trigger: NONE")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelNone() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel4());

        demographicsModel();

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(demographicsModel(), listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.NONE));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns correct Risk Level with 2 triggers and Age >= 30: BORDERLINE")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelBorderLine() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());

        demographicsModel().setAge(31);

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(demographicsModel(), listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.BORDERLINE));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level for MALE, 3 trigger terms and age < 30: INDANGER")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelInDangerForMale3TriggersAgeInfTo30() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN");
        listNoteModel.get(1).setComment("ANTIBODIES");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(28);
        modifiedDemographicsModel.setGender("MALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.INDANGER));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level for FEMALE, 4 trigger terms and age < 30: INDANGER")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelInDangerForFemale4TriggersAgeInfTo30() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN");
        listNoteModel.get(1).setComment("REACTION, ANTIBODIES");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(28);
        modifiedDemographicsModel.setGender("FEMALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.INDANGER));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level with 6 triggers and age >=30: INDANGER")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelInDangerWith6Triggers() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN, BODYHEIGHT");
        listNoteModel.get(1).setComment("RELAPSE, REACTION, ANTIBODIES");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(34);
        modifiedDemographicsModel.setGender("FEMALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.INDANGER));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level for MALE, 5 trigger terms and age < 30: EARLYONSET")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelEarlyOnSetForMale5TriggersAgeInfTo30() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN, BODYHEIGHT");
        listNoteModel.get(1).setComment("RELAPSE, REACTION");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(28);
        modifiedDemographicsModel.setGender("MALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.EARLYONSET));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level for FEMALE, 7 trigger terms and age < 30: EARLYONSET")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelEarlyOnSetForFemale7TriggersAgeInfTo30() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN, BODYHEIGHT, BODYWEIGHT");
        listNoteModel.get(1).setComment("RELAPSE, REACTION, ANTIBODIES");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(28);
        modifiedDemographicsModel.setGender("FEMALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.EARLYONSET));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level with 8 triggers or more and age >=30: EARLYONSET")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelEarlyOnSetWith9Triggers() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());
        listNoteModel.add(noteModel2());
        listNoteModel.get(0).setComment("HEMOGLOBINA1C, MICROALBUMIN, BODYHEIGHT, BODYWEIGHT, SMOKER");
        listNoteModel.get(1).setComment("DIZZINESS, RELAPSE, REACTION, ANTIBODIES");

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(34);
        modifiedDemographicsModel.setGender("FEMALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.EARLYONSET));
    }

    @Test
    @DisplayName("riskLevelDiabetesDisease returns Risk Level with 1 trigger: NO INFO")
    public void riskLevelDiabetesDiseaseShouldReturnRiskLevelNoInfoWith1Trigger() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel1());

        DemographicsModel modifiedDemographicsModel = demographicsModel();
        modifiedDemographicsModel.setAge(34);
        modifiedDemographicsModel.setGender("FEMALE");

        //ACT
        RiskLevelEnum riskLevel = reportService.riskLevelDiabetesDisease(modifiedDemographicsModel, listNoteModel);

        //ASSERT
        Assert.assertTrue(riskLevel.equals(RiskLevelEnum.NOINFO));
    }

    @Test
    @DisplayName("listRiskLevel returns correct Report Model")
    public void listRiskLevelShouldReturnCorrectReportModel() {
        //ARRANGE
        List<NoteModel> listNoteModel = new ArrayList<>();
        listNoteModel.add(noteModel4());

        ReportModel reportModel = new ReportModel();
        reportModel.setDisease(DiseaseEnum.DIABETES);
        reportModel.setRiskLevel(RiskLevelEnum.NONE);

        //ACT
        List<ReportModel> listReport = reportService.listRiskLevel(demographicsModel(), listNoteModel);

        //ASSERT
        Assert.assertTrue(listReport.get(0).getRiskLevel().equals((RiskLevelEnum.NONE)));
        Assert.assertTrue(listReport.size() == 1);
    }
}