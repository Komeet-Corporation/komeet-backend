package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.dto.RoleDto;
import fr.btssio.komeet.komeetapi.service.RoleService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            LogUtils.logInfo(this.getClass(), "Role USER has been requested");
            return ResponseEntity.status(HttpStatus.OK).body(role);
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error while getting role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
