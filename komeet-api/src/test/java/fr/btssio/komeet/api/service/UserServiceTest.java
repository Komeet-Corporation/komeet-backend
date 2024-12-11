package fr.btssio.komeet.api.service;

import fr.btssio.komeet.api.mapper.*;
import fr.btssio.komeet.common.data.Role;
import fr.btssio.komeet.common.data.User;
import fr.btssio.komeet.common.repository.RoleRepository;
import fr.btssio.komeet.common.repository.RoomRepository;
import fr.btssio.komeet.common.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final RoleMapper roleMapper = new RoleMapper();
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final UserMapper userMapper = new UserMapper(roomMapper, roleMapper);
    private final UserService userService = new UserService(userRepository, userMapper, roleRepository, roomRepository, encoder);

    @Test
    void loadUserByUsername_exception() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));
    }

    @Test
    void loadUserByUsername_one() {
        Optional<User> user = createUser();
        when(userRepository.findById(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.test");

        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    private @NotNull Optional<User> createUser() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("test@test.test");
        when(user.getPassword()).thenReturn("test");
        when(user.getRole()).thenReturn(createRole());
        return Optional.of(user);
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(String.valueOf(UUID.randomUUID()));
        role.setLabel("USER");
        role.setLevel(8979798797987987L);
        return role;
    }
}