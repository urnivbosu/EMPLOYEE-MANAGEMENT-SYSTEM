package com.thapasya.infopark.repository;

import com.thapasya.infopark.models.Employee;
import com.thapasya.infopark.models.Company;
import com.thapasya.infopark.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
//import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // employees by company ID
    List<Employee> findByCompanyId(Long companyId);

    //employees under a manager in a specific company
    List<Employee> findByCompanyIdAndManagerId(Long companyId, Long managerId);

    Optional<Employee> findByEmail(String email);

}
