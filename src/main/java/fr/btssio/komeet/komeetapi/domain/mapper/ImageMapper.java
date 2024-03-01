package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.Image;
import fr.btssio.komeet.komeetapi.domain.dto.ImageDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImageMapper {

    public ImageMapper() {
    }

    public ImageDto toDto(@NotNull Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setUuid(UUID.fromString(image.getUuid()));
        imageDto.setPath(image.getPath());
        return imageDto;
    }
}
