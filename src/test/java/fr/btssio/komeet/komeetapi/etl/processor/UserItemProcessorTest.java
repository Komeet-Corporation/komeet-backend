package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.data.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserItemProcessorTest {

    private final UserItemProcessor processor = new UserItemProcessor();

    @Test
    void process() {
        User user = createUser();

        String result = processor.process(user);

        assertEquals("INSERT INTO user (email, uuid, role, password, firstName, lastName) VALUES ('test@test.com', 'd12ef486-f80b-4558-b294-5e351b0e86f2', 1, 'test', 'Test', 'Test');", result);
    }

    private @NotNull User createUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        user.setRole(createRole());
        user.setPassword("test");
        user.setFirstName("Test");
        user.setLastName("Test");
        return user;
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        return role;
    }
}