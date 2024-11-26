package fr.btssio.komeet.api.repository;

import fr.btssio.komeet.api.domain.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByUuid(String uuid);
    Optional<Role> findByLabel(String label);
}
