package fr.btssio.komeet.api.repository;

import fr.btssio.komeet.api.domain.data.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
