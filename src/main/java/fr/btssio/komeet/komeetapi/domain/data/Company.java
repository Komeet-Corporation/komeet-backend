package fr.btssio.komeet.komeetapi.domain.data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "uuid")
    private String uuid;

    @OneToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToMany
    @JoinColumn(name = "company")
    private List<Room> rooms;
}
