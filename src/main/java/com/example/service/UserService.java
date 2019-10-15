package com.example.service;

import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.userRepository.UseruserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public ResponseEntity createUser(@RequestBody User user) {

        Pattern p = Pattern.compile("[7-9][0-9]{9}");
        Matcher m = p.matcher(user.getContactNo());
        if(user.getName() == null || user.getEmailId()==null || user.getPassword()==null)
            return new ResponseEntity<Object>("Invalid name or email", HttpStatus.EXPECTATION_FAILED);
        else if(user.getPassword().length()<6)
            return new ResponseEntity<Object>("Password length can't be less than 6 characters",HttpStatus.EXPECTATION_FAILED);
        else if(!EmailValidator.getInstance().isValid(user.getEmailId()))
            return new ResponseEntity<Object>("EmailID is not valid",HttpStatus.EXPECTATION_FAILED);
        else if(!(m.find() && m.group().equals(user.getContactNo())))
            return new ResponseEntity<Object>("Contact number is not valid",HttpStatus.EXPECTATION_FAILED);

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    
    public ResponseEntity validateUser(@RequestParam(value="emailid") String emailid, @RequestParam (value="password")String plainpassword) {

        User user= userRepository.findByemailId(emailid);
        if(user==null)
            return new ResponseEntity<Object>("No user with this emailID",HttpStatus.BAD_REQUEST);
        else if (!BCrypt.checkpw(plainpassword, user.getPassword()))
            return new ResponseEntity<Object>("Invalid credentials",HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<Object>("Logged in successfully",HttpStatus.OK);

    }

    @RequestMapping(value="/countusers", method= RequestMethod.GET)
    public long findUsersCount() {
        return userRepository.count();
    }

    @RequestMapping(value="/updatepassword", method= RequestMethod.PUT)
    public HttpStatus updatePassword(@RequestParam (value="emailid") String emailId, @RequestParam (value="password")String password) {

        if(userRepository.findByemailId(emailId)!=null)
        {
            User user=userRepository.findByemailId(emailId);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            User updatedUser = userRepository.save(user);
            if(updatedUser!=null)
            {
                return HttpStatus.OK;
            }
            else
                return HttpStatus.REQUEST_TIMEOUT;
        }
        else
            return HttpStatus.NOT_FOUND;
    }


}
