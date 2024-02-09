package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.data.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
