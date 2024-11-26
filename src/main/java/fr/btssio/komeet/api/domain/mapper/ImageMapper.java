package fr.btssio.komeet.api.domain.mapper;

import fr.btssio.komeet.api.domain.data.Image;
import fr.btssio.komeet.api.domain.dto.ImageDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImageMapper {

    public ImageMapper() {
        /// No property(ies) needed
        /// If you need to add more, delete this comment
    }

    public ImageDto toDto(@NotNull Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setUuid(UUID.fromString(image.getUuid()));
        imageDto.setPath(image.getPath());
        return imageDto;
    }
}
