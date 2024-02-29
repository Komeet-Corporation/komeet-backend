package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final RoomMapper roomMapper;
    private final RoleMapper roleMapper;

    public UserMapper(RoomMapper roomMapper, RoleMapper roleMapper) {
        this.roomMapper = roomMapper;
        this.roleMapper = roleMapper;
    }

    public UserDto toDto(@NotNull User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUuid(user.getUuid());
        userDto.setRole(roleMapper.toDto(user.getRole()));
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        List<RoomDto> favorites = user.getFavorites().stream().map(roomMapper::toDto).toList();
        userDto.setFavorites(favorites);
        return userDto;
    }
}
