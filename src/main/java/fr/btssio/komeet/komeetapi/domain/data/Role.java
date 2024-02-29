package fr.btssio.komeet.komeetapi.domain.data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(name = "label")
    private String label;

    @Column(name = "level")
    private Long level;
}
