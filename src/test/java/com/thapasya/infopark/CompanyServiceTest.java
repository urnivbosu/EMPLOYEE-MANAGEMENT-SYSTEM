package com.thapasya.infopark;

import com.thapasya.infopark.models.Company;
import com.thapasya.infopark.repository.CompanyRepository;
import com.thapasya.infopark.service.CompanyService;
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
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company();
        testCompany.setId(1L);
        testCompany.setName("Verteil");
        testCompany.setLocation("Thapasya Infopark");

        // Use lenient to avoid unnecessary stubbing errors
        lenient().when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));
    }

    @Test
    void testAddCompany() {
        when(companyRepository.save(any(Company.class))).thenReturn(testCompany);

        Company savedCompany = companyService.addCompany(testCompany);

        assertNotNull(savedCompany);
        assertEquals("Verteil", savedCompany.getName());
        assertEquals("Thapasya Infopark", savedCompany.getLocation());

        verify(companyRepository, times(1)).save(testCompany);
    }

    @Test
    void testGetAllCompanies() {
        when(companyRepository.findAll()).thenReturn(List.of(testCompany));

        List<Company> companies = companyService.getAllCompanies();

        assertNotNull(companies);
        assertEquals(1, companies.size());
        assertEquals("Verteil", companies.get(0).getName());

        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testGetCompanyById_Found() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));

        Company foundCompany = companyService.getCompanyById(1L);

        assertNotNull(foundCompany);
        assertEquals("Verteil", foundCompany.getName());

        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCompanyById_NotFound() {
        when(companyRepository.findById(2L)).thenReturn(Optional.empty());

        Company foundCompany = companyService.getCompanyById(2L);

        assertNull(foundCompany);

        verify(companyRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateCompany() {
        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Verteil");
        updatedCompany.setLocation("New Infopark");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        Company result = companyService.updateCompany(1L, updatedCompany);

        assertNotNull(result);
        assertEquals("Updated Verteil", result.getName());
        assertEquals("New Infopark", result.getLocation());

        verify(companyRepository, times(1)).findById(1L);
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void testUpdateCompany_NotFound() {
        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Verteil");
        updatedCompany.setLocation("New Infopark");

        when(companyRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.updateCompany(2L, updatedCompany);
        });

        assertEquals("Company not found", exception.getMessage());

        verify(companyRepository, times(1)).findById(2L);
        verify(companyRepository, times(0)).save(any(Company.class));
    }

    @Test
    void testDeleteCompany_Success() {
        when(companyRepository.existsById(1L)).thenReturn(true);
        doNothing().when(companyRepository).deleteById(1L);

        boolean deleted = companyService.deleteCompany(1L);

        assertTrue(deleted);

        verify(companyRepository, times(1)).existsById(1L);
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCompany_NotFound() {
        when(companyRepository.existsById(2L)).thenReturn(false);

        boolean deleted = companyService.deleteCompany(2L);

        assertFalse(deleted);

        verify(companyRepository, times(1)).existsById(2L);
        verify(companyRepository, times(0)).deleteById(anyLong());
    }
}
