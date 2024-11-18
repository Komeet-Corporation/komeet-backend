package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.config.SecurityConfig;
import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.exception.ConflictException;
import fr.btssio.komeet.komeetapi.domain.mapper.UserMapper;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.repository.RoomRepository;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoomRepository roomRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository,
                       RoomRepository roomRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roomRepository = roomRepository;
        this.encoder = bCryptPasswordEncoder;
    }

    public UserDto findByEmail(String email) throws ConflictException {
        User user = userRepository.findById(email).orElseThrow(() -> new ConflictException("User doesn't exist : " + email));
        return userMapper.toDto(user);
    }

    public void insert(@NotNull UserDto userDto, String password) throws ConflictException {
        if (userRepository.existsById(userDto.getEmail()))
            throw new ConflictException("User already exist : " + userDto);
        Role role = roleRepository.findByUuid(String.valueOf(userDto.getRole().getUuid()));
        User user = userMapper.toUser(userDto, role, encoder.encode(password));
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

    public UserDto verify(String email, String password) throws ConflictException {
        Optional<User> optional = userRepository.findById(email);
        if (optional.isEmpty() || !encoder.matches(password, optional.get().getPassword())) {
            throw new ConflictException("User cannot be verified : " + email);
        }
        return userMapper.toDto(optional.get());
    }

    /**
     * Don't use it for features development
     * Method implemented by UserDetailsService for spring security
     *
     * @param email the username identifying the user whose data is required.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findById(email);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exist : " + email);
        }
        User user = optional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(getMaxRole(user.getAuthorities()))
                .build();
    }

    private String getMaxRole(@NotNull Collection<Role> roles) {
        return roles.stream()
                .max(Comparator.comparing(Role::getLevel))
                .map(Role::getAuthority)
                .orElse(SecurityConfig.Role.UNKNOWN.name());
    }
}
