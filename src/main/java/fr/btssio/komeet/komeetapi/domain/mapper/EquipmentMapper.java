package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.Equipment;
import fr.btssio.komeet.komeetapi.domain.dto.EquipmentDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EquipmentMapper {

    public EquipmentMapper() {
        /// No property(ies) needed
        /// If you need to add more, delete this comment
    }

    public EquipmentDto toDto(@NotNull Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setUuid(UUID.fromString(equipment.getUuid()));
        equipmentDto.setLabel(equipment.getLabel());
        return equipmentDto;
    }
}
