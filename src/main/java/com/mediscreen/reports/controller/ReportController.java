package com.mediscreen.reports.controller;

import com.mediscreen.reports.model.DemographicsModel;
import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.model.PatientModel;
import com.mediscreen.reports.model.ReportModel;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    RecordWebClientService recordWebClientService;

    @Autowired
    PatientWebClientService patientWebClientService;

    @Autowired
    ReportService reportService;

    /**
     * HTTP GET request for getting the view reporting/assessment with the demographics of a patient as well as
     * a list of ReportModel with all disease associated with the risk level for the disease
     *
     * @param patientId Integer of the patient id
     * @param model Model Interface, to add attributes to it
     * @return a string to the address "reporting/assessment", returning the associated view
     * with attribute
     */
    @GetMapping("/reporting/assessment/{patientId}")
    public String reportingAssessment(@PathVariable("patientId") Integer patientId, Model model) {
        PatientModel patient = patientWebClientService.getPatient(patientId);
        DemographicsModel demographicsModel = reportService.getDemographicsFromPatientModel(patient);

        List<NoteModel> listNotes = recordWebClientService.getListNotesPatient(patientId);
        List<ReportModel> listReport = reportService.listRiskLevel(demographicsModel, listNotes);

        model.addAttribute("demographicsPatient", demographicsModel);
        model.addAttribute("reportPatient", listReport);
        return "reporting/assessment";
    }
}