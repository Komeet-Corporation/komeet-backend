package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {

    private UUID uuid;
    private String path;
}
