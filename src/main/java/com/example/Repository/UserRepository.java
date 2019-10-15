package com.example.Repository;

import com.example.Model.Movie;
import com.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
 public interface UserRepository extends JpaRepository<User,Long> {

     User findByemailId(@Param("emailId") String emailId);

}
