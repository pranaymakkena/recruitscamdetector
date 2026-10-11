package com.trusthire.config;

import com.trusthire.dto.JobPostingRequest;
import com.trusthire.model.CommunityReport;
import com.trusthire.model.Employer;
import com.trusthire.service.CommunityReportService;
import com.trusthire.service.EmployerService;
import com.trusthire.service.JobPostingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final JobPostingService jobService;
    private final CommunityReportService reportService;
    private final EmployerService employerService;

    public DataSeeder(JobPostingService jobService,
                      CommunityReportService reportService,
                      EmployerService employerService) {
        this.jobService = jobService;
        this.reportService = reportService;
        this.employerService = employerService;
    }

    @Override
    public void run(String... args) {
        seedEmployers();
        seedJobPostings();
        seedCommunityReports();
    }

    private void seedEmployers() {
        Employer e1 = new Employer();
        e1.setCompanyName("Infosys Limited");
        e1.setWebsite("https://www.infosys.com");
        e1.setIndustry("IT Services");
        e1.setLinkedinUrl("https://linkedin.com/company/infosys");
        e1.setVerificationStatus("VERIFIED");
        e1.setTrustScore(95);
        e1.setVerificationNotes("Verified through official company registry and LinkedIn.");
        employerService.save(e1);

        Employer e2 = new Employer();
        e2.setCompanyName("TCS (Tata Consultancy Services)");
        e2.setWebsite("https://www.tcs.com");
        e2.setIndustry("IT Services");
        e2.setVerificationStatus("VERIFIED");
        e2.setTrustScore(97);
        e2.setVerificationNotes("Verified NSE/BSE listed company.");
        employerService.save(e2);

        Employer e3 = new Employer();
        e3.setCompanyName("Quick Jobs Pvt Ltd");
        e3.setWebsite("http://quickjobs-fake.xyz");
        e3.setIndustry("Recruitment");
        e3.setVerificationStatus("BLACKLISTED");
        e3.setTrustScore(3);
        e3.setVerificationNotes("Multiple reports of payment scams. Domain registered 2 weeks ago. No company registration found.");
        e3.setReportCount(24);
        employerService.save(e3);

        Employer e4 = new Employer();
        e4.setCompanyName("Global Opportunities Ltd");
        e4.setWebsite("https://globalopp.net");
        e4.setIndustry("HR Consulting");
        e4.setVerificationStatus("SUSPICIOUS");
        e4.setTrustScore(22);
        e4.setVerificationNotes("Unverifiable address. Uses Gmail for communication. Under investigation.");
        e4.setReportCount(8);
        employerService.save(e4);

        Employer e5 = new Employer();
        e5.setCompanyName("Wipro Technologies");
        e5.setWebsite("https://www.wipro.com");
        e5.setIndustry("IT Services");
        e5.setVerificationStatus("VERIFIED");
        e5.setTrustScore(93);
        e5.setVerificationNotes("Verified NSE/BSE listed company.");
        employerService.save(e5);
    }

    private void seedJobPostings() {
        JobPostingRequest j1 = new JobPostingRequest();
        j1.setTitle("Software Engineer - Java Backend");
        j1.setCompany("Infosys Limited");
        j1.setLocation("Bengaluru, Karnataka");
        j1.setDescription("We are looking for a Java Backend Engineer with 0-2 years of experience. " +
            "You will work on enterprise-grade applications using Spring Boot and microservices. " +
            "Package: 4.5-6 LPA. Apply through official careers portal only.");
        j1.setContactEmail("campus@infosys.com");
        j1.setSourceUrl("https://infosys.com/careers");
        j1.setSubmittedBy("Placement Officer - IIT Delhi");
        jobService.submitAndAnalyze(j1);

        JobPostingRequest j2 = new JobPostingRequest();
        j2.setTitle("URGENT HIRING - Work from Home - Earn 5000/- Daily");
        j2.setCompany("DIGITAL MARKETING SOLUTIONS PVT LTD");
        j2.setLocation("Work From Anywhere");
        j2.setDescription("Earn 5000 to 50000 daily from home! No experience needed. " +
            "Guaranteed job offer within 24 hours. Pay registration fee of Rs.500 to get started. " +
            "Contact on WhatsApp immediately. Limited seats available. 100% genuine offer. No scam.");
        j2.setContactEmail("jobs.digital2024@gmail.com");
        j2.setSourceUrl("https://t.me/quickjobs");
        j2.setSubmittedBy("Student - KIIT University");
        jobService.submitAndAnalyze(j2);

        JobPostingRequest j3 = new JobPostingRequest();
        j3.setTitle("Data Analyst Internship");
        j3.setCompany("Analytics India Pvt Ltd");
        j3.setLocation("Mumbai, Maharashtra");
        j3.setDescription("6-month paid internship for final year students. " +
            "Work on real-world data projects with Python and SQL. Stipend: Rs.15,000/month. " +
            "Send resume to hr@analytics-india.com. No fees required.");
        j3.setContactEmail("hr@analytics-india.com");
        j3.setSourceUrl("https://analyticsindia.com/internships");
        j3.setSubmittedBy("Placement Cell - VIT Vellore");
        jobService.submitAndAnalyze(j3);

        JobPostingRequest j4 = new JobPostingRequest();
        j4.setTitle("HR Executive - Pay Security Deposit to Join");
        j4.setCompany("Global Opportunities Ltd");
        j4.setLocation("Delhi NCR");
        j4.setDescription("Hiring HR Executive. Pay a refundable security deposit of Rs.2000 via Western Union. " +
            "Salary: 25,000/month. Immediate joining. Call us on WhatsApp. No interview required. Direct selection.");
        j4.setContactEmail("globalopps.hr@yahoo.com");
        j4.setSourceUrl("https://naukri.com/job/fake");
        j4.setSubmittedBy("Student - Delhi University");
        jobService.submitAndAnalyze(j4);

        JobPostingRequest j5 = new JobPostingRequest();
        j5.setTitle("Frontend Developer - Angular/React");
        j5.setCompany("Wipro Technologies");
        j5.setLocation("Hyderabad, Telangana");
        j5.setDescription("Wipro is hiring frontend developers for its digital transformation projects. " +
            "Required: 1-3 years experience with Angular or React. Good knowledge of TypeScript and REST APIs. " +
            "CTC: 5-8 LPA based on experience. Interview process: Online test → Technical round → HR round.");
        j5.setContactEmail("campus.recruitment@wipro.com");
        j5.setSourceUrl("https://wipro.com/careers");
        j5.setSubmittedBy("Training & Placement - NIT Warangal");
        jobService.submitAndAnalyze(j5);
    }

    private void seedCommunityReports() {
        CommunityReport r1 = new CommunityReport();
        r1.setReporterName("Arjun Sharma");
        r1.setReporterEmail("arjun.s@student.edu");
        r1.setCollege("IIT Bombay");
        r1.setCompanyName("QuickHire Solutions");
        r1.setJobTitle("Management Trainee");
        r1.setScamType("PAYMENT_REQUEST");
        r1.setDescription("They asked me to pay Rs.1500 as training material fee before joining. " +
            "After I paid via UPI, they stopped responding. The number is now switched off.");
        r1.setStatus("VERIFIED");
        r1.setUpvotes(34);
        reportService.submit(r1);

        CommunityReport r2 = new CommunityReport();
        r2.setReporterName("Priya Menon");
        r2.setReporterEmail("priya.m@college.in");
        r2.setCollege("BITS Pilani");
        r2.setCompanyName("TCS Recruitment Drive");
        r2.setJobTitle("System Engineer (Fake Offer)");
        r2.setScamType("FAKE_COMPANY");
        r2.setDescription("Received an official-looking offer letter from 'TCS Talent Team' but the email " +
            "was from tcs.recruitment.official@gmail.com. The letter asked for bank details for salary setup. " +
            "Contacted real TCS HR who confirmed this is fraudulent.");
        r2.setStatus("VERIFIED");
        r2.setUpvotes(58);
        reportService.submit(r2);

        CommunityReport r3 = new CommunityReport();
        r3.setReporterName("Rahul Kumar");
        r3.setReporterEmail("rahul.k@university.edu");
        r3.setCollege("Pune University");
        r3.setCompanyName("DataEdge Analytics");
        r3.setJobTitle("Data Entry Operator");
        r3.setScamType("DATA_COLLECTION");
        r3.setDescription("During registration they asked for Aadhaar, PAN, and bank account details " +
            "claiming it was for background verification. They never called back for interview.");
        r3.setStatus("PENDING");
        r3.setUpvotes(12);
        reportService.submit(r3);

        CommunityReport r4 = new CommunityReport();
        r4.setReporterName("Sneha Patel");
        r4.setReporterEmail("sneha.p@iit.ac.in");
        r4.setCollege("IIT Delhi");
        r4.setCompanyName("Amazon Work From Home");
        r4.setJobTitle("Online Customer Support");
        r4.setScamType("IDENTITY_THEFT");
        r4.setDescription("Impersonating Amazon with a professional website. They asked to install a " +
            "remote desktop app for 'training'. When I refused, they threatened to charge cancellation fees.");
        r4.setStatus("VERIFIED");
        r4.setUpvotes(87);
        reportService.submit(r4);
    }
}
