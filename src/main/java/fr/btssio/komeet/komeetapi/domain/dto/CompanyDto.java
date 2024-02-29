package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyDto {

    private String email;
    private UUID uuid;
    private RoleDto role;
    private String name;
    private String phone;
    private List<RoomDto> rooms;

    public CompanyDto(String email, UUID uuid, RoleDto role, String name, String phone) {
        this.email = email;
        this.uuid = uuid;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }
}
