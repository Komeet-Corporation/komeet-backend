package fr.btssio.komeet.api.controller;

import fr.btssio.komeet.api.dto.UserDto;
import fr.btssio.komeet.api.exception.ConflictException;
import fr.btssio.komeet.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        try {
            UserDto userDto = userService.findByEmail(email);
            log.info("Request body : {}", userDto.toString());
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ConflictException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Error during getting user by email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> insert(@RequestParam String password, @RequestBody UserDto userDto) {
        try {
            userService.insert(userDto, password);
            log.info("New user {}", userDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ConflictException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Error during user's inscription.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/favorite")
    public ResponseEntity<Void> favorite(@RequestParam String uuidUser, @RequestParam String uuidRoom) {
        try {
            String action = userService.favorite(uuidUser, uuidRoom);
            log.info("User {} {} favorite Room {}", uuidUser, action, uuidRoom);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ConflictException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Error during favorite for User {}", uuidUser, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<UserDto> verify(@RequestParam String email, @RequestParam String password) {
        try {
            UserDto userDto = userService.verify(email, password);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ConflictException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Error during verify for User {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
