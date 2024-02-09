package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
