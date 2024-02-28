package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.Company;
import fr.btssio.komeet.komeetapi.domain.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.domain.mapper.CompanyMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.EquipmentMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.ImageMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.RoomMapper;
import fr.btssio.komeet.komeetapi.repository.CompanyRepository;
import fr.btssio.komeet.komeetapi.service.CompanyService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyControllerTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final CompanyMapper companyMapper = new CompanyMapper(roomMapper);
    private final CompanyService companyService = new CompanyService(companyRepository, companyMapper);
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

    private @NotNull Optional<Company> createCompany() {
        Company company = new Company();
        company.setEmail("test@test.test");
        company.setName("Test");
        company.setPhone("0000000000");
        company.setRooms(new ArrayList<>());
        return Optional.of(company);
    }
}
