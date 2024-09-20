package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Room;
import fr.btssio.komeet.komeetapi.domain.data.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FavoriteItemProcessorTest {

    private final FavoriteItemProcessor favoriteItemProcessor = new FavoriteItemProcessor();

    @Test
    void process() {
        User user = createUser();

        String result = favoriteItemProcessor.process(user);

        assertEquals("INSERT INTO favorite (user, room) VALUES ('test@test.com', 1);", result);
    }

    private @NotNull User createUser() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFavorites(createRooms());
        return user;
    }

    private @NotNull List<Room> createRooms() {
        List<Room> rooms = new ArrayList<>();
        Room room = new Room();
        room.setId(1L);
        rooms.add(room);
        return rooms;
    }
}