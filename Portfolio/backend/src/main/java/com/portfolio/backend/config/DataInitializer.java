package com.portfolio.backend.config;

import com.portfolio.backend.model.Project;
import com.portfolio.backend.service.ProjectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ProjectService projectService;

    public DataInitializer(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to avoid duplicates
        if (projectService.getAllProjects().isEmpty()) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        Project project1 = new Project();
        project1.setTitle("E-Commerce Dashboard");
        project1.setDescription(
                "A comprehensive analytics dashboard for online retailers providing real-time insights and performance tracking.");
        project1.setTechnologies("React, Node.js, MySQL, Chart.js");
        project1.setImageUrl("https://example.com/ecommerce-dashboard.jpg");
        project1.setProjectUrl("https://demo.example.com/ecommerce");
        project1.setGithubUrl("https://github.com/username/ecommerce-dashboard");
        project1.setProjectDate("2024-01-15");
        project1.setProjectCategories(Arrays.asList("Web Development", "UI/UX"));

        projectService.createProject(project1);

        System.out.println("✅ Sample projects initialized successfully!");

        Project project2 = new Project();
        project2.setTitle("Task Manager App");
        project2.setDescription(
                "Cross-platform mobile application for personal productivity with cloud sync and offline-first workflows.");
        project2.setTechnologies("Flutter, Dart, Firebase, SQLite");
        project2.setImageUrl("https://example.com/task-manager.jpg");
        project2.setProjectUrl("https://apps.apple.com/task-manager");
        project2.setGithubUrl("https://github.com/username/task-manager");
        project2.setProjectDate("2024-02-20");
        project2.setProjectCategories(Arrays.asList("Mobile Apps", "UI/UX"));

        projectService.createProject(project2);

        Project project3 = new Project();
        project3.setTitle("Social Media API");
        project3.setDescription(
                "Robust RESTful API handling authentication, post interactions, and analytics with rate-limiting and caching.");
        project3.setTechnologies("Node.js, Express, MongoDB, Redis, JWT");
        project3.setImageUrl("https://example.com/social-api.jpg");
        project3.setProjectUrl("https://docs.example.com/social-api");
        project3.setGithubUrl("https://github.com/username/social-media-api");
        project3.setProjectDate("2023-12-10");
        project3.setProjectCategories(Arrays.asList("Web Development", "Open Source"));

        projectService.createProject(project3);
        Project project4 = new Project();
        project4.setTitle("Weather Widget");
        project4.setDescription(
                "A lightweight, embeddable weather widget consuming OpenWeatherMap with clean UI and theming options.");
        project4.setTechnologies("Vue.js, JavaScript, CSS, OpenWeatherMap API");
        project4.setImageUrl("https://example.com/weather-widget.jpg");
        project4.setProjectUrl("https://demo.example.com/weather-widget");
        project4.setGithubUrl("https://github.com/username/weather-widget");
        project4.setProjectDate("2024-03-10");
        project4.setProjectCategories(Arrays.asList("UI/UX", "Web Development"));

        projectService.createProject(project4);

        Project project5 = new Project();
        project5.setTitle("Portfolio v1");
        project5.setDescription(
                "The first iteration of my personal portfolio site, built with semantic HTML/CSS and performance in mind.");
        project5.setTechnologies("HTML5, CSS3, JavaScript, Bootstrap");
        project5.setImageUrl("https://example.com/portfolio-v1.jpg");
        project5.setProjectUrl("https://portfolio.example.com");
        project5.setGithubUrl("https://github.com/username/portfolio-v1");
        project5.setProjectDate("2023-08-15");
        project5.setProjectCategories(Arrays.asList("Web Development", "UI/UX"));

        projectService.createProject(project5);

        Project project6 = new Project();
        project6.setTitle("Realtime Chat");
        project6.setDescription(
                "A responsive chat application featuring private messaging, rooms, typing indicators, and Socket.IO events.");
        project6.setTechnologies("Socket.IO, Node.js, Express, Redis, JWT");
        project6.setImageUrl("https://example.com/realtime-chat.jpg");
        project6.setProjectUrl("https://chat.example.com");
        project6.setGithubUrl("https://github.com/username/realtime-chat");
        project6.setProjectDate("2023-11-20");
        project6.setProjectCategories(Arrays.asList("Web Development", "Open Source"));

        projectService.createProject(project6);
    }
}