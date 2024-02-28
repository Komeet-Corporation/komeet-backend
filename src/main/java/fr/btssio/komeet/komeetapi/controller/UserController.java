package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String PATH = "/user/";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(PATH + "{email}")
    public UserDto getByEmail(@PathVariable String email) {
        return this.userService.findByEmail(email);
    }
}
