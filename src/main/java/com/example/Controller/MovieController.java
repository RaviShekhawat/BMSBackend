package com.example.Controller;

//import com.example.Configuration.EnumConverter;
import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Repository.MovieRepository;
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
@EnableJpaRepositories("com.example.Repository")
@ComponentScan(basePackages = {"com.example.Controller","com.example.Model"})
@EntityScan("com.example.Model")
@Component
public class MovieController{

    @Autowired
    private MovieRepository repository;

    @RequestMapping(value="/GetReactions", method= RequestMethod.GET)
    public Integer getReactions(Movie movie) {

       return movie.getNo_of_reviews();

    }

    @RequestMapping(value="/movies", method= RequestMethod.POST)
    public ResponseEntity createMovie(@Valid @RequestBody Movie movieparam) {
        repository.save(movieparam);
        JSONObject object = new JSONObject();
        object.append("Success Message","Movie Created successfully");
        return new ResponseEntity<Object>(movieparam,HttpStatus.OK);

    }

    @RequestMapping(value="/ReviewMovie/{rating}", method= RequestMethod.PUT)
    public ResponseEntity reviewMovie(@RequestParam long id, @PathVariable("rating") float rating)
    {
        Movie movie = repository.findMovieById(id);

        if(movie==null )
            return new ResponseEntity<Object>("Movie parameter not provided",HttpStatus.NOT_FOUND);
        else if(!repository.existsById(movie.getId()))
            return new ResponseEntity<Object>("Movie parameter not provided",HttpStatus.BAD_REQUEST);
        else if(rating<0.0F||rating>5.0F)
            return new ResponseEntity<Object>("Rating has to be between 0 and 5",HttpStatus.BAD_REQUEST);

        float newrating =(movie.getAvg_rating()*movie.getNo_of_reviews()+rating)/(movie.getNo_of_reviews()+1);

        movie.setAvg_rating(newrating);
        movie.setNo_of_reviews(movie.getNo_of_reviews()+1);
        repository.save(movie);
        return new ResponseEntity<Object>("Movie rated successfully",HttpStatus.OK);
    }

    @RequestMapping(value="/getbydirector", method= RequestMethod.PUT)
    public ResponseEntity assignDirector(@RequestParam (value="movieid")long id, @RequestParam (value="directorname")String directorname)
    {
        Movie movie= repository.findMovieById(id);
        if(movie==null )
            return new ResponseEntity<Object>("movie id not provided",HttpStatus.BAD_REQUEST);
        movie.setDirector(directorname);
        repository.save(movie);

        return new ResponseEntity<Object>("Director assigned successfully",HttpStatus.OK);

    }
    @RequestMapping(value="/getworstMovies", method= RequestMethod.GET)
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
        return new ResponseEntity<Object>(repository.findMovieById(id).getCities(),HttpStatus.OK);

    }

    @RequestMapping(value="/deleteMovie", method= RequestMethod.DELETE)
    public HttpStatus deleteMovie(@RequestParam (value="movieid")long id)
    {
        if(!repository.existsById(id))
            return HttpStatus.BAD_REQUEST;

        else {
                repository.delete(repository.findMovieById(id));
                return HttpStatus.OK;
        }

    }

    @RequestMapping(value="/getmoviesbygenre", method= RequestMethod.GET)
    public List<Movie> getMoviesByGenre(@RequestParam (value="genre")Genre genre)
    {
        return repository.findMoviesByGenre(genre);
    }

    @RequestMapping(value="/getmoviesbyyear", method= RequestMethod.GET)
    public List<Movie> getMoviesbyYear(@RequestParam (value="releaseyear")Integer releaseyear)
    {
        return repository.findYearWiseMovies(releaseyear);
    }

    @RequestMapping(value="/getbestmovie", method= RequestMethod.GET)
    public List<Movie> findHighestRatedMovieInGivenYear(@RequestParam (value="releaseyear")Integer releaseyear)
    {
        return repository.findHighestRatedMoviesInGivenYear(releaseyear);
    }

    @RequestMapping(value="/getmoviesbydirector", method= RequestMethod.GET)
    public List<Movie> findMoviesByDirector(@RequestParam (value="directorname")String directorname)
    {
        return repository.findMoviesByDirector(directorname);
    }


}
