package com.example.Repository;

import com.example.Model.Genre;
import com.example.Model.Movie;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value="select * from Movie where Movie.=?1")
     List<Movie> findCurrentWeekMovies();

    @Query(value="select m from Movie m where m.avgRating<2.0")
     List<Movie> findPoorMovies();

    @Query(value="select m from Movie m where m.genre =:genre")
     List<Movie> findMoviesByGenre(@Param("genre") Genre genre);

    @Query(value="select m from Movie m where year(m.releaseDate)=:year")
     List<Movie> findYearWiseMovies(@Param("year") int year);

    @Query(value="select * from Movie where year(release_date)=?1 and avg_rating>=(select max(avg_rating) from Movie where year(release_date)=?1)", nativeQuery = true)
     List<Movie> findHighestRatedMoviesInGivenYear(@Param("id") int year);

    @Query(value="select m from Movie m where m.directorId =:directorName")
     List<Movie> findMoviesByDirector(@Param("directorId") String directorName);

    @Query(value="select m from Movie m where m.id =:id")
     Movie findMovieById(@Param("id") long id);

    @Query(value="select m from Movie where movie_name =:name")
     Movie findMovieByName(@Param("id") String name);

}
