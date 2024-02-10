package fr.btssio.komeet.komeetapi.dto;

import fr.btssio.komeet.komeetapi.data.Image;
import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String path;

    public static ImageDto bindFromImage(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setPath(image.getPath());
        return imageDto;
    }
}
