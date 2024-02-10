package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.data.User;
import fr.btssio.komeet.komeetapi.dto.UserDto;
import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findByEmail(String email) {
        User user = this.userRepository.findById(email).orElse(null);
        return user != null ? UserDto.bindFromUser(user) : null;
    }
}
