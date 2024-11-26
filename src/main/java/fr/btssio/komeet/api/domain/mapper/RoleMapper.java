package fr.btssio.komeet.api.domain.mapper;

import fr.btssio.komeet.api.domain.data.Role;
import fr.btssio.komeet.api.domain.dto.RoleDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleMapper {

    public RoleMapper() {
        /// No property(ies) needed
        /// If you need to add more, delete this comment
    }

    public RoleDto toDto(@NotNull Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setUuid(UUID.fromString(role.getUuid()));
        roleDto.setLabel(role.getLabel());
        return roleDto;
    }
}
