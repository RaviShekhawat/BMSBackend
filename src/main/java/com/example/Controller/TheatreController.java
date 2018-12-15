package com.example.Controller;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Time;
import java.util.*;

@RestController
public class TheatreController {

    @Autowired
    MovieRepository movierepository;
    @Autowired
    Movie movie;
    @Autowired
    Theatre theatre;
    @Autowired
    TheatreHall theatrehall;
    @Autowired
    TheatreRepository theatrerepository;

    @RequestMapping(value="/gettheatres", method= RequestMethod.GET)
    public ResponseEntity getActiveTheatres(@RequestParam String city) {

        //return theatrerepository.findTheatresByCity(city);
        return new ResponseEntity(theatrerepository.findTheatresByCity(city)
                ,
                HttpStatus.OK);
    }

    @RequestMapping(value="/gettheatres", method= RequestMethod.GET)
    public List<TheatreHall> getTheatreHalls(@RequestParam Theatre theatre) {

        //ResponseEntity<TheatreHall> entity=new ResponseEntity<TheatreHall>(theatre.getHalls(),HttpStatus.OK);

        return theatre.getHalls();

    }

    @RequestMapping(value="/addmovie", method= RequestMethod.POST)
    public HttpStatus addMovie(@RequestBody Movie movie) {

        for(TheatreHall hall:theatre.getHalls())
        {
            if(hall.getMovie()==null) {
                hall.setMovie(movie);
                return HttpStatus.OK;
            }

        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"All halls are occupied");
    }

    @RequestMapping(value="/removemovie",method= RequestMethod.POST)
    public HttpStatus removeMovie(@RequestBody Movie movie) {
        for(TheatreHall hall:theatre.getHalls())
        {
            if(hall.getMovie()==movie) {
                hall.setMovie(null);
                return HttpStatus.OK;
            }

        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"This theatre not showing this movie");

    }

    @RequestMapping(value="/getmovies", method= RequestMethod.POST)
    public List<Movie> getMovies(@RequestParam Theatre theatre) {

        List<Movie> movielist= new LinkedList<Movie>();

        for(TheatreHall hall:theatre.getHalls())
            movielist.add(hall.getMovie());

        return movielist;

    }

    @RequestMapping(value="/getShowTimings", method= RequestMethod.GET)
    public JSONObject getShowTimings(@RequestParam Theatre theatre, @RequestParam Movie movie  ) {

        LinkedList<Time> list = theatre.getMap().get(movie);
        JSONObject obj = new JSONObject();

        if(list==null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"This theatre not showing this movie");
        else {
            Collections.sort(list);

            try {
                obj.put("Movie Timing: ", theatre.getMap().get(movie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
                ResponseEntity<JSONObject> entity= new ResponseEntity<JSONObject>(HttpStatus.OK);
                return obj;

    }





}
