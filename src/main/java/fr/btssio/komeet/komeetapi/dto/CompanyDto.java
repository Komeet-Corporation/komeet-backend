package fr.btssio.komeet.komeetapi.dto;

import fr.btssio.komeet.komeetapi.data.Company;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private String email;
    private String name;
    private String phone;
    private List<RoomDto> rooms;

    private CompanyDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public static CompanyDto bindFromCompany(Company company) {
        CompanyDto companyDto = new CompanyDto(company.getEmail(), company.getName(), company.getPhone());
        List<RoomDto> rooms = company.getRooms().stream().map(RoomDto::bindFromRoom).toList();
        companyDto.setRooms(rooms);
        return companyDto;
    }
}
