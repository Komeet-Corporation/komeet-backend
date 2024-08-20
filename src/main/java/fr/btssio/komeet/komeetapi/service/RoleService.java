package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.domain.dto.RoleDto;
import fr.btssio.komeet.komeetapi.domain.mapper.RoleMapper;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private static final String USER_ROLE_LABEL = "USER";

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleDto getUserRole() {
        Role role = roleRepository.findByLabel(USER_ROLE_LABEL).orElseThrow();
        return roleMapper.toDto(role);
    }
}
