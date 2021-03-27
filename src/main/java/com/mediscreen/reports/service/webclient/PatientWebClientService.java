package com.mediscreen.reports.service.webclient;

import com.mediscreen.reports.model.PatientModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PatientWebClientService {
    // Declare the base url (for docker deployment)
    private final String BASE_URL = "http://patients:8081";
    // Declare the base url (for localhost)
    private final String BASE_URL_LOCALHOST = "http://localhost:8081";
    // Declare the path to get patient list
    private final String PATH_PATIENT_LIST = "/getPatientList";
    // Declare the path to get patient
    private final String PATH_PATIENT = "/getPatient";
    //Declare the PatientId parameter name to use in the WebClient request
    private final String PATIENT_ID = "?patientId=";
    // Declare the path for checking a patient ID
    private final String PATH_PATIENT_EXIST = "/checkPatientId";

    //Define the patients URI (for patient list)
    private final String getListPatientUri() {
        return BASE_URL_LOCALHOST + PATH_PATIENT_LIST;
    }

    //Define the patient URI (for getting single patient)
    private final String getPatientUri() {
        return BASE_URL_LOCALHOST + PATH_PATIENT + PATIENT_ID;
    }

    //Define the patients service URI (for checkPatientIdExist)
    private final String getCheckPatientIdServiceUri() {
        return BASE_URL_LOCALHOST + PATH_PATIENT_EXIST + PATIENT_ID;
    }

    /**
     * Web Client request to server-service "patients" for getting a list of all patients
     *
     * @return PatientModel list of all patients
     */
    public List<PatientModel> getListPatients() {
        Flux<PatientModel> getPatientList= WebClient.create()
                .get()
                .uri(getListPatientUri())
                .retrieve()
                .bodyToFlux(PatientModel.class);
        List<PatientModel> patientList = getPatientList.collectList().block();
        return patientList;
    }

    /**
     * Web Client request to server-service "patients" for getting a patient according to its patientId
     *
     * @param patientId the int of the patient Id
     * @return PatientModel of the patient
     */
    public PatientModel getPatient(int patientId) {
        Mono<PatientModel> getPatient= WebClient.create()
                .get()
                .uri(getPatientUri() + patientId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PatientModel.class);
        PatientModel patient = getPatient.block();
        return patient;
    }

    /**
     * Web Client request to server-service "patients" to check if a patient Id exists
     *
     * @param patientId int
     * @return boolean of patient id exists' query
     */
    public boolean checkPatientIdExist(int patientId) {
        Mono<Boolean> getPatientList= WebClient.create()
                .get()
                .uri(getCheckPatientIdServiceUri() + patientId)
                .retrieve()
                .bodyToMono(Boolean.class);
        boolean patientList = getPatientList.block();
        return patientList;
    }
}