package com.mediscreen.reports.service.webclient;

import com.mediscreen.reports.model.PatientModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PatientWebClientService {
    // Declare the base url
    private final String BASE_URL = "http://localhost:8081";
    // Declare the path to get patient list
    private final String PATH_PATIENT_LIST = "/getPatientList";
    // Declare the path to get patient
    private final String PATH_PATIENT = "/getPatient";
    //Declare the PatientId parameter name to use in the WebClient request
    private final String PATIENT_ID = "?patientId=";

    //Define the patients URI (for patient list)
    private final String getListPatientUri() {
        return BASE_URL + PATH_PATIENT_LIST;
    }

    //Define the patient URI (for getting single patient)
    private final String getPatientUri() {
        return BASE_URL + PATH_PATIENT + PATIENT_ID;
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
    /*
                .onStatus(HttpStatus::is4xxClientError, response ->
                Mono.error(new MyCustomException())
            )
            .onStatus(HttpStatus::is5xxServerError, response ->
                Mono.error(new MyCustomException())
            )
     */

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
                .retrieve()
                .bodyToMono(PatientModel.class);
        PatientModel patient = getPatient.block();
        return patient;
    }
}