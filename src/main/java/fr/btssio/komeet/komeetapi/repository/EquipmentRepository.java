package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.data.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
