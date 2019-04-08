package com.example.Model;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.*;


@Entity(name="theatre")
public class Theatre {

    Theatre()
    {}

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
    @NotNull
    private Boolean isactive;
    @ElementCollection
    @CollectionTable(name = "theatre_movies", joinColumns = @JoinColumn(name = "theatre_id"))
    @Column(name = "movies")
    private List<String> movienames = new LinkedList<>();

    @ElementCollection
    @MapKeyColumn()
    @Column(name = "showtimings")
    @CollectionTable(name = "theatreshows", joinColumns = @JoinColumn(name = "theatre_id"))
    private Map<Long,LinkedList<DateTime>> showtimings = new LinkedHashMap<>();

    public List<String> getMovienames() {
        return movienames;
    }

    public void setMovieNames(List<String> movienames) {
        this.movienames = movienames;
    }

    public void setMovienames(List<String> movienames) {
        this.movienames = movienames;
    }

    public Map<Long, LinkedList<DateTime>> getShowtimings() {
        return showtimings;
    }

    public void setShowtimings(Map<Long, LinkedList<DateTime>> showtimings) {
        this.showtimings = showtimings;
    }

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


    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public String toString() {
        return "Theatre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", isactive=" + isactive +


                '}';
    }

}
