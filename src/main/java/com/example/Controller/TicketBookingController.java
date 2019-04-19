package com.example.Controller;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import com.example.Model.TicketBooking;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreHallRepository;
import com.example.Repository.TheatreRepository;
import com.example.Repository.TicketBookingRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class TicketBookingController {

    @Autowired
    private TicketBookingRepository ticketBookingRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TicketController ticketController;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreHallRepository theatreHallRepository;
    @Autowired
    private JavaMailSender sender;

    @RequestMapping(value="/findmoviesnearby", method= RequestMethod.GET)
    public ResponseEntity findMoviesNearby(@RequestParam(value="city") String city ) {

        return new ResponseEntity<Object>(ticketBookingRepository.findNearByMovies(city),HttpStatus.OK);
    }

    @RequestMapping(value="/findmoviesinatheatre", method= RequestMethod.GET)
    public ResponseEntity findTheatreWithMovie(@RequestParam(value="theatreid") Long id ) {

        return new ResponseEntity<Object>(ticketBookingRepository.findMoviesByTheatre(id),HttpStatus.OK);
    }
    @RequestMapping(value="/findcurrentweekmovies", method= RequestMethod.GET)
    public ResponseEntity<Object> findCurrentWeekMovies() {

        return new ResponseEntity<Object>(ticketBookingRepository.findCurrentWeekMovies(),HttpStatus.OK);
    }

    @org.springframework.transaction.annotation.Transactional
    @RequestMapping(value="/bookmovieticket", method= RequestMethod.POST)
    public ResponseEntity bookMovieTicket(@RequestBody String ticketrequest) {
        JsonArray responsejsonArray = new JsonArray();
        JsonArray ticketspositionArray = new JsonArray();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        boolean istheatreshowingmovie=false;
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(ticketrequest);

        ticketspositionArray=jo.getAsJsonArray("ticketsposition");
        String theatrename = jo.get("theatrename").getAsString();
        String moviename = jo.get("moviename").getAsString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        sdf.setLenient(false);
        String useremailid=jo.get("useremailid").getAsString();

        //DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate showdate= LocalDate.parse(jo.get("showdate").getAsString(),formatter);

        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("hh mm a");
        LocalTime showtime= LocalTime.parse(jo.get("showtime").getAsString(),f2);
       /* if(sdf.parse(showdate.toString(), new ParsePosition(0)) == null) {
            responsejsonArray.add("Date is not valid");
            return new ResponseEntity<Object>(responsejsonArray,HttpStatus.BAD_REQUEST);
        }
        sdf = new SimpleDateFormat("hh mm a");
        if(sdf.parse(showtime.toString(), new ParsePosition(0)) == null){
            responsejsonArray.add("showtime is null");
            return new ResponseEntity<Object>(responsejsonArray,HttpStatus.BAD_REQUEST);
        }*/

        int mm=showtime.toString().charAt(showtime.toString().length()-1)+showtime.toString().charAt(showtime.toString().length()-2)*10;
        int index=showtime.toString().indexOf(":");
        int hh=0;
        if(index==2)
            hh+=showtime.toString().charAt(0)*10+showtime.toString().charAt(1);
        else if(index==1)
            hh+=showtime.toString().charAt(0);

        else if(showtime.toString().length()>5||showtime.toString().length()<4||!showtime.toString().contains(":")||hh>23||mm>59)
        {
            responsejsonArray.add("showtime is invalid");
            return new ResponseEntity<Object>(responsejsonArray,HttpStatus.BAD_REQUEST);
        }
        Theatre theatre = theatreRepository.findByName(theatrename);
        Movie movie= movieRepository.findMovieByName(moviename);

        for (Object o : theatre.getShowtimings().keySet()) {
            TheatreHall theatreHall = theatreHallRepository.findHallById((long) Integer.parseInt(o.toString()));
            if (theatreHall.is_active() && theatreHall.getMovie_id() == movie.getId()) {
                //DateTime dateTime=new DateTime(showtime);
                responsejsonArray = ticketController.bookTicketHelper(theatreHall,ticketspositionArray,
                        showdate.toString()+"T"+showtime.toString()+":00.000+05:30");
                if(responsejsonArray.toString().equals("Sorry!!Requested seats not available"))
                    return new ResponseEntity<Object>(responsejsonArray,HttpStatus.OK);
                istheatreshowingmovie = true;
            }
                if(responsejsonArray.size()== ticketspositionArray.size()) {
                    TicketBooking ticketBooking = new TicketBooking();
                    ticketBooking.setMoviename(moviename);
                    ticketBooking.setBooking_date(new Date());
                    ticketBooking.setTheatrename(theatrename);
                    ticketBooking.setIsrefundable(false);
                    ticketBooking.setPrice(100);
                    ticketBooking.setStarttime(new DateTime(showtime));
                    ticketBookingRepository.save(ticketBooking);

                    MimeMessage message = sender.createMimeMessage();   //sending booking confirmation mail to customer
                    MimeMessageHelper helper = new MimeMessageHelper(message);
                    try {
                        helper.setTo(useremailid);
                        helper.setText("The ticket has been confirmed for the movie!! "+moviename+
                                " The theatre address is "+theatre.getAddress()+" and the show will start at "
                                 +showtime.toString()+" on "+showdate.toString());
                        helper.setSubject("Your ticket has been confirmed!!Thanks for using our platform");
                        helper.setSentDate(new Date());

                    }  catch (javax.mail.MessagingException e) {
                        e.printStackTrace();
                    }

                    sender.send(message);
                    return new ResponseEntity<Object>(responsejsonArray, HttpStatus.OK);
                }
            }
           if(!istheatreshowingmovie)
           {
               responsejsonArray.add("This theatre not showing this movie");
               return new ResponseEntity<Object>(responsejsonArray,HttpStatus.OK);
           }
           else
           {
               responsejsonArray.add("Sorry this theatre is sold out!!");
               return new ResponseEntity<Object>(responsejsonArray,HttpStatus.OK);
           }
        }
    }




