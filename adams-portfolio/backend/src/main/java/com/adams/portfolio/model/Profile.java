package com.adams.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String title;
    private String location;

    @Column(length = 1000)
    private String summary;

    private String githubUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private String avatarUrl;

}
