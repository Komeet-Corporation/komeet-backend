package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyDto {

    private String email;
    private UUID uuid;
    private String name;
    private String phone;
    private List<RoomDto> rooms;

    public CompanyDto(String email, UUID uuid, String name, String phone) {
        this.email = email;
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
    }
}
