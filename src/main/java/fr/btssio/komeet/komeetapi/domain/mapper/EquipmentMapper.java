package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.Equipment;
import fr.btssio.komeet.komeetapi.domain.dto.EquipmentDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    public EquipmentMapper() {
    }

    public EquipmentDto toDto(@NotNull Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setUuid(equipment.getUuid());
        equipmentDto.setLabel(equipment.getLabel());
        return equipmentDto;
    }
}
