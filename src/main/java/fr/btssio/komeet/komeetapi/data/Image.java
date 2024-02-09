package fr.btssio.komeet.komeetapi.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "room", joinColumns = @JoinColumn(name = "id"))
    private Room room;
}
