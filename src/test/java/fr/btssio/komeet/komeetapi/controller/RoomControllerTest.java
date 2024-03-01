package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.Equipment;
import fr.btssio.komeet.komeetapi.domain.data.Image;
import fr.btssio.komeet.komeetapi.domain.data.Room;
import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import fr.btssio.komeet.komeetapi.domain.mapper.EquipmentMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.ImageMapper;
import fr.btssio.komeet.komeetapi.domain.mapper.RoomMapper;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.service.RoomService;
import org.hibernate.JDBCException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomControllerTest {

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final RoomService roomService = new RoomService(roomRepository, roomMapper);
    private final RoomController roomController = new RoomController(roomService);

    @Test
    void findAll() {
        when(roomRepository.findAll()).thenReturn(createRooms());

        ResponseEntity<List<RoomDto>> code200 = roomController.getAll();

        assertEquals(HttpStatus.OK, code200.getStatusCode());
        assertEquals(1, Objects.requireNonNull(code200.getBody()).size());
    }

    @Test
    void findAll_exception() {
        when(roomRepository.findAll()).thenThrow(JDBCException.class);

        ResponseEntity<List<RoomDto>> code500 = roomController.getAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, code500.getStatusCode());
    }

    private @NotNull @Unmodifiable List<Room> createRooms() {
        Room room = new Room();
        room.setId(1L);
        room.setUuid(String.valueOf(UUID.randomUUID()));
        room.setCompany("Test");
        room.setName("Test");
        room.setStreet("Test");
        room.setCity("Test");
        room.setZipCode("Test");
        room.setLatitude(0.0);
        room.setLongitude(0.0);
        room.setDescription("Test");
        room.setPriceHour(0L);
        room.setPriceHalfDay(0L);
        room.setPriceDay(0L);
        room.setMaxPeople(0L);
        room.setArea(0L);
        room.setDateCreated(String.valueOf(LocalDate.now()));
        room.setImages(createImages());
        room.setEquipments(createEquipments());
        return List.of(room);
    }

    private @NotNull @Unmodifiable List<Equipment> createEquipments() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setUuid(String.valueOf(UUID.randomUUID()));
        equipment.setLabel("RJ45");
        equipment.setRooms(new ArrayList<>());
        return List.of(equipment);
    }

    private @NotNull @Unmodifiable List<Image> createImages() {
        Image image = new Image();
        image.setId(1L);
        image.setUuid(String.valueOf(UUID.randomUUID()));
        image.setPath("PATH");
        image.setRoom(null);
        return List.of(image);
    }
}