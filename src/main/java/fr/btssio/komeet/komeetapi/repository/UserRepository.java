package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.domain.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUuid(String uuid);

    @Query(value = "select email from User where uuid = :uuid")
    String findEmailByUuid(String uuid);

    @Modifying
    @Transactional
    @Query(value = "insert into favorite(user, room) values (:emailUser, :idRoom)", nativeQuery = true)
    void addFavorite(String emailUser, Long idRoom);

    @Modifying
    @Transactional
    @Query(value = "delete from favorite where user = :emailUser and room = :idRoom", nativeQuery = true)
    void removeFavorite(String emailUser, Long idRoom);

    @Query(value = "select if(count(*) > 0, 'true', 'false') from favorite where user = :emailUser and room = :idRoom", nativeQuery = true)
    boolean existsFavorite(String emailUser, Long idRoom);
}
