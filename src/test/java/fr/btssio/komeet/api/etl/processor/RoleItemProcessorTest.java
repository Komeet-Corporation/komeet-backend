package fr.btssio.komeet.api.etl.processor;

import fr.btssio.komeet.api.domain.data.Role;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleItemProcessorTest {

    private final RoleItemProcessor processor = new RoleItemProcessor();

    @Test
    void process() {
        Role role = createRole();

        String result = processor.process(role);

        assertEquals("INSERT INTO role (id, uuid, label, level) VALUES (1, 'd12ef486-f80b-4558-b294-5e351b0e86f2', 'UNKNOWN', 1);", result);
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        role.setLabel("UNKNOWN");
        role.setLevel(1L);
        return role;
    }
}