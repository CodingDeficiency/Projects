package com.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

//Represents a project in the portfolio.
//This entity maps to the 'projects' table in the database.

@Data
@Entity
@Table(name = "projects")
public class Project {

    // Unique identifier for the project.
    // Auto-incremented primary key.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of the project.
    // Cannot be null. making it a required field
    @Column(nullable = false)
    private String title;

    // Description of the project.
    // Can be null. making it an optional field
    @Column(columnDefinition = "TEXT")
    private String description;
    // Technologies used in the project.
    // Can be null. making it an optional field
    // example: "Java, Spring Boot, MySQL"
    private String technologies;

    // URL to the image of the project.
    // Can be null. making it an optional field
    // example: "https://example.com/image.jpg"
    private String imageUrl;

    // URL to the project.
    // Can be null. making it an optional field
    // example: "https://example.com/project"
    private String projectUrl;

    // URL to the GitHub repository of the project.
    // Can be null. making it an optional field
    // example: "https://github.com/username/project"
    private String githubUrl;

    // Date when the project was created.
    // Can be null. making it an optional field
    // example: "2022-01-01"
    private String projectDate;
}
