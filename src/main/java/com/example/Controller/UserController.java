package com.example.Controller;

import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EnableJpaRepositories("com.example.Repository")
@ComponentScan(basePackages = {"com.example.Controller","com.example.Model"})
@RestController
@Component
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/createuser", method= RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);

    }

    @RequestMapping(value="/validateuser", method= RequestMethod.GET)
    public ResponseEntity validateUser(@RequestParam (value="emailid") String emailid,@RequestParam (value="password")String plainPassword) {
        return userService.validateUser(emailid, plainPassword);

    }

    @RequestMapping(value="/countusers", method= RequestMethod.GET)
    public long findUsersCount() {
        return userRepository.count();
    }

    @RequestMapping(value="/updatepassword", method= RequestMethod.PUT)
    public HttpStatus updatePassword(@RequestParam (value="emailid") String emailid, @RequestParam (value="password")String password) {
        return userService.updatePassword(emailid,password);

    }



}
