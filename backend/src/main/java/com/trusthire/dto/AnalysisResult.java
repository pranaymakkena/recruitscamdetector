package com.trusthire.dto;

import java.util.List;

public class AnalysisResult {
    private int riskScore;
    private String riskLevel;
    private List<String> riskFactors;
    private String aiAnalysis;

    public AnalysisResult(int riskScore, String riskLevel, List<String> riskFactors, String aiAnalysis) {
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.riskFactors = riskFactors;
        this.aiAnalysis = aiAnalysis;
    }

    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public List<String> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }

    public String getAiAnalysis() { return aiAnalysis; }
    public void setAiAnalysis(String aiAnalysis) { this.aiAnalysis = aiAnalysis; }
}
