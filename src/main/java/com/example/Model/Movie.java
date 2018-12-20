package com.example.Model;

//import com.sun.istack.internal.NotNull;
//import org.jetbrains.annotations.NotNull;
//import com.sun.istack.internal.NotNull;
import javax.validation.constraints.NotNull;
//import org.jetbrains.annotations.Nullable;
import org.hibernate.annotations.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import javax.persistence.*;

import java.util.List;

@EnableJpaAuditing
@Entity(name="Movie")
public class Movie {


    public Movie()
    {

    }
    public Movie(String name,MovieType type,Date releasedate,
          int movie_length, Genre genre)
    {
        this.name=name;
        this.type=type;
        this.releasedate=releasedate;
        this.movie_length=movie_length;
        this.genre=genre;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="movie_name")
    @NotNull
    String name;
    @Enumerated(EnumType.STRING)
    //@NotNull
    MovieType type;
    @Column(name="release_date")
    //@NotNull
    Date releasedate;
    @Column(name="movie_length")
    //@NotNull
    int movie_length;
    @Column(name="like_percentage")
    float likepercentage;
    @Column(name="no_of_reactions")
    float no_of_reactions;
    @Column(name="avg_rating")
    float avg_rating;
    @Column(name="no_of_reviews")
    int no_of_reviews;
    @Column(name="director")
    @NotNull
    String director;
    @Transient
    List<String> cities;
    @Transient
    List<String> cast;

    @Enumerated(EnumType.STRING)
    Genre genre;
    @Enumerated(EnumType.STRING)
    PresentationType p;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public int getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(int movie_length) {
        this.movie_length = movie_length;
    }

    public float getLikepercentage() {
        return likepercentage;
    }

    public void setLikepercentage(float likepercentage) {
        this.likepercentage = likepercentage;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }

    public int getNo_of_reviews() {
        return no_of_reviews;
    }

    public void setNo_of_reviews(int no_of_reviews) {
        this.no_of_reviews = no_of_reviews;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public PresentationType getP() {
        return p;
    }

    public void setP(PresentationType p) {
        this.p = p;
    }
}
