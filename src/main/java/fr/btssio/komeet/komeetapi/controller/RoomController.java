package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import fr.btssio.komeet.komeetapi.service.RoomService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll() {
        try {
            List<RoomDto> rooms = roomService.findAll();
            LogUtils.logInfo(this.getClass(), "All rooms have been recovered");
            return ResponseEntity.status(HttpStatus.OK).body(rooms);
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during getting all rooms", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
