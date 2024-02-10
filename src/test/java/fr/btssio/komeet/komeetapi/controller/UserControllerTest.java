package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.data.User;
import fr.btssio.komeet.komeetapi.dto.UserDto;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import fr.btssio.komeet.komeetapi.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);
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

    private Optional<User> createUser() {
        User user = new User();
        user.setEmail("test@test.test");
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setFavorites(new ArrayList<>());
        return Optional.of(user);
    }
}