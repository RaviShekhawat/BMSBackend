package com.example.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Entity(name="Theatre")
public class Theatre {

    @Autowired
    Theatre()
    {

    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;
    @Column(name="address")
    private String address;
    @Column(name="city")
    private String city;
    @Column(name="is_active")
    private Boolean is_active;
    @Transient
    private List<TheatreHall> halls;
    @Transient
    private Map <Movie,LinkedList<Time>> map;

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public List<TheatreHall> getHalls() {
        return halls;
    }

    public void setHalls(List<TheatreHall> halls) {
        this.halls = halls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<Movie, LinkedList<Time>> getMap() {
        return map;
    }

    public void setMap(Map<Movie, LinkedList<Time>> map) {
        this.map = map;
    }
}
