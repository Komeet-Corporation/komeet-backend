package fr.btssio.komeet.komeetapi.dto;

import fr.btssio.komeet.komeetapi.data.Equipment;
import lombok.Data;

@Data
public class EquipmentDto {

    private Long id;
    private String label;

    public static EquipmentDto bindFromEquipment(Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setId(equipment.getId());
        equipmentDto.setLabel(equipment.getLabel());
        return equipmentDto;
    }
}
