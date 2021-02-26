package com.mediscreen.reports.model;

import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;

public class ReportModel {

    private DiseaseEnum disease;

    private RiskLevelEnum riskLevel;

    public DiseaseEnum getDisease() {
        return disease;
    }

    public void setDisease(DiseaseEnum disease) {
        this.disease = disease;
    }

    public RiskLevelEnum getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevelEnum riskLevel) {
        this.riskLevel = riskLevel;
    }
}