package com.thapasya.infopark.service;

import com.thapasya.infopark.models.Company;
import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.models.User;
import com.thapasya.infopark.repository.CompanyRepository;
import com.thapasya.infopark.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Employee addEmployee(Employee employee, Long companyId, Long managerId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Employee manager = null;
        if (managerId != 0) { // If managerId is not 0, get the manager details
            manager = employeeRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        }

        employee.setCompany(company);
        employee.setManager(manager); // This can be null

        return employeeRepository.save(employee);
    }



    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


//    public EmployeeDTO getEmployeeById(Long id) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        ManagerDTO managerDTO = new ManagerDTO(
//                employee.getManager().getId(),
//                employee.getManager().getName(),
//                employee.getManager().getEmail(),
//                employee.getManager().getPhone()
//        );
//
//        return new EmployeeDTO(
//                employee.getId(),
//                employee.getName(),
//                employee.getEmail(),
//                employee.getPhone(),
//                employee.getDesignation().toString(),
//                managerDTO
//        );
//    }

    // Get an employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Get employees by company ID
    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    // Get employees under a manager in a specific company
    public List<Employee> getEmployeesByManager(Long companyId, Long managerId) {
        return employeeRepository.findByCompanyIdAndManagerId(companyId, managerId);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }


    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (employeeRepository.existsById(id)) {
            Employee existingEmployee = employeeRepository.findById(id).get();

            // Fetching the company
            Company company = companyRepository.findById(updatedEmployee.getCompany().getId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            updatedEmployee.setCompany(company);

            // Fetch the manager
            Employee manager = employeeRepository.findById(updatedEmployee.getManager().getId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            updatedEmployee.setManager(manager);

            updatedEmployee.setId(id);

            return employeeRepository.save(updatedEmployee);
        }
        return null;
    }

    // Delete an employee
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
