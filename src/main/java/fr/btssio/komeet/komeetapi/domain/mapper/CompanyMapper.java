package fr.btssio.komeet.komeetapi.domain.mapper;

import fr.btssio.komeet.komeetapi.domain.data.Company;
import fr.btssio.komeet.komeetapi.domain.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.domain.dto.RoomDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {

    private final RoomMapper roomMapper;

    public CompanyMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public CompanyDto toDto(@NotNull Company company) {
        CompanyDto companyDto = new CompanyDto(company.getEmail(), company.getName(), company.getPhone());
        List<RoomDto> rooms = company.getRooms().stream().map(roomMapper::toDto).toList();
        companyDto.setRooms(rooms);
        return companyDto;
    }
}
