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

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

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

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}