package com.jman.recreationroomboooking.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jman.recreationroomboooking.models.Booking;
import com.jman.recreationroomboooking.models.Employee;
import com.jman.recreationroomboooking.models.Room;
import com.jman.recreationroomboooking.models.RoomDateRecord;
import com.jman.recreationroomboooking.repositories.BookingRepository;
import com.jman.recreationroomboooking.repositories.RoomDateRecordRepository;

@Service
public class RoomService {
    Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomDateRecordRepository roomDateRecordRepository;

    public Booking bookRoom(Booking bookingDraft){
            Booking booking = null;
            try{
            Map<Boolean, RoomDateRecord> availabilityMap = checkRoomAvailability(
                    bookingDraft.getRoom(), bookingDraft.getDate(), bookingDraft.getStartTime(),
                    bookingDraft.getEndTime(), bookingDraft.getEmployee());
            if (availabilityMap.containsKey(true)) {
                if(bookingDraft.getId()==null){
                    booking = new Booking(); //new booking
                }
                else{
                    booking = bookingDraft;
                }
                booking.setEmployee(bookingDraft.getEmployee());
                booking.setStartTime(bookingDraft.getStartTime());
                booking.setEndTime(bookingDraft.getEndTime());
                booking.setDate(bookingDraft.getDate());
                booking.setRoom(bookingDraft.getRoom());
                booking.setValid(Boolean.TRUE);
                bookingRepository.save(booking);

                RoomDateRecord roomDateRecord = availabilityMap.get(true);
                if(roomDateRecord != null){
                    availabilityMap.get(true).setCount(roomDateRecord.getCount()+1);
                    roomDateRecordRepository.save(roomDateRecord);
                }
                else{
                    roomDateRecord = new RoomDateRecord();
                    roomDateRecord.setRoom(booking.getRoom());
                    roomDateRecord.setDate(booking.getDate());
                    roomDateRecord.setGender(booking.getEmployee().getGender());
                    roomDateRecord.setCount(1);
                    roomDateRecord.setStartTime(booking.getStartTime());
                    roomDateRecord.setEndTime(booking.getEndTime());
                    roomDateRecordRepository.save(roomDateRecord);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return booking;
    }

    public Map<Boolean, RoomDateRecord> checkRoomAvailability(final Room room, final Date date, final Integer startTime, final Integer endTime, final Employee employee) throws IllegalArgumentException {
        Map<Boolean, RoomDateRecord> availabilityMap = new HashMap<>();
        availabilityMap.put(true, null);
        try{
            if(startTime.compareTo(endTime)>=0){
                throw new IllegalArgumentException("Start Time can't be greater or equal to End Time.");
            }
            List<RoomDateRecord> roomDateRecords = roomDateRecordRepository.findByDateAndRoomId(date, room.getId());

            for (RoomDateRecord roomDateRecord : roomDateRecords){
                if ( (startTime.compareTo(roomDateRecord.getStartTime()) >= 0 && endTime.compareTo(roomDateRecord.getEndTime()) <= 0) ){
                    if (! (roomDateRecord.getCount() < roomDateRecord.getRoom().getRoomCapacity()
                            && roomDateRecord.getGender().equals(employee.getGender()))
                    ){
                        availabilityMap.remove(true);
                        availabilityMap.put(false, null);
                        return availabilityMap;
                    }
                    else{
                        availabilityMap.replace(true, roomDateRecord);
                        return availabilityMap;
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return availabilityMap;
    }

    public Booking cancelBooking(Booking booking){
        booking.setValid(false);
        bookingRepository.save(booking);
        removeBookingFromRoomDateRecord(booking);
        return booking;
    }

    public Booking rescheduleBooking(Booking booking, Date date, Integer startTime, Integer endTime){
        removeBookingFromRoomDateRecord(booking);
        booking.setDate(date);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        return bookRoom(booking);
    }

    public void removeBookingFromRoomDateRecord(Booking booking){
        RoomDateRecord roomDateRecord = roomDateRecordRepository.findByDateAndRoomIdAndStartTimeAndEndTime(booking.getDate(), booking.getRoom().getId(),
                booking.getStartTime(), booking.getEndTime());
        if(roomDateRecord.getCount() == 1){
            roomDateRecordRepository.delete(roomDateRecord);
        }
        else {
            roomDateRecord.setCount(roomDateRecord.getCount() - 1);
            roomDateRecordRepository.save(roomDateRecord);
        }
    }

}
