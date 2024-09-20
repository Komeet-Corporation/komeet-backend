package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Room;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomItemProcessorTest {

    private final RoomItemProcessor processor = new RoomItemProcessor();

    @Test
    void process() {
        Room room = createRoom();

        String expected = "INSERT INTO room (id, uuid, company, name, street, city, zipCode, latitude, longitude, description, priceHour, priceHalfDay, priceDay, maxPeople, area, dateCreated) VALUES (1, 'd12ef486-f80b-4558-b294-5e351b0e86f2', \"company\", \"name\", \"street\", \"city\", \"zipCode\", 3, 5, \"description\", 1, 2, 3, 4, 5, '2024-09-18 12:34:35');";
        String result = processor.process(room);

        assertEquals(expected, result);
    }

    private @NotNull Room createRoom() {
        Room room = new Room();
        room.setId(1L);
        room.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        room.setCompany("company");
        room.setName("name");
        room.setStreet("street");
        room.setCity("city");
        room.setZipCode("zipCode");
        room.setLatitude(3.0032);
        room.setLongitude(5.0032);
        room.setDescription("description");
        room.setPriceHour(1L);
        room.setPriceHalfDay(2L);
        room.setPriceDay(3L);
        room.setMaxPeople(4L);
        room.setArea(5L);
        room.setDateCreated("2024-09-18 12:34:35");
        return room;
    }
}