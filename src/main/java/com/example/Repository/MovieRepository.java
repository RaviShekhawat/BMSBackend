package com.example.Repository;

import com.example.Model.Genre;
import com.example.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value="select * from Movie where Movie.=?1", nativeQuery = true)
    public List<Movie> findCurrentWeekMovies();

    @Query(value="select * from Movie where avg_rating<2.5", nativeQuery = true)
    public List<Movie> findPoorMovies();

    @Query(value="select * from Movie where genre=?1", nativeQuery = true)
    public List<Movie> findMoviesByGenre(Genre genre);

    @Query(value="select * from Movie where year(date)=?1", nativeQuery = true)
    public List<Movie> findCurrentYearMovies(int year);

    @Query(value="select * from Movie where rating>(select rating from Movie where year(date)=?1)", nativeQuery = true)
    public Movie findHighestRatedMovieInGivenYear(int year);

    @Query(value="select * from Movie where director=?1", nativeQuery = true)
    public List<Movie> findMoviesByDirector(String name);

}
