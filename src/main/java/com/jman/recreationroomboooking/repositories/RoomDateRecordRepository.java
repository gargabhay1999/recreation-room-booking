package com.jman.recreationroomboooking.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jman.recreationroomboooking.models.RoomDateRecord;

@Repository
public interface RoomDateRecordRepository extends JpaRepository<RoomDateRecord, Long> {

    List<RoomDateRecord> findByDateAndRoomId(Date date, Long roomId);

    RoomDateRecord findByDateAndRoomIdAndStartTimeAndEndTime(Date date, Long roomId, Integer startTime, Integer endTime);
}
