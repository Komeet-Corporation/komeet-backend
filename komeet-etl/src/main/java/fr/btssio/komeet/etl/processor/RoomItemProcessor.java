package fr.btssio.komeet.etl.processor;

import fr.btssio.komeet.common.data.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class RoomItemProcessor implements ItemProcessor<Room, String> {

    @Override
    public String process(@NotNull Room room) {
        return String.format("INSERT INTO room (id, uuid, company, name, street, city, zipCode, latitude, longitude, " +
                        "description, priceHour, priceHalfDay, priceDay, maxPeople, area, dateCreated) VALUES (%d, '%s', \"%s\", " +
                        "\"%s\", \"%s\", \"%s\", \"%s\", %.0f, %.0f, \"%s\", %d, %d, %d, %d, %d, '%s');",
                room.getId(),
                room.getUuid(),
                room.getCompany(),
                room.getName(),
                room.getStreet(),
                room.getCity(),
                room.getZipCode(),
                room.getLatitude(),
                room.getLongitude(),
                room.getDescription(),
                room.getPriceHour(),
                room.getPriceHalfDay(),
                room.getPriceDay(),
                room.getMaxPeople(),
                room.getArea(),
                room.getDateCreated());
    }
}
