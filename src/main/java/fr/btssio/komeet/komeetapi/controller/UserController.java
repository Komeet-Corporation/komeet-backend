package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.dto.UserDto;
import fr.btssio.komeet.komeetapi.domain.exception.ConflictException;
import fr.btssio.komeet.komeetapi.service.UserService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            LogUtils.logInfo(this.getClass(), "Request body : " + userDto.toString());
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ConflictException e) {
            LogUtils.logWarn(this.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during getting user by email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> insert(@RequestParam String password, @RequestBody UserDto userDto) {
        try {
            userService.insert(userDto, password);
            LogUtils.logInfo(this.getClass(), "New user : " + userDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ConflictException e) {
            LogUtils.logWarn(this.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during user's inscription.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/favorite")
    public ResponseEntity<Void> favorite(@RequestParam String uuidUser, @RequestParam String uuidRoom) {
        try {
            String action = userService.favorite(uuidUser, uuidRoom);
            LogUtils.logInfo(this.getClass(), "User " + uuidUser + " " + action + " favorite Room " + uuidRoom);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ConflictException e) {
            LogUtils.logWarn(this.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during favorite for User " + uuidUser, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<UserDto> verify(@RequestParam String email, @RequestParam String password) {
        try {
            UserDto userDto = userService.verify(email, password);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (ConflictException e) {
            LogUtils.logWarn(this.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during verify for User " + email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
