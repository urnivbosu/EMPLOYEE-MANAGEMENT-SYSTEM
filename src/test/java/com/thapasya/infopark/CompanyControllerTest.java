package com.thapasya.infopark;

import com.thapasya.infopark.controllers.CompanyController;
import com.thapasya.infopark.models.Company;
import com.thapasya.infopark.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private Company mockCompany;

    @BeforeEach
    void setUp() {
        mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setName("Verteil");
        mockCompany.setLocation("Infopark");
    }

    @Test
    void createCompany_ShouldReturnCompany() {
        // Arrange
        when(companyService.addCompany(any(Company.class))).thenReturn(mockCompany);

        // Act
        ResponseEntity<Company> response = companyController.createCompany(mockCompany);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Verteil", response.getBody().getName());

        // Verify
        verify(companyService, times(1)).addCompany(any(Company.class));
    }

    @Test
    void getAllCompanies_ShouldReturnListOfCompanies() {
        // Arrange
        List<Company> companyList = Arrays.asList(mockCompany);
        when(companyService.getAllCompanies()).thenReturn(companyList);

        // Act
        ResponseEntity<List<Company>> response = companyController.getAllCompanies();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());

        // Verify
        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    void getCompanyById_CompanyExists_ShouldReturnCompany() {
        // Arrange
        when(companyService.getCompanyById(1L)).thenReturn(mockCompany);

        // Act
        ResponseEntity<Company> response = companyController.getCompanyById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Verteil", response.getBody().getName());

        // Verify
        verify(companyService, times(1)).getCompanyById(1L);
    }

    @Test
    void getCompanyById_CompanyNotFound_ShouldReturnNotFound() {
        // Arrange
        when(companyService.getCompanyById(2L)).thenReturn(null);

        // Act
        ResponseEntity<Company> response = companyController.getCompanyById(2L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());

        // Verify
        verify(companyService, times(1)).getCompanyById(2L);
    }

    @Test
    void updateCompany_CompanyExists_ShouldReturnUpdatedCompany() {
        // Arrange
        Company updatedCompany = new Company();
        updatedCompany.setId(1L);
        updatedCompany.setName("Updated Verteil");
        updatedCompany.setLocation("Updated Location");

        when(companyService.updateCompany(eq(1L), any(Company.class))).thenReturn(updatedCompany);

        // Act
        ResponseEntity<Company> response = companyController.updateCompany(1L, updatedCompany);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Verteil", response.getBody().getName());

        // Verify
        verify(companyService, times(1)).updateCompany(eq(1L), any(Company.class));
    }

    @Test
    void updateCompany_CompanyNotFound_ShouldReturnNotFound() {
        // Arrange
        when(companyService.updateCompany(eq(2L), any(Company.class))).thenReturn(null);

        // Act
        ResponseEntity<Company> response = companyController.updateCompany(2L, new Company());

        // Assert
        assertEquals(404, response.getStatusCodeValue());

        // Verify
        verify(companyService, times(1)).updateCompany(eq(2L), any(Company.class));
    }

    @Test
    void deleteCompany_ShouldReturnSuccessMessage() {
        // Act
        ResponseEntity<String> response = companyController.deleteCompany(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Company deleted successfully", response.getBody());

        // Verify
        verify(companyService, times(1)).deleteCompany(1L);
    }
}
