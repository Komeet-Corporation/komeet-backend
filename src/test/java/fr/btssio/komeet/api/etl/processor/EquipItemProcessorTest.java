package fr.btssio.komeet.api.etl.processor;

import fr.btssio.komeet.api.domain.data.Equipment;
import fr.btssio.komeet.api.domain.data.Room;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquipItemProcessorTest {

    private final EquipItemProcessor processor = new EquipItemProcessor();

    @Test
    void process() {
        Room room = createRoom();

        String result = processor.process(room);

        assertEquals("INSERT INTO equip (equipment, room) VALUES (1, 1);", result);
    }

    private @NotNull Room createRoom() {
        Room room = new Room();
        room.setId(1L);
        room.setEquipments(createEquipmentList());
        return room;
    }

    private @NotNull List<Equipment> createEquipmentList() {
        List<Equipment> equipmentList = new ArrayList<>();
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipmentList.add(equipment);
        return equipmentList;
    }
}