package fr.btssio.komeet.api.etl.processor;

import fr.btssio.komeet.api.domain.data.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, String> {

    @Override
    public String process(@NotNull User user) {
        return String.format("INSERT INTO user (email, uuid, role, password, firstName, lastName) VALUES ('%s', '%s', " +
                        "%d, '%s', '%s', '%s');",
                user.getEmail(),
                user.getUuid(),
                user.getRole().getId(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName());
    }
}
