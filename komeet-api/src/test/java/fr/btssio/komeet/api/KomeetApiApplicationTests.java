package fr.btssio.komeet.api;

import fr.btssio.komeet.common.data.Role;
import fr.btssio.komeet.common.data.User;
import fr.btssio.komeet.common.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
class KomeetApiApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        List<User> users = createUsers();
        when(userRepository.findAll()).thenReturn(users);
    }

    private @NotNull @Unmodifiable List<User> createUsers() {
        User user = new User();
        user.setEmail("test@test.test");
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setRole(createRole());
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setFavorites(new ArrayList<>());
        return List.of(user);
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