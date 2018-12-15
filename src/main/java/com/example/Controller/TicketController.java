package com.example.Controller;

import com.example.Model.*;
import com.example.Repository.MovieRepository;
import com.sun.deploy.net.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.xml.ws.http.HTTPException;
import java.util.*;

@RestController
public class TicketController {

    @Autowired
    MovieRepository repository;
    @Autowired
    Movie movie;
    @Autowired
    Theatre theatre;
    @Autowired
    TheatreHall theatrehall;


    synchronized private JSONObject bookTicketHelper(TheatreHall hall, Movie movie, byte no_of_tickets)
    {
        JSONObject object = new JSONObject();
        if(hall.getVacant_seats()<no_of_tickets)
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Enough seats not available");

        Map map = TheatreHall.sortByValues(hall.getVacantseatscount());

        //Set<Byte> entryset = hall.getVacantseatscount().keySet();
        byte assigned = 0;

        Set set = map.entrySet();
        Iterator it=set.iterator();

        while(assigned<no_of_tickets)
        {
            int col_vacant_seats=0;
            Map.Entry pair = (Map.Entry)it.next();
            Integer key = (Integer)pair.getKey();

            for(int j=0;j<hall.getColumns();j++) {
                if (hall.getArr()[key][j]) {
                    try {
                        object.put("Row", key);
                        object.put("Column", j);
                        hall.getArr()[key][j] = false;
                        assigned++;
                        hall.setVacant_seats(hall.getVacant_seats()-1);
                        map.put(key,(Integer)map.get(key)-1);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


        }
        return  object;
    }

    @RequestMapping(value="/booktickets", method= RequestMethod.POST)
    public void bookTickets(@RequestBody Theatre theatre, @RequestBody Movie movie, @RequestBody byte no_of_tickets) {

            List<TheatreHall> list=theatre.getHalls();

            boolean flag=false;

            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).getMovie()==movie&&list.get(i).is_active()) {
                    flag = true;
                    theatrehall = list.get(i);
                }
            }

            if(!flag)
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Movie not running in this theatre currently");
            else
            {
                Thread t=new Thread(){
                    public void run()
                    {
                        bookTicketHelper(theatrehall,movie,no_of_tickets);
                    }
                };
            }


    }





}
