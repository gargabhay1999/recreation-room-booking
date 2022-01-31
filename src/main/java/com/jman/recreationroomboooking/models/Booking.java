package com.jman.recreationroomboooking.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="BOOKING")
public class Booking {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="startTime")
    private Integer startTime;

    @Column(name="endTime")
    private Integer endTime;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @Column(name="isValid")
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
}
