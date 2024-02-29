package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.Company;
import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.domain.mapper.*;
import fr.btssio.komeet.komeetapi.repository.CompanyRepository;
import fr.btssio.komeet.komeetapi.service.CompanyService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyControllerTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final RoleMapper roleMapper = new RoleMapper();
    private final CompanyMapper companyMapper = new CompanyMapper(roomMapper, roleMapper);
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
        company.setUuid(UUID.randomUUID());
        company.setRole(createRole());
        company.setName("Test");
        company.setPhone("0000000000");
        company.setRooms(new ArrayList<>());
        return Optional.of(company);
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(UUID.randomUUID());
        role.setLabel("USER");
        role.setLevel(8979798797987987L);
        return role;
    }
}
