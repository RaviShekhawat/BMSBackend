package com.example.Controller;

//import com.example.Configuration.EnumConverter;
import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Repository.MovieRepository;
import com.example.service.MovieService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@Component
public class MovieController{

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MovieService movieService;

    @RequestMapping(value="/movies", method= RequestMethod.POST)
    public ResponseEntity createMovie(@Valid @RequestBody Movie movie) {

        return movieService.createMovie(movie);

    }

    @RequestMapping(value="/ReviewMovie/{rating}", method= RequestMethod.PUT)
    public ResponseEntity reviewMovie(@RequestParam long id, @PathVariable("rating") float rating)
    {
       return movieService.reviewMovie(id,rating);
    }

    @RequestMapping(value="/getbydirector", method= RequestMethod.PUT)
    public ResponseEntity assignDirector(@RequestParam (value="movieid")long id, @RequestParam (value="directorname")String directorName)
    {
        Movie movie= repository.findMovieById(id);
        if(movie==null )
            return new ResponseEntity<Object>("movie id not provided",HttpStatus.BAD_REQUEST);
        movie.setDirector(directorName);
        repository.save(movie);

        return movieService.assignDirector(id,directorName);

    }
    @RequestMapping(value="/getworstmovies", method= RequestMethod.GET)
    public ResponseEntity findPoorMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.ACCEPTED);

    }

    @RequestMapping(value="/getallmovies", method= RequestMethod.GET)
    public ResponseEntity getAllMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.OK);

    }

    @RequestMapping(value="/getallcities", method= RequestMethod.GET)
    public ResponseEntity getCities(@RequestParam (value="movieid")long id)
    {
        return movieService.getCities(id);
    }

    @RequestMapping(value="/deletemovie", method= RequestMethod.DELETE)
    public HttpStatus deleteMovie(@RequestParam (value="movieid")long id)
    {
        return movieService.deleteMovie(id);

    }

    @RequestMapping(value="/getmoviesbygenre", method= RequestMethod.GET)
    public List<Movie> getMoviesByGenre(@RequestParam (value="genre")Genre genre)
    {
        return movieService.getMoviesByGenre(genre);
    }

    @RequestMapping(value="/getmoviesbyyear", method= RequestMethod.GET)
    public List<Movie> getMoviesbyYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return movieService.getMoviesByYear(releaseYear);
    }

    @RequestMapping(value="/getbestmovie", method= RequestMethod.GET)
    public List<Movie> findHighestRatedMovieInGivenYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return repository.findHighestRatedMoviesInGivenYear(releaseYear);
    }

    @RequestMapping(value="/getmoviesbydirector", method= RequestMethod.GET)
    public List<Movie> findMoviesByDirector(@RequestParam (value="directorname")String directorname)
    {
        return repository.findMoviesByDirector(directorname);
    }


}
