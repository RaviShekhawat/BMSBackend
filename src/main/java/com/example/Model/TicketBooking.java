package com.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import javax.jws.Oneway;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity(name="ticketbooking")
public class TicketBooking {

    public TicketBooking() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;
    @Column(name = "movie")
    @NotNull
    private String moviename;
    @Column(name = "theatre")
    @NotNull
    private String theatrename;
    @Column(name = "price")
    @NotNull
    @Min(value = 0)
    private float price;
    @Column(name = "booking_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date booking_date;
    @Column(name = "start_time")
    @NotNull
    private DateTime starttime;
    @Column(name = "end_time")
    @NotNull
    private DateTime endtime;

    @Column(name = "is_refundable")
    private Boolean isrefundable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getTheatrename() {
        return theatrename;
    }

    public void setTheatrename(String theatrename) {
        this.theatrename = theatrename;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public DateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(DateTime starttime) {
        this.starttime = starttime;
    }

    public DateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(DateTime endtime) {
        this.endtime = endtime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getIsrefundable() {
        return isrefundable;
    }

    public void setIsrefundable(Boolean isrefundable) {
        this.isrefundable = isrefundable;
    }

}
