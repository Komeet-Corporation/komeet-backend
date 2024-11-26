package fr.btssio.komeet.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RoomDto {

    private UUID uuid;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private Double latitude;
    private Double longitude;
    private String description;
    private Long priceHour;
    private Long priceHalfDay;
    private Long priceDay;
    private Long maxPeople;
    private Long area;
    private LocalDate dateCreated;
    private List<ImageDto> images;
    private List<EquipmentDto> equipments;
}
