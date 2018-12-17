package com.example.Controller;

import com.example.Model.Genre;
import com.example.Model.Movie;
import com.example.Model.MovieType;
import com.example.Model.User;
import com.example.Repository.MovieRepository;
import com.example.Repository.UserRepository;
import com.sun.deploy.net.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    User user;

    JSONObject json;

    @RequestMapping(value="/createuser", method= RequestMethod.POST)
    public void createUser(@RequestBody String name, @RequestBody String password,@RequestBody String emailId, @RequestBody long contact_no) {

        User user=new User(name,password,emailId,contact_no);

        if(name==null || emailId==null)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"name or emailid is null");


        repository.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        try{
            JSONObject json = new JSONObject();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //HttpEntity <String> httpEntity = new HttpEntity <String> (json.toString(), httpHeaders);

        //RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.postForObject("/createuser", httpEntity, String.class);

        //JSONObject jsonObj = new JSONObject(response);


    }

    @RequestMapping(value="/deleteuser", method= RequestMethod.POST)
    public HttpStatus deleteUser(@RequestBody long id) {

        if(repository.existsById(id)) {
            repository.deleteById(id);
            return  HttpStatus.OK;
        }

        else
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"no user with this Id");
    }

    @RequestMapping(value="/finduser", method= RequestMethod.GET)
    public User findUserById(@RequestBody long id) {

        if(repository.existsById(id))
            return repository.findUserById(id);
        else
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"no user with this Id");
    }

    @RequestMapping(value="/countusers", method= RequestMethod.GET)
    public long findUsersCount() {

        return repository.count();
    }

    @RequestMapping(value="/updatepassword", method= RequestMethod.PUT)
    public HttpStatus updatepassword(long id,String password) {

        if(repository.existsById(id))
        {
            User user=repository.findUserById(id);
            user.setPassword(password);
            repository.save(user);
            return HttpStatus.OK;
        }
        else
            return HttpStatus.NOT_FOUND;
    }



}
