package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.exception.ConflictException;
import fr.btssio.komeet.komeetapi.domain.mapper.UserMapper;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoomRepository roomRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository,
                       RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roomRepository = roomRepository;
    }

    public UserDto findByEmail(String email) throws ConflictException {
        User user = this.userRepository.findById(email).orElseThrow(() -> new ConflictException("User doesn't exist : " + email));
        return userMapper.toDto(user);
    }

    public void insert(@NotNull UserDto userDto, String password) throws ConflictException {
        if (userRepository.existsById(userDto.getEmail()))
            throw new ConflictException("User already exist : " + userDto);
        Role role = roleRepository.findByUuid(String.valueOf(userDto.getRole().getUuid()));
        User user = userMapper.toUser(userDto, role, password);
        userRepository.save(user);
    }

    public String favorite(String uuidUser, String uuidRoom) throws ConflictException {
        if (!userRepository.existsByUuid(uuidUser) && !roomRepository.existsByUuid(uuidRoom))
            throw new ConflictException("User or Room doesn't exist");
        String emailUser = userRepository.findEmailByUuid(uuidUser);
        Long idRoom = roomRepository.findIdByUuid(uuidRoom);
        if (userRepository.existsFavorite(emailUser, idRoom)) {
            userRepository.removeFavorite(emailUser, idRoom);
            return "removed";
        } else {
            userRepository.addFavorite(emailUser, idRoom);
            return "added";
        }
    }
}
