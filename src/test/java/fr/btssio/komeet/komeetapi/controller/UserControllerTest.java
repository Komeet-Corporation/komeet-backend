package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.mapper.*;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import fr.btssio.komeet.komeetapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final RoleMapper roleMapper = new RoleMapper();
    private final UserMapper userMapper = new UserMapper(roomMapper, roleMapper);
    private final UserService userService = new UserService(userRepository, userMapper, roleRepository);
    private final UserController userController = new UserController(userService);

    @Test
    void getByEmail() {
        Optional<User> user = createUser();
        when(userRepository.findById("test@test.test")).thenReturn(user);

        ResponseEntity<UserDto> code200 = userController.getByEmail("test@test.test");
        ResponseEntity<UserDto> code409 = userController.getByEmail("test");

        assertEquals(HttpStatus.OK, code200.getStatusCode());
        assertEquals(HttpStatus.CONFLICT, code409.getStatusCode());
    }

    private @NotNull Optional<User> createUser() {
        User user = new User();
        user.setEmail("test@test.test");
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setRole(createRole());
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setFavorites(new ArrayList<>());
        return Optional.of(user);
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