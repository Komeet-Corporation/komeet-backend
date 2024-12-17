package fr.btssio.komeet.api.mapper;

import fr.btssio.komeet.api.dto.UserDto;
import fr.btssio.komeet.common.data.Role;
import fr.btssio.komeet.common.data.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private final ImageMapper imageMapper = new ImageMapper();
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final RoleMapper roleMapper = new RoleMapper();
    private final UserMapper userMapper = new UserMapper(roomMapper, roleMapper);

    @Test
    void toUser() {
        UUID uuid = UUID.randomUUID();
        UserDto userDto = createUserDto();
        userDto.setUuid(uuid);

        User user = userMapper.toUser(userDto, new Role(), "password");

        assertEquals(uuid.toString(), user.getUuid());
    }

    @Test
    void toUserUuidNull() {
        UserDto userDto = createUserDto();

        User user = userMapper.toUser(userDto, new Role(), "password");

        assertNotNull(user.getUuid());
    }

    private @NotNull UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.test");
        userDto.setFirstName("Test");
        userDto.setLastName("Test");
        userDto.setUuid(null);
        return userDto;
    }
}