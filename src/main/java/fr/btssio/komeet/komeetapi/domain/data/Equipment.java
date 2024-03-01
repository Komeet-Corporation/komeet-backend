package fr.btssio.komeet.komeetapi.domain.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "label")
    private String label;

    @JsonIgnore
    @ManyToMany(mappedBy = "equipments")
    private List<Room> rooms;
}
