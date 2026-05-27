# 🚀 Resume Analyzer (Gemini AI)

An **AI-powered Resume Analyzer** that evaluates resumes against job descriptions and provides an **ATS score**, skill gap analysis, and actionable feedback — built using **Gemini SDK**, **Java Spring Boot 3**, and **React (Vite)**.

### 🔗 Live Production Demo:
* 💻 **Live Web App**: [https://frontend-two-ashen-89.vercel.app](https://frontend-two-ashen-89.vercel.app)
* ⚙️ **Backend API**: [https://ai-resume-analyzer-using-gemini-ai-production.up.railway.app](https://ai-resume-analyzer-using-gemini-ai-production.up.railway.app)

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

Follow these step-by-step instructions to run the application on your local machine.

### Step 1: Run the Backend (Spring Boot)

1. Open your terminal and navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Build and compile the project (optional but recommended for first time):
   ```bash
   mvn clean compile
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
4. The backend server will start running on port **3000**.
   * 🔗 **Backend API Link**: [http://localhost:3000](http://localhost:3000)

### Step 2: Run the Frontend (React + Vite)

1. Open a new, separate terminal and navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```
2. Install the required Node dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. The frontend application will start running on port **5173**.
   * 🔗 **Frontend Web App Link**: [http://localhost:5173](http://localhost:5173)

---

## 🔗 Local Access Links

Once both servers are running successfully, you can access them using the links below:

* 💻 **Web Application (UI)**: [http://localhost:5173](http://localhost:5173)
* ⚙️ **Backend Server (REST API)**: [http://localhost:3000](http://localhost:3000)

