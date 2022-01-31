package com.jman.recreationroomboooking.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jman.recreationroomboooking.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByType(String type);

    @Query("SELECT id FROM Room room where type = :type")
    List<Long> getRoomsIdByType(String type);
}
