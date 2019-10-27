package com.example.service;

import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Repository.MovieRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public ResponseEntity createMovie(@Valid @RequestBody Movie movie) {
        System.out.print("Going to create movie");
        System.out.print("Creating movie");
        repository.save(movie);
        JSONObject object = new JSONObject();
        object.append("Success Message","Movie Created successfully");
        return new ResponseEntity<Object>(movie, HttpStatus.OK);

    }

    public ResponseEntity reviewMovie(@RequestParam long id, @PathVariable("rating") float rating)
    {
        Movie movie = repository.findMovieById(id);

        if(movie==null )
            return new ResponseEntity<Object>("Movie parameter not provided",HttpStatus.NOT_FOUND);
        else if(!repository.existsById(movie.getId()))
            return new ResponseEntity<Object>("Movie parameter not provided",HttpStatus.BAD_REQUEST);
        else if(rating<0.0F||rating>5.0F)
            return new ResponseEntity<Object>("Rating has to be between 0 and 5",HttpStatus.BAD_REQUEST);

        Double newRating =(movie.getAvgRating()*movie.getNoOfReviews()+rating)/(movie.getNoOfReviews()+1);

        movie.setAvgRating(newRating);
        movie.setNoOfReviews(movie.getNoOfReviews()+1);
        repository.save(movie);

        return new ResponseEntity<Object>("Movie rated successfully",HttpStatus.OK);
    }

    public ResponseEntity assignDirector(@RequestParam (value="movieid")long id, @RequestParam (value="directorname")String directorname)
    {
        Movie movie= repository.findMovieById(id);
        if(movie==null )
            return new ResponseEntity<Object>("movie id not provided",HttpStatus.BAD_REQUEST);
        movie.setDirector(directorname);
        repository.save(movie);

        return new ResponseEntity<Object>("Director assigned successfully",HttpStatus.OK);

    }

    public ResponseEntity findPoorMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.ACCEPTED);

    }


    public ResponseEntity getAllMovies()
    {
        return new ResponseEntity<Object>(repository.findAll(),HttpStatus.OK);

    }


    public ResponseEntity getCities(@RequestParam (value="movieid")long id)
    {
        return new ResponseEntity<Object>(repository.findMovieById(id).getCities(),HttpStatus.OK);

    }


    public HttpStatus deleteMovie(@RequestParam (value="movieid")long id)
    {
        if(!repository.existsById(id))
            return HttpStatus.BAD_REQUEST;

        else {
            repository.delete(repository.findMovieById(id));
            return HttpStatus.OK;
        }

    }


    public List<Movie> getMoviesByGenre(@RequestParam (value="genre") Genre genre)
    {
        return repository.findMoviesByGenre(genre);
    }


    public List<Movie> getMoviesByYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return repository.findYearWiseMovies(releaseYear);
    }


    public List<Movie> findHighestRatedMovieInGivenYear(@RequestParam (value="releaseyear")Integer releaseYear)
    {
        return repository.findHighestRatedMoviesInGivenYear(releaseYear);
    }

   
    public List<Movie> findMoviesByDirector(@RequestParam (value="directorname")String directorName)
    {
        return repository.findMoviesByDirector(directorName);
    }
}
