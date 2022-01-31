package com.jman.recreationroomboooking;

import java.util.Date;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.jman.recreationroomboooking.models.Booking;
import com.jman.recreationroomboooking.models.Employee;
import com.jman.recreationroomboooking.models.Room;
import com.jman.recreationroomboooking.models.RoomDateRecord;
import com.jman.recreationroomboooking.models.enums.GenderEnum;
import com.jman.recreationroomboooking.models.enums.RoomEnum;
import com.jman.recreationroomboooking.repositories.BookingRepository;
import com.jman.recreationroomboooking.repositories.EmployeeRepository;
import com.jman.recreationroomboooking.repositories.RoomDateRecordRepository;
import com.jman.recreationroomboooking.repositories.RoomRepository;
import com.jman.recreationroomboooking.services.RoomService;

@SpringBootApplication
public class RecreationRoomBoookingApplication {


	public static void main(String[] args) {
		SpringApplication.run(RecreationRoomBoookingApplication.class, args);
	}

	//Test Cases and Demo
	@Bean
	public CommandLineRunner demo(EmployeeRepository employeeRepository, BookingRepository bookingRepository,
								  RoomRepository roomRepository, RoomDateRecordRepository roomDateRecordRepository, RoomService roomService) {
		return (args) -> {

			//create rooms
			Room room1 = new Room();
			room1.setType(RoomEnum.CHESS.toString());
			roomRepository.save(room1);

			Room room2 = new Room();
			room2.setType(RoomEnum.GYM.toString());
			roomRepository.save(room2);

			Room room3 = new Room();
			room3.setType(RoomEnum.TT.toString());
			roomRepository.save(room3);

			for (Room room : roomRepository.findAll()){
				System.out.println(room.getId() + ", " + room.getType() + ", " + room.getRoomCapacity());
			}


			//create employees
			Employee employee1 = new Employee();
			employee1.setFirstName("Abhay");
			employee1.setLastName("Garg");
			employee1.setGender(GenderEnum.MALE.toString());
			employeeRepository.save(employee1);

			Employee employee2 = new Employee();
			employee2.setFirstName("Umang");
			employee2.setLastName("Shah");
			employee2.setGender(GenderEnum.MALE.toString());
			employeeRepository.save(employee2);

			Employee employee3 = new Employee();
			employee3.setFirstName("Kathy");
			employee3.setLastName("Rice");
			employee3.setGender(GenderEnum.FEMALE.toString());
			employeeRepository.save(employee3);

			Employee employee4 = new Employee();
			employee4.setFirstName("Priyanka");
			employee4.setLastName("B");
			employee4.setGender(GenderEnum.FEMALE.toString());
			employeeRepository.save(employee4);

			Employee employee5 = new Employee();
			employee5.setFirstName("Vinay");
			employee5.setLastName("R");
			employee5.setGender(GenderEnum.MALE.toString());
			employeeRepository.save(employee5);

			Employee employee6 = new Employee();
			employee6.setFirstName("Sarath");
			employee6.setLastName("Kumar");
			employee6.setGender(GenderEnum.MALE.toString());
			employeeRepository.save(employee6);

			Employee employee7 = new Employee();
			employee7.setFirstName("Supraja");
			employee7.setLastName("S");
			employee7.setGender(GenderEnum.FEMALE.toString());
			employeeRepository.save(employee7);

			Employee employee8 = new Employee();
			employee8.setFirstName("Janani");
			employee8.setLastName("J");
			employee8.setGender(GenderEnum.FEMALE.toString());
			employeeRepository.save(employee8);

			Employee employee9 = new Employee();
			employee9.setFirstName("Karthik");
			employee9.setLastName("B");
			employee9.setGender(GenderEnum.MALE.toString());
			employeeRepository.save(employee9);

			Employee employee10 = new Employee();
			employee10.setFirstName("Swapnil");
			employee10.setLastName("Tiwari");
			employee10.setGender(GenderEnum.FEMALE.toString());
			employeeRepository.save(employee10);

			for (Employee employee : employeeRepository.findAll()){
				System.out.println(employee.getId() + ", " + employee.getFirstName() + ", " + employee.getGender());
			}

			//first booking
			Booking booking = new Booking();
			booking.setRoom(roomRepository.findByType(RoomEnum.GYM.toString()));
			booking.setEmployee(employee1);
			booking.setStartTime(11);
			booking.setEndTime(12);
			booking.setDate(new Date());
			booking.setValid(true);
			booking = roomService.bookRoom(booking);
			printBooking(booking);

			//2nd booking
			booking = new Booking();
			booking.setRoom(roomRepository.findByType(RoomEnum.GYM.toString()));
			booking.setEmployee(employee2);
			booking.setStartTime(11);
			booking.setEndTime(12);
			booking.setDate(new Date());
			booking.setValid(true);
			booking = roomService.bookRoom(booking);
			printBooking(booking);

			//3rd booking
			booking = new Booking();
			booking.setRoom(roomRepository.findByType(RoomEnum.GYM.toString()));
			booking.setEmployee(employee3);
			booking.setStartTime(11);
			booking.setEndTime(12);
			booking.setDate(new Date());
			booking.setValid(true);
			booking = roomService.bookRoom(booking);
			printBooking(booking);

			//4th booking
			booking = new Booking();
			booking.setRoom(roomRepository.findByType(RoomEnum.GYM.toString()));
			booking.setEmployee(employee4);
			booking.setStartTime(11);
			booking.setEndTime(12);
			booking.setDate(new Date());
			booking.setValid(true);
			booking = roomService.bookRoom(booking);
			printBooking(booking);

			//5th booking
			booking = new Booking();
			booking.setRoom(roomRepository.findByType(RoomEnum.GYM.toString()));
			booking.setEmployee(employee5);
			booking.setStartTime(11);
			booking.setEndTime(12);
			booking.setDate(new Date());
			booking.setValid(true);
			booking = roomService.bookRoom(booking);
			printBooking(booking);


			//print all RoomDateRecord
			for (RoomDateRecord r : roomDateRecordRepository.findAll()) {
				printRoomDateRecord(r);
			}


			//Booking Cancellation
			Optional<Booking> bookingToBeCancelled = bookingRepository.findById(Long.valueOf(5));
			booking = null;
			if(bookingToBeCancelled.isPresent()){
				booking = roomService.cancelBooking(bookingToBeCancelled.get());
			}
			printCancelBooking(booking);

			bookingToBeCancelled = bookingRepository.findById(Long.valueOf(2));
			booking = null;
			if(bookingToBeCancelled.isPresent()){
				booking = roomService.cancelBooking(bookingToBeCancelled.get());
			}
			printCancelBooking(booking);

			//print all RoomDateRecord
			for (RoomDateRecord r : roomDateRecordRepository.findAll()) {
				printRoomDateRecord(r);
			}


			//Reschedule Booking
			Optional<Booking> bookingToBeRescheduled = bookingRepository.findById(Long.valueOf(1));
			booking = null;
			if(bookingToBeRescheduled.isPresent() && bookingToBeRescheduled.get().isValid()){
				booking = roomService.rescheduleBooking(bookingToBeRescheduled.get(), new Date(), 15, 16);
			}
			printRescheduleBooking(booking);


			//print all RoomDateRecord
			for (RoomDateRecord r : roomDateRecordRepository.findAll()) {
				printRoomDateRecord(r);
			}
		};
	}

	private void printRescheduleBooking(Booking booking) {
		if(booking!=null){
			System.out.println(
					"################## BOOKING RESCHEDULED ############\n" +
							"BookingId: " + booking.getId() + "\n" +
							"RoomId: " + booking.getRoom().getId() + "\n" +
							"EmployeeId: " + booking.getEmployee().getId() + "\n" +
							"StartTime: " + booking.getStartTime() + "\n" +
							"EndTime: " + booking.getEndTime() + "\n" +
							"Date: " + booking.getDate() + "\n" +
							"Valid: " + booking.isValid() + "\n" +
							"##################################################\n"
			);
		}
		else{
			System.out.println("Sorry, Couldn't find any booking with given info");
		}
	}

	private void printRoomDateRecord(RoomDateRecord roomDateRecord) {
		System.out.println(
				"##################################################\n" +
				"##################################################\n" +
				"################## ROOM DATA RECORD ############\n" +
				"RoomDateRecordId: " + roomDateRecord.getId() + "\n" +
				"RoomId: " + roomDateRecord.getRoom().getId() + "\n" +
				"Date: " + roomDateRecord.getDate() + "\n" +
				"StartTime: " + roomDateRecord.getStartTime() + "\n" +
				"EndTime: " + roomDateRecord.getEndTime() + "\n" +
				"Gender: " + roomDateRecord.getGender() + "\n" +
				"Count: " + roomDateRecord.getCount() + "\n" +
				"##################################################\n" +
				"##################################################\n" +
				"##################################################\n"
		);
	}

	public void printBooking(Booking booking){
		if(booking!=null){
			System.out.println(
					"################## BOOKING SUCCESSFUL ############\n" +
							"BookingId: " + booking.getId() + "\n" +
							"RoomId: " + booking.getRoom().getId() + "\n" +
							"EmployeeId: " + booking.getEmployee().getId() + "\n" +
							"StartTime: " + booking.getStartTime() + "\n" +
							"EndTime: " + booking.getEndTime() + "\n" +
							"Date: " + booking.getDate() + "\n" +
							"Valid: " + booking.isValid() + "\n" +
							"##################################################\n"
			);
		}
		else{
			System.out.println("Sorry, Slot is not available");
		}

	}

	public void printCancelBooking(Booking booking){
		if(booking!=null){
			System.out.println(
					"################## BOOKING CANCELLED ############\n" +
							"BookingId: " + booking.getId() + "\n" +
							"RoomId: " + booking.getRoom().getId() + "\n" +
							"EmployeeId: " + booking.getEmployee().getId() + "\n" +
							"StartTime: " + booking.getStartTime() + "\n" +
							"EndTime: " + booking.getEndTime() + "\n" +
							"Date: " + booking.getDate() + "\n" +
							"Valid: " + booking.isValid() + "\n" +
							"##################################################\n"
			);
		}
		else{
			System.out.println("Sorry, Couldn't find any booking with given info");
		}

	}

}
