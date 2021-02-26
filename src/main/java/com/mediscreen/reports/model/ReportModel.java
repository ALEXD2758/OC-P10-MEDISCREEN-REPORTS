package com.mediscreen.reports.model;

import com.mediscreen.reports.repository.DiseaseEnum;
import com.mediscreen.reports.repository.RiskLevelEnum;
import com.mediscreen.reports.repository.TriggerTermsEnum;

import java.util.List;

public class ReportModel {

    private DiseaseEnum disease;

    private RiskLevelEnum riskLevel;

    private List<TriggerTermsEnum> triggerTerms;

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

    public List<TriggerTermsEnum> getTriggerTerms() {
        return triggerTerms;
    }

    public void setTriggerTerms(List<TriggerTermsEnum> triggerTerms) {
        this.triggerTerms = triggerTerms;
    }
}