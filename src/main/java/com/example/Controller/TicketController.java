package com.example.Controller;

import com.example.Model.*;
import com.example.Repository.MovieRepository;
import com.example.Repository.TheatreHallRepository;
import com.example.Repository.TicketBookingRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.deploy.net.HttpResponse;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import javax.xml.ws.http.HTTPException;
import java.util.*;


@EnableJpaRepositories("com.example.Repository")
@RestController
public class TicketController {

    @Autowired
    private TheatreHallRepository theatreHallRepository;

    @Transactional
    synchronized JsonArray bookTicketHelper(TheatreHall hall, JsonArray jsonArray, String dateTime) {
        JsonArray responsejsonArray = new JsonArray();
        if (hall.getVacant_seats() < jsonArray.size())
        {
            jsonArray.add("Sorry!!Requested seats not available");
            return jsonArray;
        }
        Map map = hall.getVacantseatscount();
        Iterator it = map.entrySet().iterator();
        LinkedHashMap<Byte,LinkedHashSet<Byte>> seatsbookedrowwise = hall.getVacantseatscount().get(dateTime);

        for (int i = 0; i < jsonArray.size(); i++)
        {
            try {
                JsonArray ja=jsonArray.get(i).getAsJsonArray();
                if(seatsbookedrowwise.get(Byte.parseByte(ja.get(0).toString())).contains(Byte.parseByte(ja.get(1).toString())))
                        responsejsonArray.add(String.valueOf("row "+ ja.get(0)+" column "+ja.get(1)));
                seatsbookedrowwise.get(Byte.parseByte(ja.get(0).toString())).remove(Byte.parseByte(ja.get(1).toString()));
                hall.getVacantseatscount().put(dateTime, seatsbookedrowwise);
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }
        hall.setVacant_seats(hall.getVacant_seats() - jsonArray.size());
        theatreHallRepository.save(hall);
        return responsejsonArray;
        }
    }





