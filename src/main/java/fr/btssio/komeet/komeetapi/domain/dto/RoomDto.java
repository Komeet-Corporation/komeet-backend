package fr.btssio.komeet.komeetapi.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RoomDto {

    private Long id;
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

    public RoomDto(Long id, String name, String street, String city, String zipCode, Double latitude, Double longitude,
                    String description, Long priceHour, Long priceHalfDay, Long priceDay, Long maxPeople, Long area,
                    LocalDate dateCreated) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.priceHour = priceHour;
        this.priceHalfDay = priceHalfDay;
        this.priceDay = priceDay;
        this.maxPeople = maxPeople;
        this.area = area;
        this.dateCreated = dateCreated;
    }
}
