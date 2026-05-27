package com.resumeanalyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumeanalyzer.dto.AnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${GEMINI_API_KEY:}")
    private String apiKey;

    private final RestClient restClient = RestClient.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AnalysisResult analyzeResume(String resumeText, String jobDescription) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("GEMINI_API_KEY is not set in environment or application.properties");
        }

        String prompt = buildResumePrompt(resumeText, jobDescription);

        // Build the request body map for the Gemini API structure
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                ),
                "generationConfig", Map.of(
                        "responseMimeType", "application/json",
                        "responseSchema", Map.of(
                                "type", "OBJECT",
                                "properties", Map.of(
                                        "overall_summary", Map.of("type", "STRING"),
                                        "seniority_level", Map.of("type", "STRING", "enum", List.of("junior", "mid", "senior", "lead", "unknown")),
                                        "key_skills", Map.of("type", "ARRAY", "items", Map.of("type", "STRING")),
                                        "missing_skills", Map.of("type", "ARRAY", "items", Map.of("type", "STRING")),
                                        "strengths", Map.of("type", "ARRAY", "items", Map.of("type", "STRING")),
                                        "weaknesses", Map.of("type", "ARRAY", "items", Map.of("type", "STRING")),
                                        "ats_score", Map.of("type", "INTEGER"),
                                        "suggestions", Map.of("type", "ARRAY", "items", Map.of("type", "STRING"))
                                ),
                                "required", List.of(
                                        "overall_summary", "seniority_level", "key_skills", "missing_skills", "strengths", "weaknesses", "ats_score", "suggestions"
                                )
                        )
                )
        );

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey.trim();

        try {
            String response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode candidate = rootNode.path("candidates").get(0);
            if (candidate == null) {
                throw new RuntimeException("No candidates found in Gemini API response");
            }
            String jsonText = candidate.path("content").path("parts").get(0).path("text").asText();
            
            return objectMapper.readValue(jsonText, AnalysisResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Gemini API call failed: " + e.getMessage(), e);
        }
    }

    private String buildResumePrompt(String resumeText, String jobDescription) {
        String systemTemplate = """
                You are an expert technical recruiter and ATS specialist.
                Analyze the resume and optional job description.

                STRICT RULES:
                - Output ONLY valid JSON
                - Do NOT include markdown, explanations, or comments
                - Do NOT include trailing commas
                - Keep overall_summary to MAX 2 sentences
                - Limit all array fields to MAX 6 items

                Required JSON format:
                {
                    "overall_summary": string,
                    "seniority_level": "junior" | "mid" | "senior" | "lead" | "unknown",
                    "key_skills": string[],
                    "missing_skills": string[],
                    "strengths": string[],
                    "weaknesses": string[],
                    "ats_score": number,
                    "suggestions": string[]
                }
                """;
        String humanTemplate = """
                Analyze the following resume and job description (optional) and produce ONLY the JSON output:
                RESUME:
                -----------------
                %s

                JOB DESCRIPTION (optional):
                -----------------
                %s
                """.formatted(resumeText, jobDescription != null ? jobDescription : "");

        return systemTemplate + humanTemplate;
    }
}
