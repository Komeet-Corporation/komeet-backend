package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RoleItemProcessor implements ItemProcessor<Role, String> {

    @Override
    public String process(@NotNull Role role) {
        return String.format("INSERT INTO role (id, uuid, label, level) VALUES (%d, '%s', '%s', %d);",
                role.getId(), role.getUuid(), role.getLabel(), role.getLevel());
    }
}
