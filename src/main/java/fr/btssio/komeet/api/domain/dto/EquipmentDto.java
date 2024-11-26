package fr.btssio.komeet.api.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EquipmentDto {

    private UUID uuid;
    private String label;
}
