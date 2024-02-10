package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.data.Equipment;
import fr.btssio.komeet.komeetapi.data.Image;
import fr.btssio.komeet.komeetapi.data.Room;
import fr.btssio.komeet.komeetapi.dto.RoomDto;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.service.RoomService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomControllerTest {

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomService roomService = new RoomService(roomRepository);
    private final RoomController roomController = new RoomController(roomService);

    @Test
    void findAll() {
        when(roomRepository.findAll()).thenReturn(createRooms());

        List<RoomDto> rooms = roomController.getAll();

        assertEquals(1, rooms.size());
    }

    private List<Room> createRooms() {
        Room room = new Room();
        room.setId(1L);
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

    private List<Equipment> createEquipments() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setLabel("RJ45");
        equipment.setRooms(new ArrayList<>());
        return List.of(equipment);
    }

    private List<Image> createImages() {
        Image image = new Image();
        image.setId(1L);
        image.setPath("PATH");
        image.setRoom(null);
        return List.of(image);
    }
}