package fr.btssio.komeet.api.controller;

import fr.btssio.komeet.api.domain.data.Company;
import fr.btssio.komeet.api.domain.data.Role;
import fr.btssio.komeet.api.domain.dto.CompanyDto;
import fr.btssio.komeet.api.domain.mapper.*;
import fr.btssio.komeet.api.repository.CompanyRepository;
import fr.btssio.komeet.api.service.CompanyService;
import org.hibernate.JDBCException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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

        ResponseEntity<CompanyDto> code200 = companyController.getByEmail("test@test.test");
        ResponseEntity<CompanyDto> code409 = companyController.getByEmail("test");

        assertEquals(HttpStatus.OK, code200.getStatusCode());
        assertEquals(HttpStatus.CONFLICT, code409.getStatusCode());
    }

    @Test
    void getByEmailException() {
        when(companyRepository.findById(anyString())).thenThrow(JDBCException.class);

        ResponseEntity<CompanyDto> code500 = companyController.getByEmail("test");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, code500.getStatusCode());
    }

    private @NotNull Optional<Company> createCompany() {
        Company company = new Company();
        company.setEmail("test@test.test");
        company.setUuid(String.valueOf(UUID.randomUUID()));
        company.setRole(createRole());
        company.setName("Test");
        company.setPhone("0000000000");
        company.setRooms(new ArrayList<>());
        return Optional.of(company);
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(String.valueOf(UUID.randomUUID()));
        role.setLabel("USER");
        role.setLevel(8979798797987987L);
        return role;
    }
}
