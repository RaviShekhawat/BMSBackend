package com.example.Controller;

import com.example.Model.User;
import com.example.Repository.UserRepository;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EnableJpaRepositories("com.example.Repository")
@ComponentScan(basePackages = {"com.example.Controller","com.example.Model"})
@RestController
@Component
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value="/createuser", method= RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {

        Pattern p = Pattern.compile("[7-9][0-9]{9}");
        Matcher m = p.matcher(user.getContact_no());
        if(user.getName() == null || user.getEmailId()==null || user.getPassword()==null)
            return new ResponseEntity<Object>("Invalid name or email",HttpStatus.EXPECTATION_FAILED);
        else if(user.getPassword().length()<6)
            return new ResponseEntity<Object>("Password length can't be less than 6 characters",HttpStatus.EXPECTATION_FAILED);
        else if(!EmailValidator.getInstance().isValid(user.getEmailId()))
            return new ResponseEntity<Object>("EmailID is not valid",HttpStatus.EXPECTATION_FAILED);
        else if(!(m.find() && m.group().equals(user.getContact_no())))
            return new ResponseEntity<Object>("Contact number is not valid",HttpStatus.EXPECTATION_FAILED);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        repository.save(user);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/validateuser", method= RequestMethod.GET)
    public ResponseEntity validateUser(@RequestParam (value="emailid") String emailid,@RequestParam (value="password")String plainpassword) {

        User user= repository.findUserBymailID(emailid);
        if(user==null)
            return new ResponseEntity<Object>("No user with this emailID",HttpStatus.BAD_REQUEST);
        else if (!BCrypt.checkpw(plainpassword, user.getPassword()))
            return new ResponseEntity<Object>("Invalid credentials",HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<Object>("Logged in successfully",HttpStatus.OK);

    }

    @RequestMapping(value="/countusers", method= RequestMethod.GET)
    public long findUsersCount() {
        return repository.count();
    }

    @RequestMapping(value="/updatepassword", method= RequestMethod.PUT)
    public HttpStatus updatePassword(@RequestParam (value="emailid") String emailid, @RequestParam (value="password")String password) {

        if(repository.findUserBymailID(emailid)!=null)
        {
            User user=repository.findUserBymailID(emailid);
            user.setPassword(password);
            repository.save(user);
            return HttpStatus.OK;
        }
        else
            return HttpStatus.NOT_FOUND;
    }



}
