package com.example.Controller;

import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Repository.MovieRepository;
import com.example.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Component
public class MovieController{

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MovieService movieService;

    @RequestMapping(value="/movie", method= RequestMethod.POST)
    public ResponseEntity createMovie(@Valid @RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @RequestMapping(value="/review/{rating}", method= RequestMethod.PUT)
    public ResponseEntity reviewMovie(@RequestParam long id, @PathVariable("rating") float rating)
    {
       return movieService.reviewMovie(id,rating);
    }

    @RequestMapping(value="/moviedirector", method= RequestMethod.PUT)
    public ResponseEntity assignDirector(@RequestParam (value="movieid")long id, @RequestParam (value="directorname")String directorName)
    {
        Movie movie= repository.findMovieById(id);
        if(movie == null )
            return new ResponseEntity<Object>("movie id not provided",HttpStatus.BAD_REQUEST);
        movie.setDirector(directorName);
        repository.save(movie);

        return movieService.assignDirector(id,directorName);

    }
    @RequestMapping(value="/worstmovies", method= RequestMethod.GET)
    public ResponseEntity findPoorMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.ACCEPTED);

    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public ResponseEntity getAllMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.OK);

    }

    @RequestMapping(value="/movies/city", method= RequestMethod.GET)
    public ResponseEntity getCities(@RequestParam (value="movieid")long id)
    {
        return movieService.getCities(id);
    }

    @RequestMapping(value="/movie", method= RequestMethod.DELETE)
    public HttpStatus deleteMovie(@RequestParam (value="movieid")long id)
    {
        return movieService.deleteMovie(id);
    }

    @RequestMapping(value="/movies/genre", method= RequestMethod.GET)
    public List<Movie> getMoviesByGenre(@RequestParam (value="genre")Genre genre)
    {
        return movieService.getMoviesByGenre(genre);
    }

    @RequestMapping(value="/movies/year", method= RequestMethod.GET)
    public List<Movie> getMoviesbyYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return movieService.getMoviesByYear(releaseYear);
    }

    @RequestMapping(value="/movie/highest-rated", method= RequestMethod.GET)
    public List<Movie> findHighestRatedMovieInGivenYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return repository.findHighestRatedMoviesInGivenYear(releaseYear);
    }

    @RequestMapping(value="/movies/director", method= RequestMethod.GET)
    public List<Movie> findMoviesByDirector(@RequestParam (value="directorname")String directorname)
    {
        return repository.findMoviesByDirector(directorname);
    }


}
