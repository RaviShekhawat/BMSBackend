package com.example.Repository;

import com.example.Model.Theatre;
import com.example.Model.TheatreHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreHallRepository extends JpaRepository<TheatreHall,Long>{

    @Query(value="select * from theatrehall where id=?1", nativeQuery = true)
    public TheatreHall findHallById(long id);

}
