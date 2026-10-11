package com.trusthire.service;

import com.trusthire.dto.AnalysisResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-powered fraud analysis engine.
 * Uses rule-based heuristics to detect scam patterns in job descriptions.
 */
@Service
public class FraudAnalysisService {

    // High-risk keywords indicative of scams
    private static final String[] HIGH_RISK_KEYWORDS = {
        "registration fee", "processing fee", "security deposit", "training fee",
        "pay to apply", "guaranteed job", "work from home earn", "no experience needed",
        "earn 50000 daily", "easy money", "urgent hiring", "immediate joining",
        "pay via western union", "bitcoin payment", "advance payment",
        "100% placement guarantee", "visa sponsored", "lottery", "prize money",
        // Paid internship scam patterns
        "fees will depend", "fees & charges", "how to book seat", "book seat",
        "one time payment", "offer is valid till today", "valid till today",
        "click here to download", "download offer letter", "offer letter sample",
        "globally recognized certification", "globally recognised certification",
        "certificate of internship", "intern evaluation report",
        "self paced online", "self-paced online",
        "aicte", "apsche", "nip approved",
        // Fee amounts commonly used in these scams
        "rs.199", "rs.299", "rs.999", "rs.1199", "rs.1499", "rs.1999", "rs.2999",
        "199 only", "299 only", "999 only"
    };

    private static final String[] MEDIUM_RISK_KEYWORDS = {
        "gmail.com", "yahoo.com", "hotmail.com", "whatsapp", "telegram",
        "no interview", "direct selection", "no age limit", "work from anywhere",
        "part time earn", "unlimited income", "passive income",
        // Internship scam indicators
        "whatsapp chat support", "whatsapp support", "weekly off",
        "corporate profile", "certificate sample", "batch starting",
        "2/4/6/8 weeks", "2/4 weeks", "6/8 weeks", "12/16 weeks",
        "multiple projects", "learning curve", "best educators",
        "follow us on linkedin", "follow us on instagram"
    };

    private static final String[] SUSPICIOUS_PATTERNS = {
        "contact on whatsapp", "call immediately", "limited seats",
        "only today", "first come first serve", "do not miss",
        "verified company", "100% genuine", "no scam",
        // Urgency & pressure tactics
        "offer is valid", "valid till today", "hurry",
        "seat is limited", "limited seats available", "book your seat",
        "offer valid till", "last date", "today only",
        // Fake credential bait
        "government approved", "iso certified", "globally recognized",
        "internationally recognized", "globally recognised", "internationally recognised",
        "recognized by", "approved by government", "ministry approved"
    };

    public AnalysisResult analyze(String title, String company, String description, String contactEmail) {
        List<String> riskFactors = new ArrayList<>();
        int score = 0;

        String content = (title + " " + company + " " + description + " " + contactEmail).toLowerCase();

        // Check high-risk keywords
        for (String keyword : HIGH_RISK_KEYWORDS) {
            if (content.contains(keyword.toLowerCase())) {
                riskFactors.add("🚨 High-risk phrase detected: \"" + keyword + "\"");
                score += 20;
            }
        }

        // Check medium-risk keywords
        for (String keyword : MEDIUM_RISK_KEYWORDS) {
            if (content.contains(keyword.toLowerCase())) {
                riskFactors.add("⚠️ Suspicious pattern: \"" + keyword + "\"");
                score += 10;
            }
        }

        // Check suspicious patterns
        for (String pattern : SUSPICIOUS_PATTERNS) {
            if (content.contains(pattern.toLowerCase())) {
                riskFactors.add("⚠️ Red flag language: \"" + pattern + "\"");
                score += 8;
            }
        }

        // Check email domain
        if (contactEmail != null && !contactEmail.isBlank()) {
            String emailLower = contactEmail.toLowerCase();
            if (emailLower.contains("@gmail") || emailLower.contains("@yahoo") ||
                emailLower.contains("@hotmail") || emailLower.contains("@outlook")) {
                riskFactors.add("📧 Personal email used instead of corporate domain");
                score += 15;
            }
        }

        // Check description length (too short is suspicious)
        if (description != null && description.trim().length() < 100) {
            riskFactors.add("📝 Very short job description — lacks credibility");
            score += 10;
        }

        // Check if company name looks suspicious (all caps, too generic)
        if (company != null) {
            if (company.equals(company.toUpperCase()) && company.length() > 5) {
                riskFactors.add("🏢 Company name in all caps — potential red flag");
                score += 5;
            }
            if (company.toLowerCase().contains("pvt ltd") && !content.contains("cin") &&
                !content.contains("registration")) {
                riskFactors.add("🏢 Company claims Pvt Ltd status without verifiable registration");
                score += 8;
            }
        }

        // Check for salary promises
        if (description != null) {
            String desc = description.toLowerCase();
            if (desc.matches(".*\\d+\\s*(lpa|lakh|per day|daily|weekly).*")) {
                if (desc.contains("per day") || desc.contains("daily")) {
                    riskFactors.add("💰 Unrealistic daily salary claims detected");
                    score += 15;
                }
            }
        }

        // Check for paid internship scam patterns
        if (description != null) {
            String desc = description.toLowerCase();

            // Any mention of rupee fees (Rs. followed by numbers)
            if (desc.matches(".*rs\\.?\\s*\\d+.*") || desc.matches(".*₹\\s*\\d+.*")) {
                riskFactors.add("💸 Candidate is being charged a fee — legitimate internships/jobs never charge applicants");
                score += 30;
            }

            // Internship + fee combo is always a scam signal
            boolean mentionsFee = desc.contains("fee") || desc.contains("payment") || desc.contains("pay") || desc.contains("charge");
            boolean mentionsInternship = desc.contains("internship") || desc.contains("intern");
            if (mentionsInternship && mentionsFee) {
                riskFactors.add("🎓 Paid internship detected — legitimate internships do not charge participants");
                score += 25;
            }

            // Urgency + payment combo
            boolean mentionsUrgency = desc.contains("valid till") || desc.contains("today only") || desc.contains("limited seats") || desc.contains("book seat");
            if (mentionsUrgency && mentionsFee) {
                riskFactors.add("⏰ Urgency + payment pressure tactic — classic scam combination");
                score += 15;
            }

            // Fake credential bait
            if (desc.contains("certificate") && (desc.contains("globally") || desc.contains("internationally") || desc.contains("recognized") || desc.contains("recognised"))) {
                riskFactors.add("🏅 False prestige claim — 'globally recognized certificate' is a common scam lure");
                score += 15;
            }

            // Multiple duration options with pricing is a scam template
            if ((desc.contains("2 weeks") || desc.contains("4 weeks") || desc.contains("2/4")) &&
                (desc.contains("6 weeks") || desc.contains("8 weeks") || desc.contains("6/8"))) {
                riskFactors.add("📋 Cookie-cutter multi-duration pricing template — matches known scam formats");
                score += 20;
            }
        }

        // Cap at 100
        score = Math.min(score, 100);

        String riskLevel;
        String aiAnalysis;

        if (score >= 70) {
            riskLevel = "CRITICAL";
            aiAnalysis = "This job posting exhibits multiple strong indicators of recruitment fraud. " +
                "The combination of suspicious keywords, payment requests, and unverifiable claims " +
                "makes this highly likely to be a scam. Students are strongly advised not to engage " +
                "and report this posting immediately.";
        } else if (score >= 45) {
            riskLevel = "HIGH";
            aiAnalysis = "Several red flags were identified in this posting. It shows patterns " +
                "commonly associated with fraudulent recruiters including suspicious contact " +
                "methods and potentially misleading job promises. Proceed with extreme caution.";
        } else if (score >= 20) {
            riskLevel = "MEDIUM";
            aiAnalysis = "Some suspicious elements were found in this job posting. While not " +
                "definitively fraudulent, the identified patterns warrant careful verification " +
                "before proceeding. Verify the employer's credentials independently.";
        } else {
            riskLevel = "LOW";
            aiAnalysis = "This job posting appears relatively safe based on our analysis. " +
                "No major red flags were detected. However, always perform due diligence " +
                "by verifying the employer through official channels before applying.";
        }

        if (riskFactors.isEmpty()) {
            riskFactors.add("✅ No major red flags detected in this posting");
        }

        return new AnalysisResult(score, riskLevel, riskFactors, aiAnalysis);
    }
}
