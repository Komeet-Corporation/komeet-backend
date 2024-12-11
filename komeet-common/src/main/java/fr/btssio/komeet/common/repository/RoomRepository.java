package fr.btssio.komeet.common.repository;

import fr.btssio.komeet.common.data.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByUuid(String uuid);

    @Query("select id from Room where uuid = :uuid")
    Long findIdByUuid(String uuid);
}
