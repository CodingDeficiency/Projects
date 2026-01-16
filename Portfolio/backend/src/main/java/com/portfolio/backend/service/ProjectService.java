package com.portfolio.backend.service;

import com.portfolio.backend.model.Project;
import java.util.List;
import java.util.Optional;

/*
*Service interface for managing projects in the portfolio.
This interface defines the business operations available for projects entities.
*/

public interface ProjectService {

    /**
     * Retrieves all projects from the database.
     * 
     * @return a list of all projects.
     */
    List<Project> getAllProjects();

    /**
     * Retrieves a project by its ID.
     * 
     * @param id the ID of the project to retrieve
     * @return an Optional containing the project if found, or empty if not found
     */
    Optional<Project> getProjectById(Long id);

    /**
     * Creates a new project in the database.
     * 
     * @param project the project to create
     * @return the created project
     */
    Project createProject(Project project);

    /**
     * Updates an existing project.
     * 
     * @param id             the ID of the project to update
     * @param projectDetails the updated project data
     * @return the updated project
     * @throws RuntimeException if the project with the given ID is not found
     */
    Project updateProject(Long id, Project project);

    /**
     * Deletes a project from the database.
     * 
     * @param id the ID of the project to delete
     */
    void deleteProject(Long id);

    /**
     * Saves a project to the database.
     * 
     * @param project the project to save
     * @return the saved project
     */
    Project saveProject(Project project);
}
