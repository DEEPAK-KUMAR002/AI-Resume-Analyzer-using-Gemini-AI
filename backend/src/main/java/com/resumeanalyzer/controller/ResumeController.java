package com.resumeanalyzer.controller;

import com.resumeanalyzer.dto.ApiResponse;
import com.resumeanalyzer.dto.AnalysisResult;
import com.resumeanalyzer.service.GeminiService;
import com.resumeanalyzer.service.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*") // Allows calls from any origin like the original Express CORS setup when needed
@RequestMapping("/")
public class ResumeController {

    private final PdfService pdfService;
    private final GeminiService geminiService;

    public ResumeController(PdfService pdfService, GeminiService geminiService) {
        this.pdfService = pdfService;
        this.geminiService = geminiService;
    }

    @GetMapping
    public String home() {
        return "hii";
    }

    @PostMapping("/api/analyze-resume")
    public ResponseEntity<ApiResponse<AnalysisResult>> analyzeResume(
            @RequestParam("resume") MultipartFile file,
            @RequestParam(value = "jobDescription", required = false) String jobDescription) {

        System.out.println("📥 Received analyze-resume request. File: " + file.getOriginalFilename() + ", Size: " + file.getSize() + " bytes");

        if (file == null || file.isEmpty()) {
            System.out.println("❌ Error: File is empty or null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure("PDF file is required"));
        }

        if (!"application/pdf".equals(file.getContentType())) {
            System.out.println("❌ Error: Invalid mime type: " + file.getContentType());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure("Only PDF files are supported."));
        }

        try {
            System.out.println("📄 Extracting text from PDF...");
            String resumeText = pdfService.extractText(file);
            if (resumeText == null || resumeText.trim().length() < 50) {
                System.out.println("❌ Error: Extracted text is too short or null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.failure("Could not extract enough text from the PDF."));
            }
            System.out.println("📄 Extracted text successfully (" + resumeText.length() + " chars). Calling Gemini API...");

            AnalysisResult analysisResult = geminiService.analyzeResume(resumeText, jobDescription);
            System.out.println("✅ Gemini analysis succeeded!");
            return ResponseEntity.ok(ApiResponse.success(analysisResult));

        } catch (Exception e) {
            System.out.println("💥 Exception during analysis: ");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure("Failed to process PDF: " + e.getMessage()));
        }
    }
}
