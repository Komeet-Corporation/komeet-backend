package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.mapper.EquipmentMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.ImageMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.RoomMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.UserMapper;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import fr.btssio.komeet.komeetapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final UserMapper userMapper = new UserMapper(roomMapper);
    private final UserService userService = new UserService(userRepository, userMapper);
    private final UserController userController = new UserController(userService);

    @Test
    void getByEmail() {
        Optional<User> user = createUser();
        when(userRepository.findById("test@test.test")).thenReturn(user);

        UserDto notNull = userController.getByEmail("test@test.test");
        UserDto isNull = userController.getByEmail("test");

        assertNotNull(notNull);
        assertNull(isNull);
    }

    private @NotNull Optional<User> createUser() {
        User user = new User();
        user.setEmail("test@test.test");
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setFavorites(new ArrayList<>());
        return Optional.of(user);
    }
}