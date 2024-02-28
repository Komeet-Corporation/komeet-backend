package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private String email;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private List<RoomDto> favorites;
}
