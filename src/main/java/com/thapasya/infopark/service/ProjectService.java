package com.thapasya.infopark.service;

import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.models.Project;
import com.thapasya.infopark.repository.EmployeeRepository;
import com.thapasya.infopark.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Add a new project
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get a project by ID
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    // Add employees to a project by companyId
    public Project assignEmployeesToProjectByCompany(Long projectId, Long companyId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Fetch all employees for the given company
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);

        // Assign employees to the project
        project.getEmployees().addAll(employees);

        // Save the project with updated employee list
        return projectRepository.save(project);
    }

    // Add employees to a project by managerId and companyId
    public Project assignEmployeesToProjectByManager(Long projectId, Long companyId, Long managerId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Fetch all employees for the given company and manager
        List<Employee> employees = employeeRepository.findByCompanyIdAndManagerId(companyId, managerId);

        // Assign employees to the project
        project.getEmployees().addAll(employees);

        // Save the project with updated employee list
        return projectRepository.save(project);
    }
}
