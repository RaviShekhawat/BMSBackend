package com.example.Controller;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreHallRepository;
import com.example.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
public class TheatreHallController {

    @Autowired
    private MovieRepository movierepository;
    @Autowired
    private TheatreHallRepository theatrehallrepository;
    @Autowired
    private TheatreRepository theatrerepository;

    @RequestMapping(value="/createtheatrehall", method= RequestMethod.POST)
    public ResponseEntity createTheatreHall(@Valid @RequestBody TheatreHall theatrehall ) {

        long theatreid = theatrehall.getTheatreId();
        Theatre theatre= theatrerepository.findById(theatreid);
        theatrehall.setVacantSeatsCount(new LinkedHashMap<>());
        theatrehall.setVacantSeats(theatrehall.getRows()*theatrehall.getColumns());
        theatrehallrepository.save(theatrehall);

        return new ResponseEntity<Object>(theatrehall,HttpStatus.OK);
    }

    @RequestMapping(value="/findtheatrehall", method= RequestMethod.PUT)
    public TheatreHall findHall(@RequestParam(value="theatreid") long theatrehallid ) {
        return theatrehallrepository.findHallById(theatrehallid);

    }

}
