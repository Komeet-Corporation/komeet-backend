package fr.btssio.komeet.etl.processor;

import fr.btssio.komeet.common.data.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class EquipmentItemProcessor implements ItemProcessor<Equipment, String> {

    @Override
    public String process(@NotNull Equipment equipment) {
        return String.format("INSERT INTO equipment (id, uuid, label) VALUES (%d, '%s', \"%s\");",
                equipment.getId(),
                equipment.getUuid(),
                equipment.getLabel());
    }
}
