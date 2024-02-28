package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private String email;
    private String name;
    private String phone;
    private List<RoomDto> rooms;

    public CompanyDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
