package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.dto.RoomDto;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDto> findAll() {
        return this.roomRepository.findAll().stream().map(RoomDto::bindFromRoom).toList();
    }
}
