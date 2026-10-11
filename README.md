# TrustHire AI — Recruitment Scam Detector

> Protecting students and job seekers from fraudulent job postings in the Indian recruitment market.

![Platform](https://img.shields.io/badge/Platform-Web-blue)
![Backend](https://img.shields.io/badge/Backend-Spring%20Boot%203.3-green)
![Frontend](https://img.shields.io/badge/Frontend-Angular%2021-red)
![Java](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

---

## What is TrustHire AI?

TrustHire AI is a full-stack web application that helps students and job seekers detect and report fraudulent job postings. The platform uses a rule-based heuristic engine to analyse job descriptions, flag red flags, and assign a fraud risk score — with a particular focus on scam patterns common in the Indian recruitment market (paid internship scams, fake offer letters, WhatsApp-based urgent hiring scams, and more).

### Core Features

| Feature | Description |
|---|---|
| **Job Fraud Analyser** | Paste a job posting and get an instant fraud risk score (0–100) with a detailed breakdown of red flags |
| **Employer Verification** | Look up employers by trust score and verification status (VERIFIED / SUSPICIOUS / BLACKLISTED) |
| **Community Reports** | Crowdsourced scam reports from students, with upvoting and status tracking |
| **Placement Cell View** | Dedicated interface for college placement officers to manage and monitor postings |
| **Dashboard** | Platform-wide statistics including total jobs analysed, high-risk count, and verified employer count |

---

## Tech Stack

### Backend
- **Java 21** + **Spring Boot 3.3.0**
- **Spring Data JPA** with **H2 in-memory database** (auto-seeded on startup)
- **Spring Validation** for request DTOs
- **Lombok** for boilerplate reduction
- **Maven** build system
- REST API on port `8080`; H2 console at `/h2-console`

### Frontend
- **Angular 21** (standalone components)
- **TypeScript** + **SCSS**
- **Angular Router** — five main views
- Communicates with backend over `http://localhost:8080`
- Dev server on port `4200`

---

## Project Structure

```
recruitscamdetector/
├── backend/                          # Spring Boot application
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/trusthire/
│       │   ├── TrustHireApplication.java     # App entry point
│       │   ├── config/
│       │   │   ├── CorsConfig.java            # CORS (allows localhost:4200)
│       │   │   └── DataSeeder.java            # Seeds demo data on startup
│       │   ├── controller/
│       │   │   ├── JobPostingController.java  # /api/jobs
│       │   │   ├── EmployerController.java    # /api/employers
│       │   │   ├── CommunityReportController.java # /api/reports
│       │   │   └── DashboardController.java   # /api/dashboard/stats
│       │   ├── dto/                           # Request/response DTOs
│       │   ├── model/                         # JPA entities
│       │   ├── repository/                    # Spring Data repositories
│       │   └── service/
│       │       ├── FraudAnalysisService.java  # Core fraud detection engine
│       │       ├── JobPostingService.java
│       │       ├── EmployerService.java
│       │       └── CommunityReportService.java
│       └── resources/
│           └── application.properties
└── frontend/                         # Angular application
    └── src/app/
        ├── app.routes.ts
        ├── components/
        │   ├── dashboard/
        │   ├── analyze-job/
        │   ├── community-reports/
        │   ├── employer-verify/
        │   ├── placement-cell/
        │   └── shared/
        ├── models/
        └── services/
```

---

## How the Fraud Detection Works

The `FraudAnalysisService` scans job content (title + company + description + contact email) against three tiers of indicators:

| Tier | Examples | Score Impact |
|---|---|---|
| **High-Risk Keywords** | "registration fee", "pay to apply", "earn 50000 daily", "bitcoin payment", "rs.199" | +20 pts each |
| **Medium-Risk Patterns** | Gmail/Yahoo contact email, "no interview", "direct selection", "whatsapp support" | +10 pts each |
| **Suspicious Language** | "limited seats", "valid till today", "government approved", "globally recognized" | +8 pts each |

Additional checks: personal email domain (+15), description under 100 chars (+10), any rupee fee amount in description (+30), internship + fee combo (+25), urgency + payment pressure (+15), fake certification bait (+15).

**Risk Levels:**

| Score | Level | Meaning |
|---|---|---|
| 0–19 | 🟢 LOW | No major red flags |
| 20–44 | 🟡 MEDIUM | Some suspicious elements — verify independently |
| 45–69 | 🟠 HIGH | Multiple red flags — proceed with extreme caution |
| 70–100 | 🔴 CRITICAL | Highly likely to be a scam — do not engage |

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- Node.js 18+ and npm
- Angular CLI 21 (`npm install -g @angular/cli`)

### Run the Backend

```bash
cd backend
mvn spring-boot:run
```

The API will start at `http://localhost:8080`.  
Demo data is seeded automatically on every startup (employers, job postings, and community reports).  
H2 console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:trusthiredb`).

### Run the Frontend

```bash
cd frontend
npm install
ng serve
```

Open `http://localhost:4200` in your browser.

---

## API Reference

### Job Postings — `/api/jobs`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/jobs/analyze` | Submit a job posting for fraud analysis |
| `GET` | `/api/jobs` | List all analysed job postings |
| `GET` | `/api/jobs/{id}` | Get a single job posting |
| `GET` | `/api/jobs/risk/{level}` | Filter by risk level (LOW/MEDIUM/HIGH/CRITICAL) |
| `GET` | `/api/jobs/flagged` | Get jobs flagged as fraud |
| `DELETE` | `/api/jobs/{id}` | Delete a job posting |

**Sample request body for `POST /api/jobs/analyze`:**
```json
{
  "title": "Software Engineer",
  "company": "Infosys Limited",
  "location": "Bengaluru",
  "description": "We are looking for a Java backend engineer...",
  "contactEmail": "campus@infosys.com",
  "sourceUrl": "https://infosys.com/careers",
  "submittedBy": "Placement Officer"
}
```

### Employers — `/api/employers`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/employers` | Add an employer |
| `GET` | `/api/employers` | List all employers |
| `GET` | `/api/employers/search?q=` | Search employers by name |
| `GET` | `/api/employers/status/{status}` | Filter by verification status |
| `PUT` | `/api/employers/{id}/verify` | Update verification status |

### Community Reports — `/api/reports`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/reports` | Submit a scam report |
| `GET` | `/api/reports` | List all reports |
| `GET` | `/api/reports/type/{scamType}` | Filter by scam type |
| `POST` | `/api/reports/{id}/upvote` | Upvote a report |

Scam types: `PAYMENT_REQUEST`, `FAKE_COMPANY`, `IDENTITY_THEFT`, `DATA_COLLECTION`, `OTHER`

### Dashboard — `/api/dashboard/stats`

Returns platform-wide statistics (total jobs, high-risk count, verified employers, etc.).

---

## Known Limitations

- **In-memory database** — all data is reset on every restart. For persistence, replace H2 with PostgreSQL or MySQL in `application.properties`.
- **No authentication** — all API endpoints are open. Production use would require Spring Security.
- **Rule-based engine only** — no external ML model or LLM is used. The analysis is based on curated keyword lists.
- **Indian market focus** — fraud patterns, fee amounts (₹), and example data are tailored to the Indian job market.

---

## Contributing

Contributions are welcome! To improve the fraud detection engine, add new keywords to the relevant arrays in `FraudAnalysisService.java`. For UI changes, components live in `frontend/src/app/components/`.

---

## License

This project is licensed under the MIT License.
