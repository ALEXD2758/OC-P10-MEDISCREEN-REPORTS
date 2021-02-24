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
    // Declare the path for patient list
    private final String PATH_PATIENT_LIST = "/getPatientList";

    //Define the patients service URI (for patient list)
    private final String getListPatientServiceUri() {
        return BASE_URL + PATH_PATIENT_LIST;
    }

    /**
     * Web Client request to server-service "patients" for getting a list of all patients
     *
     * @return PatientModel list of all patients
     */
    public List<PatientModel> getListPatients() {
        Flux<PatientModel> getPatientList= WebClient.create()
                .get()
                .uri(getListPatientServiceUri())
                .retrieve()
                .bodyToFlux(PatientModel.class);
        List<PatientModel> patientList = getPatientList.collectList().block();
        return patientList;
    }
}