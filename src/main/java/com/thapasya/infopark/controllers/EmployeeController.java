package com.thapasya.infopark.controllers;

import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping("/{companyId}/{managerId}")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee,
                                                   @PathVariable Long companyId,
                                                   @PathVariable Long managerId) {
        // Call service to add employee
        Employee createdEmployee = employeeService.addEmployee(employee, companyId, managerId);
        return ResponseEntity.ok(createdEmployee);
    }

    // Get all employees
    @GetMapping("/getemp")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Get employees by company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Employee>> getEmployeesByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(employeeService.getEmployeesByCompanyId(companyId));
    }

    // Get employees under a manager in a specific company
    @GetMapping("/company/{companyId}/manager/{managerId}")
    public ResponseEntity<List<Employee>> getEmployeesByManager(
            @PathVariable Long companyId, @PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesByManager(companyId, managerId));
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, updatedEmployee));
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
