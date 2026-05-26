package com.resumeanalyzer.dto;

import java.util.List;

public record AnalysisResult(
    String overall_summary,
    String seniority_level,
    List<String> key_skills,
    List<String> missing_skills,
    List<String> strengths,
    List<String> weaknesses,
    Integer ats_score,
    List<String> suggestions
) {}
