package fr.btssio.komeet.api.domain.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "company", insertable = false, updatable = false)
    private String company;

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

    @OneToMany
    @JoinColumn(name = "room")
    private List<Image> images;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "equip",
            joinColumns = @JoinColumn(name = "room"),
            inverseJoinColumns = @JoinColumn(name = "equipment"))
    private List<Equipment> equipments;

    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private List<User> users;
}
