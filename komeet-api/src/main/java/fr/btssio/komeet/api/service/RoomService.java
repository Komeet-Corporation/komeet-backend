package fr.btssio.komeet.api.service;

import fr.btssio.komeet.api.dto.RoomDto;
import fr.btssio.komeet.api.mapper.RoomMapper;
import fr.btssio.komeet.common.repository.RoomRepository;
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
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }
}
