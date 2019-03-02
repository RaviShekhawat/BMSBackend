package com.example.Repository;

import com.example.Model.Movie;
import com.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value="select * from User where id=?1", nativeQuery = true)
    public User findUserById(long id);

    @Query(value="select * from User where email_id=?1", nativeQuery = true)
    public User findUserBymailID(String emailid);

}
