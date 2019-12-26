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
class TicketController {

    @Autowired
    private TheatreHallRepository theatreHallRepository;

    @Transactional
    synchronized JSONArray bookTicketHelper(TheatreHall hall, JSONArray jsonArray, String dateTime) {

        JSONArray responseJsonArray = new JSONArray();
        if (hall.getVacantSeats() < jsonArray.length())
        {
            jsonArray.put("Sorry!!Requested seats not available");
            return jsonArray;
        }
        Map map = hall.getVacantSeatsCount();
        LinkedHashMap<Byte,LinkedHashSet<Byte>> seatsBookedRowWise = hall.getVacantSeatsCount().get(dateTime);

        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                JSONArray ja = (JSONArray) jsonArray.get(i);
                if(seatsBookedRowWise.get(Byte.parseByte(ja.get(0).toString())).contains(Byte.parseByte(ja.get(1).toString())))
                        responseJsonArray.put(String.valueOf("row "+ ja.get(0)+" column "+ja.get(1)));
                seatsBookedRowWise.get(Byte.parseByte(ja.get(0).toString())).remove(Byte.parseByte(ja.get(1).toString()));
                hall.getVacantSeatsCount().put(dateTime, seatsBookedRowWise);
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }
        hall.setVacantSeats(hall.getVacantSeats() - jsonArray.length());
        theatreHallRepository.save(hall);
        return responseJsonArray;
        }
    }





