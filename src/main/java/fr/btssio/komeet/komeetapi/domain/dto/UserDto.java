package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private String email;
    private UUID uuid;
    private RoleDto role;
    private String firstName;
    private String lastName;
    private List<RoomDto> favorites;

    @Override
    public String toString() {
        return "UserDto(email=" + email + ", uuid=" + uuid + ", role=" + role.toString() + ", firstName=" + firstName
                + ", lastName=" + lastName + ", favorites=" + favorites.size() + ")";
    }
}
