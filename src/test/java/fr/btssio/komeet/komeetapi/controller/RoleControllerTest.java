package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.dto.RoleDto;
import fr.btssio.komeet.komeetapi.domain.mapper.RoleMapper;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.service.RoleService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    private final RoleMapper roleMapper = new RoleMapper();
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoleService roleService = new RoleService(roleRepository, roleMapper);
    private final RoleController roleController = new RoleController(roleService);

    @Test
    void getRoleUser() {
        when(roleRepository.findByLabel(anyString())).thenReturn(createRole());

        ResponseEntity<RoleDto> response = roleController.getRoleUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserRoleException() {
        when(roleRepository.findByLabel(anyString())).thenReturn(Optional.empty());

        ResponseEntity<RoleDto> response = roleController.getRoleUser();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private @NotNull Optional<Role> createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(UUID.randomUUID().toString());
        role.setLabel("ROLE");
        role.setLevel(1L);
        return Optional.of(role);
    }
}