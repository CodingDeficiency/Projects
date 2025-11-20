package com.timeclock.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WelcomeController {

    @GetMapping()
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Time Clock API!");
        response.put("version", "1.0");
        response.put("status", "Running");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("employees", "/api/employees");
        endpoints.put("punch", "/api/punch");
        endpoints.put("reports", "/api/reports");

        response.put("endpoints", endpoints);

        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running smoothly!");
        return response;
    }
}