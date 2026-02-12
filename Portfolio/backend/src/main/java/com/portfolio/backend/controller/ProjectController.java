package com.portfolio.backend.controller;

import com.portfolio.backend.model.Project;
import com.portfolio.backend.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/*
 * The ProjectController class is a REST controller that handles HTTP requests for project-related operations.
 * It is annotated with @RestController and @RequestMapping("/api/projects") to define the base URL for all project-related endpoints.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    /*
     * The ProjectController class is a REST controller that handles HTTP requests
     * for project-related operations.
     * It is annotated with @RestController and @RequestMapping("/api/projects") to
     * define the base URL for all project-related endpoints.
     */
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get a project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new project
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        // Call the service method
        Project createdProject = projectService.createProject(project);
        // Return the result
        return ResponseEntity.ok(createdProject);
    }
}
