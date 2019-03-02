package com.example.Repository;

import com.example.Model.Genre;
import com.example.Model.Movie;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value="select * from Movie where Movie.=?1", nativeQuery = true)
    public List<Movie> findCurrentWeekMovies();

    @Query(value="select * from Movie where avg_rating<2.0", nativeQuery = true)
    public List<Movie> findPoorMovies();

    @Query(value="select * from Movie where genre=?1", nativeQuery = true)
    public List<Movie> findMoviesByGenre(Genre genre);

    @Query(value="select * from Movie where year(release_date)=?1", nativeQuery = true)
    public List<Movie> findYearWiseMovies(int year);

    @Query(value="select * from Movie where year(release_date)=?1 and avg_rating>=(select max(avg_rating) from Movie where year(release_date)=?1)", nativeQuery = true)
    public List<Movie> findHighestRatedMoviesInGivenYear(int year);

    @Query(value="select * from Movie where director=?1", nativeQuery = true)
    public List<Movie> findMoviesByDirector(String name);

    @Query(value="select * from Movie where id=?1", nativeQuery = true)
    public Movie findMovieById(long id);

    @Query(value="select * from Movie where movie_name=?1", nativeQuery = true)
    public Movie findMovieByName(String name);

}
