package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class EquipItemProcessor implements ItemProcessor<Room, String> {

    @Override
    public String process(@NotNull Room room) {

        List<String> requests = room.getEquipments()
                .stream()
                .map(e -> String.format("INSERT INTO equip (equipment, room) VALUES (%d, %d);", e.getId(), room.getId()))
                .toList();

        return String.join("\n", requests);
    }
}
