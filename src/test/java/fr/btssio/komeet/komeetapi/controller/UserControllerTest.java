package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.data.*;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.mapper.*;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import fr.btssio.komeet.komeetapi.service.UserService;
import org.hibernate.JDBCException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final EquipmentMapper equipmentMapper = new EquipmentMapper();
    private final ImageMapper imageMapper = new ImageMapper();
    private final RoomMapper roomMapper = new RoomMapper(imageMapper, equipmentMapper);
    private final RoleMapper roleMapper = new RoleMapper();
    private final UserMapper userMapper = new UserMapper(roomMapper, roleMapper);
    private final UserService userService = new UserService(userRepository, userMapper, roleRepository, roomRepository, mock(BCryptPasswordEncoder.class));
    private final UserController userController = new UserController(userService);

    @Test
    void getByEmail() {
        Optional<User> user = createUser();
        when(userRepository.findById("test@test.test")).thenReturn(user);

        ResponseEntity<UserDto> code200 = userController.getByEmail("test@test.test");
        ResponseEntity<UserDto> code409 = userController.getByEmail("test");

        assertEquals(HttpStatus.OK, code200.getStatusCode());
        assertEquals(HttpStatus.CONFLICT, code409.getStatusCode());
    }

    @Test
    void getByEmail_exception() {
        when(userRepository.findById(anyString())).thenThrow(JDBCException.class);

        ResponseEntity<UserDto> code500 = userController.getByEmail("test");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, code500.getStatusCode());
    }

    @Test
    void insert() {
        Optional<User> user = createUser();
        if (user.isEmpty()) throw new TestAbortedException();
        UserDto userDto = userMapper.toDto(user.get());
        when(userRepository.existsById(anyString())).thenReturn(false);

        ResponseEntity<Void> code200 = userController.insert("password", userDto);

        assertEquals(HttpStatus.OK, code200.getStatusCode());
    }

    @Test
    void insert_conflict_exception() {
        Optional<User> user = createUser();
        if (user.isEmpty()) throw new TestAbortedException();
        UserDto userDto = userMapper.toDto(user.get());
        when(userRepository.existsById(anyString())).thenReturn(true);

        ResponseEntity<Void> code409 = userController.insert("password", userDto);

        assertEquals(HttpStatus.CONFLICT, code409.getStatusCode());
    }

    @Test
    void insert_exception() {
        Optional<User> user = createUser();
        if (user.isEmpty()) throw new TestAbortedException();
        UserDto userDto = userMapper.toDto(user.get());
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenThrow(JDBCException.class);

        ResponseEntity<Void> code500 = userController.insert("password", userDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, code500.getStatusCode());
    }

    @Test
    void add_favorite() {
        Optional<User> user = createUser();
        Optional<Room> room = createRoom();
        if (user.isEmpty() || room.isEmpty()) throw new TestAbortedException();
        when(userRepository.existsByUuid(anyString())).thenReturn(true);
        when(roomRepository.existsByUuid(anyString())).thenReturn(true);

        ResponseEntity<Void> add200 = userController.favorite(user.get().getUuid(), room.get().getUuid());

        assertEquals(HttpStatus.OK, add200.getStatusCode());
    }

    @Test
    void remove_favorite() {
        Optional<User> user = createUser();
        Optional<Room> room = createRoom();
        if (user.isEmpty() || room.isEmpty()) throw new TestAbortedException();
        when(userRepository.existsByUuid(anyString())).thenReturn(true);
        when(userRepository.findEmailByUuid(anyString())).thenReturn(user.get().getEmail());
        when(userRepository.existsFavorite(anyString(), anyLong())).thenReturn(true);
        when(roomRepository.existsByUuid(anyString())).thenReturn(true);
        when(roomRepository.findIdByUuid(anyString())).thenReturn(room.get().getId());

        ResponseEntity<Void> add200 = userController.favorite(user.get().getUuid(), room.get().getUuid());

        assertEquals(HttpStatus.OK, add200.getStatusCode());
    }

    @Test
    void favorite_conflict_exception() {
        when(userRepository.existsByUuid(anyString())).thenReturn(false);
        when(roomRepository.existsByUuid(anyString())).thenReturn(false);

        ResponseEntity<Void> code409 = userController.favorite(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertEquals(HttpStatus.CONFLICT, code409.getStatusCode());
    }

    @Test
    void favorite_exception() {
        when(userRepository.existsByUuid(anyString())).thenThrow(JDBCException.class);

        ResponseEntity<Void> code500 = userController.favorite(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, code500.getStatusCode());
    }

    private @NotNull Optional<User> createUser() {
        User user = new User();
        user.setEmail("test@test.test");
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setRole(createRole());
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setFavorites(new ArrayList<>());
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

    private @NotNull Optional<Room> createRoom() {
        Room room = new Room();
        room.setId(1L);
        room.setUuid(String.valueOf(UUID.randomUUID()));
        room.setCompany("Test");
        room.setName("Test");
        room.setStreet("Test");
        room.setCity("Test");
        room.setZipCode("Test");
        room.setLatitude(0.0);
        room.setLongitude(0.0);
        room.setDescription("Test");
        room.setPriceHour(0L);
        room.setPriceHalfDay(0L);
        room.setPriceDay(0L);
        room.setMaxPeople(0L);
        room.setArea(0L);
        room.setDateCreated(String.valueOf(LocalDate.now()));
        room.setImages(createImages());
        room.setEquipments(createEquipments());
        return Optional.of(room);
    }

    private @NotNull @Unmodifiable List<Equipment> createEquipments() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setUuid(String.valueOf(UUID.randomUUID()));
        equipment.setLabel("RJ45");
        equipment.setRooms(new ArrayList<>());
        return List.of(equipment);
    }

    private @NotNull @Unmodifiable List<Image> createImages() {
        Image image = new Image();
        image.setId(1L);
        image.setUuid(String.valueOf(UUID.randomUUID()));
        image.setPath("PATH");
        image.setRoom(null);
        return List.of(image);
    }
}