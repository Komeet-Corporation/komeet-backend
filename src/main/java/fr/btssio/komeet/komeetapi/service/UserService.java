package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.domain.data.User;
import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.mapper.UserMapper;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto findByEmail(String email) {
        User user = this.userRepository.findById(email).orElse(null);
        return user != null ? userMapper.toDto(user) : null;
    }
}
