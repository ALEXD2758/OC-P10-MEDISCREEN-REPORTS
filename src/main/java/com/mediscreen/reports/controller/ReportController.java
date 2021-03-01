package com.mediscreen.reports.controller;

import com.mediscreen.reports.model.DemographicsModel;
import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.model.ReportModel;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    RecordWebClientService recordWebClientService;

    @Autowired
    ReportService reportService;

    /**
     * HTTP GET request for getting the demographics of a specific patient, by its patientId
     * @param patientId Integer of the patientId
     * @return DemographicsModel of a patient
     */
    @GetMapping("/getDemographic")
    @ResponseBody
    public DemographicsModel getDemographic(@RequestParam Integer patientId) {
        return reportService.getDemographicsFromPatientModel(patientId);
    }

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
        DemographicsModel demographicsModel = getDemographic(patientId);

        List<NoteModel> listNotes = recordWebClientService.getListNotesPatient(patientId);
        List<ReportModel> listReport = reportService.listRiskLevel(demographicsModel, listNotes);

        model.addAttribute("demographicsPatient", demographicsModel);
        model.addAttribute("reportPatient", listReport);
        return "reporting/assessment";
    }
}