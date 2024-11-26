package fr.btssio.komeet.api.controller;

import fr.btssio.komeet.api.domain.dto.RoleDto;
import fr.btssio.komeet.api.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public ResponseEntity<RoleDto> getRoleUser() {
        try {
            RoleDto role = roleService.getUserRole();
            log.info("Role USER has been requested");
            return ResponseEntity.status(HttpStatus.OK).body(role);
        } catch (Exception e) {
            log.error("Error while getting role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
