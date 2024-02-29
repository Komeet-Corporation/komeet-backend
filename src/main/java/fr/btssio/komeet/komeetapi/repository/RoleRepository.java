package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
