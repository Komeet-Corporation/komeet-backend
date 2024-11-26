package fr.btssio.komeet.api.repository;

import fr.btssio.komeet.api.domain.data.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
