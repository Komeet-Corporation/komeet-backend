package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.data.Company;
import fr.btssio.komeet.komeetapi.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.repository.CompanyRepository;
import fr.btssio.komeet.komeetapi.service.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyControllerTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final CompanyService companyService = new CompanyService(companyRepository);
    private final CompanyController companyController = new CompanyController(companyService);

    @Test
    void getByEmail() {
        Optional<Company> company = createCompany();
        when(companyRepository.findById("test@test.test")).thenReturn(company);

        CompanyDto notNull = companyController.getByEmail("test@test.test");
        CompanyDto isNull = companyController.getByEmail("test");

        assertNotNull(notNull);
        assertNull(isNull);
    }

    private Optional<Company> createCompany() {
        Company company = new Company();
        company.setEmail("test@test.test");
        company.setName("Test");
        company.setPhone("0000000000");
        company.setRooms(new ArrayList<>());
        return Optional.of(company);
    }
}
