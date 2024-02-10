package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.dto.RoomDto;
import fr.btssio.komeet.komeetapi.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {

    private static final String PATH = "/room/";

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(PATH)
    public List<RoomDto> getAll() {
        return this.roomService.findAll();
    }
}
