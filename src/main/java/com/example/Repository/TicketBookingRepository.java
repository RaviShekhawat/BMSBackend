package com.example.Repository;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import com.example.Model.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 public interface TicketBookingRepository extends JpaRepository<TicketBooking,Long>{
    
    @Query(value="select * from movies where ?1 in(select cities from movie_cities)")
     List<Movie> findNearByMovies(String city);


    @Query(value="select theatre_movies.movies from theatre inner join theatre_movies on theatre.id= theatre_movies.id  where theatre.id=?1")
     List<String> findMoviesByTheatre(Long id);

    @Query(value="select movie.movie_name from movie where week(movie.release_date)=week(sysdate()) and datepart(day,movie.release_date)>datepart(day.sysdate())")
     List<String> findCurrentWeekMovies();



}
