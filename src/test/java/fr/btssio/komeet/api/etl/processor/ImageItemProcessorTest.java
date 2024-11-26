package fr.btssio.komeet.api.etl.processor;

import fr.btssio.komeet.api.domain.data.Image;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageItemProcessorTest {

    private final ImageItemProcessor processor = new ImageItemProcessor();

    @Test
    void process() {
        Image image = createImage();

        String result = processor.process(image);

        assertEquals("INSERT INTO image (id, uuid, path, room) VALUES (1, 'd12ef486-f80b-4558-b294-5e351b0e86f2', 'path://', 1);", result);
    }

    private @NotNull Image createImage() {
        Image image = new Image();
        image.setId(1L);
        image.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        image.setPath("path://");
        image.setRoom(1L);
        return image;
    }
}