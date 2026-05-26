# 🚀 Resume Analyzer (Gemini AI)

An **AI-powered Resume Analyzer** that evaluates resumes against job descriptions and provides an **ATS score**, skill gap analysis, and actionable feedback — built using **Gemini SDK**, **Node.js**, and **React (Vite)**.

### 📽️ *Demo video* ###
https://youtu.be/W1Q_tCvhmXQ?si=cEDNwbKWBnaP5iiA

---
## ✨ Features

- 📄 Upload resume in **PDF format**
- 🧠 AI-powered analysis using **Gemini SDK**
- 📝 Optional **Job Description** input
- 📊 ATS score generation
- 🔍 Skill match & missing skills detection
- 💡 Strengths, weaknesses & improvement suggestions
- ⚡ Fast, clean UI with Tailwind CSS

---

## 🧠 How It Works

1. User uploads resume (PDF)
2. Resume text is extracted server-side
3. Gemini AI analyzes resume + job description
4. Structured output is validated
5. ATS score & insights are returned to UI

---

## 🧱 Tech Stack

### Backend
- Java 21 & Spring Boot 3
- Gemini REST API
- Apache PDFBox (PDF text extraction)
- Dotenv Java (environment variable management)
- Jackson (JSON parsing & validation)

### Frontend
- React (Vite)
- TypeScript
- Tailwind CSS

---

## 🗂️ Project Structure

```
resume-analyzer/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── .env.example
│
├── frontend/
│   ├── src/
│   ├── components/
│   ├── package.json
│   ├── vite.config.ts
│   └── .env.example
│
├── README.md
└── .gitignore
```
---

## ⚙️ Environment Variables

### Backend (`backend/.env`)
```env
PORT=3000
GEMINI_API_KEY=your_gemini_api_key
FRONTEND_BASE_URI=http://localhost:5173
```

### Frontend (`frontend/.env`)

```env
VITE_API_URL=http://localhost:3000
```

---

## 🚀 Running Locally

### Backend

```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```
