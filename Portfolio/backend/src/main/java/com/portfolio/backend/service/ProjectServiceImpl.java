package com.portfolio.backend.service;

import com.portfolio.backend.model.Project;
import com.portfolio.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/*
 * Implementation of the ProjectService interface.
 * This class provides the actual implementation of the project-related operations.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    /*
     * Constructor for ProjectServiceImpl.
     * 
     * @param projectRepository the repository for project data
     */
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /*
     * Returns a list of all projects.
     * 
     * @return a list of all projects
     */
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /*
     * Returns a project by its ID.
     * 
     * @param id the ID of the project to retrieve
     * 
     * @return an Optional containing the project if found, or empty if not found
     */
    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /*
     * Creates a new project.
     * 
     * @param project the project to create
     * 
     * @return the created project
     */
    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    /*
     * Updates an existing project.
     * 
     * @param id the ID of the project to update
     * 
     * @param projectDetails the updated project data
     * 
     * @return the updated project
     * 
     * @throws RuntimeException if the project with the given ID is not found
     */
    @Override
    public Project updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setTitle(projectDetails.getTitle());
                    project.setDescription(projectDetails.getDescription());
                    project.setTechnologies(projectDetails.getTechnologies());
                    project.setImageUrl(projectDetails.getImageUrl());
                    project.setProjectUrl(projectDetails.getProjectUrl());
                    project.setGithubUrl(projectDetails.getGithubUrl());
                    project.setProjectDate(projectDetails.getProjectDate());
                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id " + id));
    }

    /*
     * Deletes a project by its ID.
     * 
     * @param id the ID of the project to delete
     */
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    /*
     * Saves a project to the database.
     * 
     * @param project the project to save
     * 
     * @return the saved project
     */
    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}