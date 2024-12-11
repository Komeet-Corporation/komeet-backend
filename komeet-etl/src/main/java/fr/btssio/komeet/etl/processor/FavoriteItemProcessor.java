package fr.btssio.komeet.etl.processor;

import fr.btssio.komeet.common.data.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class FavoriteItemProcessor implements ItemProcessor<User, String> {

    @Override
    public String process(@NotNull User user) {

        List<String> requests = user.getFavorites()
                .stream()
                .map(r -> String.format("INSERT INTO favorite (user, room) VALUES ('%s', %d);", user.getEmail(), r.getId()))
                .toList();

        return String.join("\n", requests);
    }
}
