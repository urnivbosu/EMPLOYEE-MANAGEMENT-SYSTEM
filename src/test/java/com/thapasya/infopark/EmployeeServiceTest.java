package com.thapasya.infopark;

import com.thapasya.infopark.models.Company;
import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.repository.CompanyRepository;
import com.thapasya.infopark.repository.EmployeeRepository;
import com.thapasya.infopark.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private Employee testManager;
    private Company testCompany;

    @BeforeEach
    void setUp() {
        // Setting up a test company
        testCompany = new Company();
        testCompany.setId(1L);
        testCompany.setName("Verteil");

        // Setting up a test manager
        testManager = new Employee();
        testManager.setId(1L);
        testManager.setName("Manager Name");

        // Setting up a test employee
        testEmployee = new Employee();
        testEmployee.setId(5L);
        testEmployee.setName("Aman Ray");
        testEmployee.setEmail("aman.ray@verteil.com");
        testEmployee.setPhone("1234599999");
        testEmployee.setCompany(testCompany);
        testEmployee.setManager(testManager);

        // Use lenient stubbing for cases where the stub might not be used in every test.
        lenient().when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));
        lenient().when(employeeRepository.findById(1L)).thenReturn(Optional.of(testManager)); // For manager lookup
        lenient().when(employeeRepository.findById(5L)).thenReturn(Optional.of(testEmployee)); // For employee lookup
    }

    @Test
    void testAddEmployee() {
        // In addEmployee, the code uses companyRepository.findById(1L) and employeeRepository.findById(1L)
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        Employee savedEmployee = employeeService.addEmployee(testEmployee, 1L, 1L);

        assertNotNull(savedEmployee);
        assertEquals("Aman Ray", savedEmployee.getName());
        assertEquals("aman.ray@verteil.com", savedEmployee.getEmail());
        assertEquals(testCompany, savedEmployee.getCompany());
        assertEquals(testManager, savedEmployee.getManager());

        verify(companyRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(1L); // manager lookup
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(testEmployee));

        Employee foundEmployee = employeeService.getEmployeeById(5L);

        assertNotNull(foundEmployee);
        assertEquals("Aman Ray", foundEmployee.getName());

        verify(employeeRepository, times(1)).findById(5L);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(testEmployee));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeesByCompanyId() {
        when(employeeRepository.findByCompanyId(1L)).thenReturn(List.of(testEmployee));

        List<Employee> employees = employeeService.getEmployeesByCompanyId(1L);

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("Aman Ray", employees.get(0).getName());

        verify(employeeRepository, times(1)).findByCompanyId(1L);
    }

    @Test
    void testGetEmployeesByManager() {
        when(employeeRepository.findByCompanyIdAndManagerId(1L, 1L)).thenReturn(List.of(testEmployee));

        List<Employee> employees = employeeService.getEmployeesByManager(1L, 1L);

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("Aman Ray", employees.get(0).getName());

        verify(employeeRepository, times(1)).findByCompanyIdAndManagerId(1L, 1L);
    }

    @Test
    void testUpdateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(5L);
        updatedEmployee.setName("Updated Name");
        updatedEmployee.setEmail("updated.email@verteil.com");
        updatedEmployee.setCompany(testCompany);
        updatedEmployee.setManager(testManager);

        when(employeeRepository.existsById(5L)).thenReturn(true);
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(testEmployee));
        // Using lenient for stubbing reused in different tests.
        lenient().when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));
        lenient().when(employeeRepository.findById(1L)).thenReturn(Optional.of(testManager));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(5L, updatedEmployee);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated.email@verteil.com", result.getEmail());

        verify(employeeRepository, times(1)).existsById(5L);
        verify(employeeRepository, times(1)).findById(5L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(5L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(5L);

        boolean deleted = employeeService.deleteEmployee(5L);

        assertTrue(deleted);
        verify(employeeRepository, times(1)).existsById(5L);
        verify(employeeRepository, times(1)).deleteById(5L);
    }
}
