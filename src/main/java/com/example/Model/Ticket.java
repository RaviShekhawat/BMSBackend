package com.example.Model;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    long id;
    @Column(name="Theatre")
    @ManyToOne
    private
    Theatre theatre;
    @Column(name="Movie")
    @ManyToOne
    private
    Movie movie;
    @Column(name="price")
    private float price;
    @Column(name = "event_type")
    private EventCategory event;
    @Column(name="booking_date")
    private
    Date booking_date;
    @Column(name = "payment_type")
    private Payment payment;

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public EventCategory getEvent() {
        return event;
    }

    public void setEvent(EventCategory event) {
        this.event = event;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
