package com.thapasya.infopark;

import com.thapasya.infopark.controllers.EmployeeController;
import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.addEmployee(any(Employee.class), anyLong(), anyLong())).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee, 1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testGetEmployeesByCompany() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeService.getEmployeesByCompanyId(1L)).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByCompany(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetEmployeesByManager() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeService.getEmployeesByManager(1L, 2L)).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByManager(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, employee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<String> response = employeeController.deleteEmployee(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Employee deleted successfully", response.getBody());
    }
}
