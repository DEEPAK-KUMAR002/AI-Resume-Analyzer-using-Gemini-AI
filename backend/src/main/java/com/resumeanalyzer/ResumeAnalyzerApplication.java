package com.resumeanalyzer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResumeAnalyzerApplication {

    public static void main(String[] args) {
        // Load .env file and set properties as system properties so Spring can resolve them
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory(".")
                    .ignoreIfMissing()
                    .load();
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
        } catch (Exception e) {
            System.out.println("Warning: Could not load .env file. Relying on System environment variables. " + e.getMessage());
        }

        SpringApplication.run(ResumeAnalyzerApplication.class, args);
    }
}
