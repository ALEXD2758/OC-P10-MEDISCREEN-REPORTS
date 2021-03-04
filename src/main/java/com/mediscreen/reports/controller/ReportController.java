package com.mediscreen.reports.controller;

import com.mediscreen.reports.exception.MicroServiceNotFoundException;
import com.mediscreen.reports.exception.PatientIdNotFoundException;
import com.mediscreen.reports.model.DemographicsModel;
import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.model.ReportModel;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.PatientWebClientService;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;

@Controller
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(ReportController.class);

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
     * @throws MicroServiceNotFoundException in case of MicroService not found
     * @throws PatientIdNotFoundException in case the patientId does not exist in patient service
     */
    @GetMapping("/reporting/assessment/{patientId}")
    public String reportingAssessment(@PathVariable("patientId") Integer patientId, Model model) throws
            MicroServiceNotFoundException, PatientIdNotFoundException {
        try {
            if (patientWebClientService.checkPatientIdExist(patientId) == false) {
                throw new PatientIdNotFoundException(patientId);
            }
            DemographicsModel demographicsModel = reportService.getDemographicsFromPatientModel(patientId);

            List<NoteModel> listNotes = recordWebClientService.getListNotesPatient(patientId);
            List<ReportModel> listReport = reportService.listRiskLevel(demographicsModel, listNotes);

            model.addAttribute("demographicsPatient", demographicsModel);
            model.addAttribute("reportPatient", listReport);

            logger.info("GET /reporting/assessment/{patientId} : OK");

        } catch (WebClientRequestException e) {
            throw new MicroServiceNotFoundException();
        }
        return "reporting/assessment";
    }
}