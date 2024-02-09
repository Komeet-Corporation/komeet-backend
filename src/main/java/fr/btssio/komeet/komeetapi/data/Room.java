package fr.btssio.komeet.komeetapi.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "company", joinColumns = @JoinColumn(name = "email"))
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "priceHour")
    private Long priceHour;

    @Column(name = "priceHalfDay")
    private Long priceHalfDay;

    @Column(name = "priceDay")
    private Long priceDay;

    @Column(name = "maxPeople")
    private Long maxPeople;

    @Column(name = "area")
    private Long area;

    @Column(name = "dateCreated")
    private String dateCreated;

    @OneToMany(mappedBy = "room")
    private List<Image> images;

    @ManyToMany
    @JoinTable(name = "equip",
            joinColumns = @JoinColumn(name = "room"),
            inverseJoinColumns = @JoinColumn(name = "equipment"))
    private List<Equipment> equipments;

    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private List<User> users;
}
