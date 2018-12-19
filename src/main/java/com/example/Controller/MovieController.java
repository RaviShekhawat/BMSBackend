package com.example.Controller;

import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Model.MovieType;
import com.example.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sun.deploy.net.protocol.ProtocolType.HTTP;

@RestController
@Component
public class MovieController {

    @Autowired
    MovieRepository repository;
    @Autowired
    Movie movie;

    @RequestMapping(value="/GetReactions", method= RequestMethod.GET)
    public Integer getReactions() {

       return movie.getNo_of_reviews();

    }

    @RequestMapping(value="/CreateMovie", method= RequestMethod.POST)
    public HttpStatus createMovie(String name, MovieType type, Date releasedate,
                                  int time, Genre genre) {

        Movie movie =new Movie(name,type,releasedate,time,genre);

        repository.save(movie);

        return HttpStatus.OK;

    }

    @RequestMapping(value="/ReviewMovie", method= RequestMethod.POST)
    public void reviewMovie(Movie movie,float rating)
    {

        float newrating=(movie.getAvg_rating()*movie.getNo_of_reviews()+rating)/movie.getNo_of_reviews()+1;
        movie.setAvg_rating(newrating);
        movie.setNo_of_reviews(movie.getNo_of_reviews()+1);
    }

    @RequestMapping(value="/worstMovies", method= RequestMethod.POST)
    public List<Movie> findPoorMovies()
    {
        return repository.findPoorMovies();

    }

    @RequestMapping(value="/deleteMovie", method= RequestMethod.DELETE)
    public HttpStatus deleteMovie(long id)
    {
        if(!repository.existsById(id))
            return HttpStatus.BAD_REQUEST;

        else
            return HttpStatus.OK;

    }

    @RequestMapping(value="/getmoviesbyGenre", method= RequestMethod.GET)
    public List<Movie> getMoviesByGenre(Genre genre)
    {
        List<Movie> list=repository.findMoviesByGenre(genre);

        return list;

    }

    @RequestMapping(value="/getmoviesbyyear", method= RequestMethod.GET)
    public List<Movie> getMoviesbyYear(int year)
    {
        List<Movie> list=repository.findCurrentYearMovies(year);

        return list;

    }

    @RequestMapping(value="/getbestmovie", method= RequestMethod.GET)
    public Movie findHighestRatedMovieInGivenYear(int year)
    {
        Movie movie=repository.findHighestRatedMovieInGivenYear(year);

        return movie;

    }

    @RequestMapping(value="/getbydirector", method= RequestMethod.GET)
    public List<Movie> findMoviesByDirector(String name)
    {
        List<Movie> directormovies = repository.findMoviesByDirector(name);

        return directormovies;

    }

}
