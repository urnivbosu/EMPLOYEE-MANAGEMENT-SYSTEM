package com.thapasya.infopark.controllers;

import com.thapasya.infopark.models.Project;
import com.thapasya.infopark.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create a new project
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    // Get all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Get project by ID
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // Assign employees to a project by company ID
    @PutMapping("/{projectId}/employees/company/{companyId}")
    public Project assignEmployeesToProjectByCompany(@PathVariable Long projectId, @PathVariable Long companyId) {
        return projectService.assignEmployeesToProjectByCompany(projectId, companyId);
    }

    // Assign employees to a project by manager ID and company ID
    @PutMapping("/{projectId}/employees/manager/{managerId}/company/{companyId}")
    public Project assignEmployeesToProjectByManager(@PathVariable Long projectId, @PathVariable Long companyId, @PathVariable Long managerId) {
        return projectService.assignEmployeesToProjectByManager(projectId, companyId, managerId);
    }
}
