package com.trusthire.dto;

public class DashboardStats {
    private long totalJobsAnalyzed;
    private long highRiskJobs;
    private long flaggedJobs;
    private long communityReports;
    private long verifiedEmployers;
    private long blacklistedEmployers;
    private double averageRiskScore;
    private long verifiedReports;

    public DashboardStats(long totalJobsAnalyzed, long highRiskJobs, long flaggedJobs,
                          long communityReports, long verifiedEmployers, long blacklistedEmployers,
                          double averageRiskScore, long verifiedReports) {
        this.totalJobsAnalyzed = totalJobsAnalyzed;
        this.highRiskJobs = highRiskJobs;
        this.flaggedJobs = flaggedJobs;
        this.communityReports = communityReports;
        this.verifiedEmployers = verifiedEmployers;
        this.blacklistedEmployers = blacklistedEmployers;
        this.averageRiskScore = averageRiskScore;
        this.verifiedReports = verifiedReports;
    }

    public long getTotalJobsAnalyzed() { return totalJobsAnalyzed; }
    public void setTotalJobsAnalyzed(long totalJobsAnalyzed) { this.totalJobsAnalyzed = totalJobsAnalyzed; }

    public long getHighRiskJobs() { return highRiskJobs; }
    public void setHighRiskJobs(long highRiskJobs) { this.highRiskJobs = highRiskJobs; }

    public long getFlaggedJobs() { return flaggedJobs; }
    public void setFlaggedJobs(long flaggedJobs) { this.flaggedJobs = flaggedJobs; }

    public long getCommunityReports() { return communityReports; }
    public void setCommunityReports(long communityReports) { this.communityReports = communityReports; }

    public long getVerifiedEmployers() { return verifiedEmployers; }
    public void setVerifiedEmployers(long verifiedEmployers) { this.verifiedEmployers = verifiedEmployers; }

    public long getBlacklistedEmployers() { return blacklistedEmployers; }
    public void setBlacklistedEmployers(long blacklistedEmployers) { this.blacklistedEmployers = blacklistedEmployers; }

    public double getAverageRiskScore() { return averageRiskScore; }
    public void setAverageRiskScore(double averageRiskScore) { this.averageRiskScore = averageRiskScore; }

    public long getVerifiedReports() { return verifiedReports; }
    public void setVerifiedReports(long verifiedReports) { this.verifiedReports = verifiedReports; }
}
