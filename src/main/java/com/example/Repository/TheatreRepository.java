package com.example.Repository;

import com.example.Model.Movie;
import com.example.Model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {

    @Query(value="select * from Theatre where city=?1", nativeQuery = true)
    public List<Theatre> findTheatresByCity(String name);
}
