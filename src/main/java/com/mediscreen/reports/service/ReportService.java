package com.mediscreen.reports.service;

import com.mediscreen.reports.model.DemographicsModel;
import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.model.PatientModel;
import com.mediscreen.reports.model.ReportModel;
import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;
import com.mediscreen.reports.repository.TriggerTermsEnum;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportService {

    private PatientWebClientService patientWebClientService;
    private AgeCalculatorService ageCalculatorService;

    public ReportService(PatientWebClientService patientWebClientService, AgeCalculatorService ageCalculatorService) {
        this.patientWebClientService = patientWebClientService;
        this.ageCalculatorService = ageCalculatorService;
    }

    /**
     * Set up a Demographics Model from a Patient Model with a specific patientId
     *
     * @param patientId
     * @return a demographicsModel
     */
    public DemographicsModel getDemographicsFromPatientModel(int patientId) {
        PatientModel patient = patientWebClientService.getPatient(patientId);
        DemographicsModel demographicsPatient = new DemographicsModel();

        //Call function agePatient for calculating the age from LocalDate (java time)
        int agePatient = ageCalculatorService.agePatient(patient.getBirthdate().toString());

        demographicsPatient.setGivenName(patient.getGivenName());
        demographicsPatient.setFamilyName(patient.getFamilyName());
        demographicsPatient.setGender(patient.getGender());
        demographicsPatient.setAge(agePatient);
        demographicsPatient.setEmailAddress(patient.getEmailAddress());
        demographicsPatient.setPhoneNumber(patient.getPhoneNumber());

        return demographicsPatient;
    }

    /**
     * Create a list with all values contained in DiseaseEnum
     * @return a list of DiseaseEnum
     */
    public List<DiseaseEnum> getListDiseases() {
        return new ArrayList<DiseaseEnum>(Arrays.asList(DiseaseEnum.values()));
    }

    /**
     * Create a list with all values contained in RiskLevelEnum
     * @return a list of RiskLevelEnum
     */
    public List<RiskLevelEnum> getListRiskLevels() {
        return new ArrayList<RiskLevelEnum>(Arrays.asList(RiskLevelEnum.values()));
    }

    /**
     * Method used for building a list with all values contained in TriggerTermsEnum
     * @return a list of TriggerTermsEnum
     */
    public List<TriggerTermsEnum> getListTriggerTerms() {
        return new ArrayList<TriggerTermsEnum>(Arrays.asList(TriggerTermsEnum.values()));
    }

    /**
     * Method used for DIABETES disease: iterate through each NoteModel present in the List<NoteModel> to calculate the
     * total number of triggers present in the list
     * Call numberTriggersDiabetesDiseaseInNote in the lambda expression as a way to check if the NoteModel contains
     * more than one trigger
     *
     * @param listNotes the list of NoteModel
     * @return int of the final number of triggers
     */
    public final int numberTriggersDiabetesDisease(List<NoteModel> listNotes) {
         int finalNbTriggersInNoteList = listNotes.stream()
                 .mapToInt(ln -> numberTriggersDiabetesDiseaseInNote(ln).intValue()).sum();
        return finalNbTriggersInNoteList;
    }

    /**
     * Method used for DIABETES disease: iterate through each NoteModel present in the List<NoteModel> to calculate the
     * total number of triggers present in the list
     * Initialize to zero an AtomicInteger and iterate through each trigger present in TriggerTermsEnum and check if
     * it contains a string of the comment of NoteModel. Add +1 if true, nothing if false
     *
     * @param noteModel the NoteModel
     * @return int the number of trigger terms matching with inside the note comment
     */
    public AtomicInteger numberTriggersDiabetesDiseaseInNote(NoteModel noteModel) {
        List<TriggerTermsEnum> triggersList = getListTriggerTerms();
        AtomicInteger nbTriggersNote = new AtomicInteger();
        triggersList.forEach(tl -> {
            String commentUpperCaseWithoutSpace = noteModel.getComment().toUpperCase().replaceAll(" ","");
            if (commentUpperCaseWithoutSpace.contains(tl.toString())) {
                nbTriggersNote.incrementAndGet();
            }
        });
        return nbTriggersNote;
    }

    /**
     * Determine through if/else if statements risk level of the patient
     *
     * @param demographicsModel
     * @param listNotes the list of NoteModel
     * @return value of RiskLevelEnum
     */
    public RiskLevelEnum riskLevelDiabetesDisease(DemographicsModel demographicsModel, List<NoteModel> listNotes) {
        int age = demographicsModel.getAge();
        String gender = demographicsModel.getGender();
        int nbTriggers = numberTriggersDiabetesDisease(listNotes);

        //CONDITION STATEMENT FOR DETERMINING "NONE" RISK LEVEL
        if (nbTriggers == 0) {
            return RiskLevelEnum.NONE;
        }
        //CONDITION STATEMENT FOR DETERMINING "BORDERLINE" RISK LEVEL
          else if (nbTriggers == 2 && age >= 30) {
            return RiskLevelEnum.BORDERLINE;
        }
        //CONDITION STATEMENTS FOR DETERMINING "INDANGER" RISK LEVEL
          else if (nbTriggers == 3 && age < 30 && gender.equals("MALE")) {
            return RiskLevelEnum.INDANGER;
        } else if (nbTriggers == 4 && age < 30 && gender.equals("FEMALE")) {
            return RiskLevelEnum.INDANGER;
        } else if (nbTriggers == 6 && age >= 30) {
            return RiskLevelEnum.INDANGER;
        }
        //CONDITION STATEMENT FOR DETERMINING "EARLYONSET" RISK LEVEL
          else if (nbTriggers == 5 && age < 30 && gender.equals("MALE")) {
            return RiskLevelEnum.EARLYONSET;
        } else if (nbTriggers == 7 && age < 30 && gender.equals("FEMALE")) {
            return RiskLevelEnum.EARLYONSET;
        } else if (nbTriggers >= 8 && age >= 30) {
            return RiskLevelEnum.EARLYONSET;
        }
        //CONDITION STATEMENT FOR DETERMINING "NOINFO" RISK LEVEL
        return RiskLevelEnum.NOINFO;
    }

    /**
     * Create a new ReportModel list and inject each type of disease with its associated risk level for the patient
     * TODO: You can use this method to add all other type of diseases in a ReportModel
     *
     * @param demographicsModel
     * @param listNotes the list of NoteModel
     * @return a list of ReportModel
     */
    public List<ReportModel> listRiskLevel(DemographicsModel demographicsModel, List<NoteModel> listNotes) {
        //CREATE A NEW REPORT MODEL LIST
        List<ReportModel> listReport = new ArrayList<>();
        //CREATE A NEW REPORT MODEL FOR DIABETES DISEASE WITH ENUM CLASS AND THE RISK LEVEL DETERMINED FOR THE PATIENT
        ReportModel diabetesReport = new ReportModel();
        diabetesReport.setDisease(DiseaseEnum.DIABETES);
        diabetesReport.setRiskLevel(riskLevelDiabetesDisease(demographicsModel, listNotes));

        //ADD THE DIABETES REPORT MODEL TO THE LIST OF REPORT MODEL
        listReport.add(diabetesReport);
        return listReport;
    }
}