package com.thapasya.infopark.repository;

import com.thapasya.infopark.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
