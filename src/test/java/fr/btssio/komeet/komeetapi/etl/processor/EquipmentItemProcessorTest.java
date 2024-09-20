package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Equipment;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquipmentItemProcessorTest {

    private final EquipmentItemProcessor processor = new EquipmentItemProcessor();

    @Test
    void process() {
        Equipment equipment = createEquipment();

        String result = processor.process(equipment);

        assertEquals("INSERT INTO equipment (id, uuid, label) VALUES (1, 'd12ef486-f80b-4558-b294-5e351b0e86f2', \"WIFI\");", result);
    }

    private @NotNull Equipment createEquipment() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        equipment.setLabel("WIFI");
        return equipment;
    }
}