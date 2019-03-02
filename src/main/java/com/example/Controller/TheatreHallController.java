package com.example.Controller;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreHallRepository;
import com.example.Repository.TheatreRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.sql.Time;
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

        long theatreid = theatrehall.getTheatre_id();
        Theatre theatre= theatrerepository.findById(theatreid);
        theatrehall.setVacantseatscount(new LinkedHashMap<>());
        theatrehall.setVacant_seats(theatrehall.getRows()*theatrehall.getColumns());
        theatrehallrepository.save(theatrehall);

        return new ResponseEntity<Object>(theatrehall,HttpStatus.OK);
    }

    @RequestMapping(value="/findtheatrehall", method= RequestMethod.PUT)
    public TheatreHall findHall(@RequestParam(value="theatreid") long theatrehallid ) {
        return theatrehallrepository.findHallById(theatrehallid);

    }

}
