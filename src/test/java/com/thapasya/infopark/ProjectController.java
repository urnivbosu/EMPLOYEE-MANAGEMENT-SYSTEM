package com.thapasya.infopark;

import com.thapasya.infopark.controllers.ProjectController;
import com.thapasya.infopark.models.Project;
import com.thapasya.infopark.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        Project project = new Project();
        when(projectService.addProject(any(Project.class))).thenReturn(project);

        Project createdProject = projectController.createProject(project);
        assertEquals(project, createdProject);
    }

    @Test
    void testGetAllProjects() {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectService.getAllProjects()).thenReturn(projects);

        List<Project> result = projectController.getAllProjects();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProjectById() {
        Project project = new Project();
        when(projectService.getProjectById(1L)).thenReturn(project);

        Project result = projectController.getProjectById(1L);
        assertEquals(project, result);
    }

    @Test
    void testAssignEmployeesToProjectByCompany() {
        Project project = new Project();
        when(projectService.assignEmployeesToProjectByCompany(1L, 1L)).thenReturn(project);

        Project result = projectController.assignEmployeesToProjectByCompany(1L, 1L);
        assertEquals(project, result);
    }

    @Test
    void testAssignEmployeesToProjectByManager() {
        Project project = new Project();
        when(projectService.assignEmployeesToProjectByManager(1L, 1L, 1L)).thenReturn(project);

        Project result = projectController.assignEmployeesToProjectByManager(1L, 1L, 1L);
        assertEquals(project, result);
    }
}
