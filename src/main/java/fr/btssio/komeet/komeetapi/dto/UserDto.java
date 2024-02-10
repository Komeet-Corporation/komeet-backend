package fr.btssio.komeet.komeetapi.dto;

import fr.btssio.komeet.komeetapi.data.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;
    private List<RoomDto> favorites;

    public static UserDto bindFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        List<RoomDto> favorites = user.getFavorites().stream().map(RoomDto::bindFromRoom).toList();
        userDto.setFavorites(favorites);
        return userDto;
    }
}
