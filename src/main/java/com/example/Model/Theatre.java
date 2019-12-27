package com.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.*;

@Data
@Entity(name="theatre")
public class Theatre {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="name",unique = true)
    @NotNull
    private String name;

    @Column(name="address")
    @NotNull
    private String address;

    @Column(name="city")
    @NotNull
    private String city;

    @Column(name="is_active")
    @JsonProperty("is_active")
    @NotNull
    private Boolean isActive;

    @ElementCollection
    @CollectionTable(name = "theatre_movies", joinColumns = @JoinColumn(name = "theatre_id"))
    @JsonProperty("movies")
    @Column(name = "movies")
    private List<String> movieNames = new LinkedList<>();

    @ElementCollection
    @MapKeyColumn()
    @Column(name = "showtimings")
    @CollectionTable(name = "theatreshows", joinColumns = @JoinColumn(name = "theatre_id"))
    private Map<Long,LinkedList<DateTime>> showTimings = new LinkedHashMap<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theatre theatre = (Theatre) o;
        return id == theatre.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }



}
