package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.mapper.*;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
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
    void loadUserByUsername_empty() {
        Optional<User> user = createUser(ListSize.EMPTY);
        when(userRepository.findById(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.test");

        assertEquals("ROLE_UNKNOWN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void loadUserByUsername_one() {
        Optional<User> user = createUser(ListSize.ONE);
        when(userRepository.findById(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.test");

        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void loadUserByUsername_several() {
        Optional<User> user = createUser(ListSize.SEVERAL);
        when(userRepository.findById(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.test");

        assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    private @NotNull Optional<User> createUser(ListSize listSize) {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test@test.test");
        when(user.getPassword()).thenReturn("test");
        if (listSize == ListSize.EMPTY) {
            when(user.getAuthorities()).thenReturn(createEmptyRole());
        } else if (listSize == ListSize.ONE) {
            when(user.getAuthorities()).thenReturn(createRole());
        } else {
            when(user.getAuthorities()).thenReturn(createRoles());
        }
        return Optional.of(user);
    }

    private @NotNull List<Role> createEmptyRole() {
        return new ArrayList<>(List.of());
    }

    private @NotNull List<Role> createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(String.valueOf(UUID.randomUUID()));
        role.setLabel("USER");
        role.setLevel(8979798797987987L);
        return List.of(role);
    }

    private @NotNull List<Role> createRoles() {
        Role role = new Role();
        role.setId(1L);
        role.setUuid(String.valueOf(UUID.randomUUID()));
        role.setLabel("USER");
        role.setLevel(0L);
        Role role2 = new Role();
        role2.setId(2L);
        role2.setUuid(String.valueOf(UUID.randomUUID()));
        role2.setLabel("ADMIN");
        role2.setLevel(1L);
        return List.of(role, role2);
    }

    private enum ListSize {
        EMPTY, ONE, SEVERAL
    }
}