package com.mediscreen.reports.service.webclient;

import com.mediscreen.reports.model.NoteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class RecordWebClientService {

    // Declare the base url
    private final String BASE_URL = "http://localhost:8082";
    // Declare the path for patient list
    private final String PATH_NOTE_LIST = "/getNoteList";
    //Declare the PatientId parameter name to use in the WebClient request
    private final String PATIENT_ID = "?patientId=";

    //Define the listNotesPatientService URI (for patient list)
    private final String getNotesListPatientServiceUri() {
        return BASE_URL + PATH_NOTE_LIST + PATIENT_ID;
    }

    /**
     * Web Client request to server-service "records" for getting a list of notes for a specific patient Id
     *
     * @param patientId int
     * @return NoteModel list of all notes
     */
    public List<NoteModel> getListNotesPatient(int patientId) {
        Flux<NoteModel> getNoteList= WebClient.create()
                .get()
                .uri(getNotesListPatientServiceUri() + patientId)
                .retrieve()
                .bodyToFlux(NoteModel.class);
        List<NoteModel> noteList = getNoteList.collectList().block();
        return noteList;
    }
}
