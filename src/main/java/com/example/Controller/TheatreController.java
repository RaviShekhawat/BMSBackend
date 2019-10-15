package com.example.Controller;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import com.example.Model.User;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreHallRepository;
import com.example.Repository.TheatreRepository;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.*;


@EnableJpaRepositories("com.example.Repository")
@RestController
public class TheatreController {

    @Autowired
    private MovieRepository movierepository;
    @Autowired
    private UserController userController;
    @Autowired
    private TheatreRepository theatrerepository;
    @Autowired
    private TheatreHallRepository theatreHallRepository;

    @RequestMapping(value="/getactivetheatres", method= RequestMethod.GET)
    public ResponseEntity getActiveTheatres(@RequestParam String city) {
        return new ResponseEntity<Object>(theatrerepository.findActiveTheatresByCity(city), HttpStatus.OK);
    }

    
    @RequestMapping(value="/addtheatre", method= RequestMethod.POST)
    public ResponseEntity<Theatre> addTheatre(@Valid @RequestBody Theatre theatre ) {

        theatrerepository.save(theatre);
        return new ResponseEntity<Theatre>(theatre,HttpStatus.OK);

    }

    @RequestMapping(value="/addtheatrehall", method= RequestMethod.PUT)
    public ResponseEntity addHall(@RequestParam(value="theatreid") long theatreid, @RequestParam(value="hallid") long hallid ) {

        Theatre theatre = theatrerepository.findById(theatreid);
        TheatreHall theatreHall = theatreHallRepository.findHallById(hallid);
        if(theatreHall.getTheatreId()!=0)
            return new ResponseEntity<Object>("The hall is already attached to another hall",HttpStatus.BAD_REQUEST);

        theatreHall.setTheatreId(theatreid);
        if(theatre.getShowTimings()==null)
            theatre.setShowTimings(new LinkedHashMap<Long,LinkedList<DateTime>>());
        theatre.getShowTimings().put(hallid,new LinkedList<>());

        theatrerepository.save(theatre);
        theatreHallRepository.save(theatreHall);

        return new ResponseEntity<Theatre>(theatre,HttpStatus.OK);
    }

    @RequestMapping(value="/addmovie", method= RequestMethod.POST)
    public ResponseEntity addMovieShows(@RequestParam (value="theatreid")long theatreid,
                                        @RequestParam (value="movieid")long movieid,
                                        @RequestParam (value="startdate")@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")DateTime startdate,
                                        @RequestParam (value="enddate")@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")DateTime enddate,
                                        @RequestParam (value="showtimings")@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")LinkedList<DateTime> showtimings) {

        //if(userController.validateUser(userid,password).getStatusCode()==HttpStatus.UNAUTHORIZED)
          //  new ResponseEntity<Object>("Invalid user details",HttpStatus.UNAUTHORIZED);

        Theatre theatre = theatrerepository.findById(theatreid);
        Movie movie = movierepository.findMovieById(movieid);


        for(Long hallid1:theatre.getShowTimings().keySet())
        {
            TheatreHall hall=theatreHallRepository.findHallById(hallid1);
            if(hall.getMovieId()==0) {
                hall.setMovieId(movieid);
                theatreHallRepository.save(hall);
                if(theatre.getMovieNames()==null) {
                    theatre.setMovieNames(new LinkedList<String>());
                }
                Map<Long,LinkedList<DateTime>> map=new LinkedHashMap<Long,LinkedList<DateTime>>();
                map.put(hallid1,showtimings);
                theatre.getShowTimings().put(hall.getHallId(),showtimings);
                if(!theatre.getMovieNames().contains(movie.getName()))
                    theatre.getMovieNames().add(movie.getName());

                if(hall.getVacantSeatsCount()==null)
                    hall.setVacantSeatsCount(new LinkedHashMap<>());

                theatrerepository.save(theatre);


                for(int i=0;i<showtimings.size();i++) {
                    hall.getVacantSeatsCount().put(showtimings.get(i).toString(),new LinkedHashMap<>());
                    LinkedHashMap<Byte,LinkedHashSet<Byte>> seats= hall.getVacantSeatsCount().get(showtimings.get(i).toString());
                    for (byte j = 0; j < hall.getRows(); j++) {
                        for (byte k = 0; k < hall.getColumns(); k++) {
                                //seats.computeIfAbsent(j, k1 -> new LinkedHashSet<>());
                            if(!seats.containsKey(j))
                                seats.put(j,new LinkedHashSet<>());
                            seats.get(j).add(k);
                            hall.getVacantSeatsCount().put(showtimings.get(i).toString(),seats);
                            }
                        }
                        //theatreHallRepository.save(hall);
                    }

                theatreHallRepository.save(hall);

                return new ResponseEntity<Object>("Movie added",HttpStatus.OK);
            }
        }
        return new ResponseEntity<Object>("All halls are booked for this theatre",HttpStatus.OK);
    }

    @RequestMapping(value="/removemovie",method= RequestMethod.POST)
    public ResponseEntity removeMovieFromAllHalls(@RequestParam (value="theatrename")String theatrename,
                                                          @RequestParam (value="name")String moviename) {

        boolean var = false;
        Theatre theatre = theatrerepository.findByName(theatrename);
        Movie movie  = movierepository.findMovieByName(moviename);
        synchronized(theatre) {
            for (Long hallid : theatre.getShowTimings().keySet()) {
                TheatreHall theatrehall = theatreHallRepository.findHallById(hallid);
                if (theatrehall.getMovieId() == movie.getId()) {
                    var = true;
                    theatrehall.setMovieId(0);
                    theatre.getMovieNames().remove(movie.getName());
                    theatrehall.getVacantSeatsCount().clear();
                    theatreHallRepository.save(theatrehall);
                }
            }
            if (var) {
                theatre.getMovieNames().remove(movie.getName());
                theatrerepository.save(theatre);
                return new ResponseEntity<String>("Movie removed from the theatre", HttpStatus.OK);

            }
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "This theatre not showing this movie");
        }
    }

    @RequestMapping(value="/getmoviesbytheatre", method= RequestMethod.GET)
    public ResponseEntity getMoviesByTheatre(@RequestParam (value = "theatrename") String theatrename) {

        JSONObject obj = new JSONObject();
        List<Movie> movielist= new LinkedList<Movie>();
        Theatre theatre = theatrerepository.findByName(theatrename);
        if(theatre==null)
            return new ResponseEntity<Object>("Incorrect theatre details",HttpStatus.NOT_FOUND);
        if(movielist.size()==0)
            return new ResponseEntity<Object>("Currently no movie in this theatre",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Object>(movielist,HttpStatus.OK);

    }

    @RequestMapping(value="/getShowTimings", method= RequestMethod.GET)
    public ResponseEntity getShowTimings(@RequestParam (value ="theatrename")String theatreName, @RequestParam (value ="moviename")String movieName  ) {

        Theatre theatre = theatrerepository.findByName(theatreName);
        Movie movie  = movierepository.findMovieByName(movieName);
        List<DateTime> list=new LinkedList<DateTime>();

        if(theatre==null)
            return new ResponseEntity<Object>("Theatre doesn't exist",HttpStatus.BAD_REQUEST);
        else if(movie==null)
            return new ResponseEntity<Object>("No movie with this name",HttpStatus.BAD_REQUEST);

        for(Long hallid:theatre.getShowTimings().keySet())
        {
            TheatreHall theatrehall = theatreHallRepository.findHallById(hallid);
            if(theatrehall.getMovieId()==movie.getId()) {
                list.addAll(theatre.getShowTimings().get(hallid));
            }
            Collections.sort(list);
            return new ResponseEntity<Object>(list,HttpStatus.OK);
        }
            return new ResponseEntity<Object>("This theatre not showing this movie",HttpStatus.OK);

    }

}
