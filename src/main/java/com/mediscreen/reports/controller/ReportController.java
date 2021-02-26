package com.mediscreen.reports.controller;

import com.mediscreen.reports.model.DemographicsModel;
import com.mediscreen.reports.model.NoteModel;
import com.mediscreen.reports.model.ReportModel;
import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;
import com.mediscreen.reports.service.ReportService;
import com.mediscreen.reports.service.webclient.RecordWebClientService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    RecordWebClientService recordWebClientService;

    @Autowired
    ReportService reportService;

    @GetMapping("/getListNotes")
    public @ResponseBody List<NoteModel> getListNotes(@RequestParam Integer patientId) {
        return recordWebClientService.getListNotesPatient(patientId);
    }

    @GetMapping("/getDemographic")
    @ResponseBody
    public DemographicsModel getDemographic(@RequestParam Integer patientId) {
        return reportService.getDemographicsFromPatientModel(patientId);
    }

    @GetMapping("/getListNs")
    public @ResponseBody List<RiskLevelEnum> getListNotees() {
        return reportService.getListRiskLevels();
    }

    @GetMapping("/reporting/assessment/{patientId}")
    public String reportingAssessment(@PathVariable("patientId") Integer patientId, Model model) {
    //    ReportModel reportPatientModel = new ReportModel();
        DemographicsModel demographicsModel = getDemographic(patientId);

        List<NoteModel> listNotes = recordWebClientService.getListNotesPatient(patientId);
        List<ReportModel> listReport = reportService.listRiskLevel(demographicsModel, listNotes);

        model.addAttribute("demographicsPatient", demographicsModel);
        model.addAttribute("reportPatient", listReport);
        return "reporting/assessment";
    }
}