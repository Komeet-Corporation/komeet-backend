package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {

    private UUID uuid;
    private String label;
}
