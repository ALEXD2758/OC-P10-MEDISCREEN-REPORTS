package com.mediscreen.reports.controller;

import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

  /*  @Autowired
    RecordWebClientService recordWebClientService;

    @GetMapping("/getListNotes")
    public List<NoteModel> getListNotes(Integer patientId) {
        return recordWebClientService.getListNotesPatient(patientId);
    }

   */
}
