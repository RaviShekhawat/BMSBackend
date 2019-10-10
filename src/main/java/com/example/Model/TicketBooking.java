package com.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.DateTime;

import javax.jws.Oneway;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Entity(name="ticketbooking")
public class TicketBooking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "movie")
    @NotNull
    private String movieName;

    @Column(name = "theatre")
    @NotNull
    private String theatreName;

    @Column(name = "price")
    @NotNull
    @Min(value = 0)
    private float price;

    @Column(name = "booking_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date bookingDate;

    @Column(name = "start_time")
    @NotNull
    private DateTime startTime;

    @Column(name = "end_time")
    @NotNull
    private DateTime endTime;

    @Column(name = "is_refundable")
    private Boolean isRefundable;


}
