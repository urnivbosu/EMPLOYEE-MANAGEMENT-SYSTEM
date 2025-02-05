package com.thapasya.infopark;

import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.models.Project;
import com.thapasya.infopark.repository.EmployeeRepository;
import com.thapasya.infopark.repository.ProjectRepository;
import com.thapasya.infopark.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project testProject;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);
        testProject.setName("AI Automation");

        employee1 = new Employee();
        employee1.setId(101L);
        employee1.setName("John Doe");

        employee2 = new Employee();
        employee2.setId(102L);
        employee2.setName("Jane Smith");

        // Use lenient stubbing to prevent unnecessary stubbing exceptions
        lenient().when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
    }

    @Test
    void testAddProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        Project savedProject = projectService.addProject(testProject);

        assertNotNull(savedProject);
        assertEquals("AI Automation", savedProject.getName());

        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(testProject));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("AI Automation", projects.get(0).getName());

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById_Found() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        Project foundProject = projectService.getProjectById(1L);

        assertNotNull(foundProject);
        assertEquals("AI Automation", foundProject.getName());

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(2L)).thenReturn(Optional.empty());

        Project foundProject = projectService.getProjectById(2L);

        assertNull(foundProject);

        verify(projectRepository, times(1)).findById(2L);
    }

    @Test
    void testAssignEmployeesToProjectByCompany_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(employeeRepository.findByCompanyId(10L)).thenReturn(List.of(employee1, employee2));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        Project updatedProject = projectService.assignEmployeesToProjectByCompany(1L, 10L);

        assertNotNull(updatedProject);
        assertEquals(2, updatedProject.getEmployees().size());

        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findByCompanyId(10L);
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void testAssignEmployeesToProjectByCompany_ProjectNotFound() {
        when(projectRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectService.assignEmployeesToProjectByCompany(2L, 10L);
        });

        assertEquals("Project not found", exception.getMessage());

        verify(projectRepository, times(1)).findById(2L);
        verify(employeeRepository, times(0)).findByCompanyId(anyLong());
        verify(projectRepository, times(0)).save(any(Project.class));
    }

    @Test
    void testAssignEmployeesToProjectByManager_Success() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(employeeRepository.findByCompanyIdAndManagerId(10L, 100L)).thenReturn(List.of(employee1));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        Project updatedProject = projectService.assignEmployeesToProjectByManager(1L, 10L, 100L);

        assertNotNull(updatedProject);
        assertEquals(1, updatedProject.getEmployees().size());
        assertEquals("John Doe", updatedProject.getEmployees().iterator().next().getName());

        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findByCompanyIdAndManagerId(10L, 100L);
        verify(projectRepository, times(1)).save(testProject);
    }

    @Test
    void testAssignEmployeesToProjectByManager_ProjectNotFound() {
        when(projectRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectService.assignEmployeesToProjectByManager(2L, 10L, 100L);
        });

        assertEquals("Project not found", exception.getMessage());

        verify(projectRepository, times(1)).findById(2L);
        verify(employeeRepository, times(0)).findByCompanyIdAndManagerId(anyLong(), anyLong());
        verify(projectRepository, times(0)).save(any(Project.class));
    }
}
