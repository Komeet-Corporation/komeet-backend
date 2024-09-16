package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Image;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class ImageItemProcessor implements ItemProcessor<Image, String> {

    @Override
    public String process(@NotNull Image image) {
        return String.format("INSERT INTO image (id, uuid, path) VALUES (%d, '%s', '%s');",
                image.getId(),
                image.getUuid(),
                image.getPath());
    }
}
