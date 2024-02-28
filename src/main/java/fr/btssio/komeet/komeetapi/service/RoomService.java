package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import fr.btssio.komeet.komeetapi.domain.mapper.RoomMapper;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public List<RoomDto> findAll() {
        return this.roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }
}
