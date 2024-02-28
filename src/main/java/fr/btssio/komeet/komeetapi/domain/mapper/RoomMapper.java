package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.Room;
import fr.btssio.komeet.komeetapi.domain.dto.EquipmentDto;
import fr.btssio.komeet.komeetapi.domain.dto.ImageDto;
import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RoomMapper {

    private final ImageMapper imageMapper;
    private final EquipmentMapper equipmentMapper;

    public RoomMapper(ImageMapper imageMapper, EquipmentMapper equipmentMapper) {
        this.imageMapper = imageMapper;
        this.equipmentMapper = equipmentMapper;
    }

    public RoomDto toDto(@NotNull Room room) {
        RoomDto roomDto = new RoomDto(room.getId(), room.getName(), room.getStreet(), room.getCity(), room.getZipCode(),
                room.getLatitude(), room.getLongitude(), room.getDescription(), room.getPriceHour(), room.getPriceHalfDay(),
                room.getPriceDay(), room.getMaxPeople(), room.getArea(), LocalDate.parse(room.getDateCreated()));
        List<ImageDto> images = room.getImages().stream().map(imageMapper::toDto).toList();
        List<EquipmentDto> equipments = room.getEquipments().stream().map(equipmentMapper::toDto).toList();
        roomDto.setImages(images);
        roomDto.setEquipments(equipments);
        return roomDto;
    }
}
