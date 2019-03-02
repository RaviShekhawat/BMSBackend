package com.example.Model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
//import org.jetbrains.annotations.Nullable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.hibernate.validator.constraints.time.DurationMax;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity(name="movie")
public class Movie {

    Movie(){}
    /*public Movie(String name,MovieType type,Date releasedate,
          int movie_length, Genre genre)
    {
        this.name=name;
        this.type=type;
        this.releasedate=releasedate;
        this.movie_length=movie_length;
        this.genre=genre;
    }*/

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Column(name="movie_name")
    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    private MovieType type;
    @Column(name="release_date")
    @JsonFormat(shape=STRING, pattern="dd-MM-yyyy")
    @NotNull
    private Date releasedate;
    @Column(name="movie_length")
    //@NotNull
    @Max(value=240,message = "Movie Length Exceeded")
    private float movie_length;
    @Column(name="like_percentage")
    private float likepercentage;
    @Column(name="no_of_reactions")
    private int no_of_reactions;
    @Column(name="avg_rating")
    @Min(value=0,message = "Negative rating not allowed")
    @Max(value=5,message = "Rating>5.0 not allowed")
    private float avg_rating;
    @Column(name="no_of_reviews")
    private int no_of_reviews;
    @Column(name="director")
    @NotNull
    private String director;
    @ElementCollection
    @CollectionTable(name = "movie_cities", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name="cities")
    private List<String> cities = new ArrayList<String>();
    @ElementCollection
    @CollectionTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name="cast")
    private List<String> cast = new ArrayList<String>();
    @Enumerated(EnumType.STRING)
    @Column(name="genre")
    private Genre genre;
    @Enumerated(EnumType.STRING)
    @Column(name="presentationtype")
    private PresentationType p;

    public int getNo_of_reactions() {
        return no_of_reactions;
    }

    public void setNo_of_reactions(int no_of_reactions) {
        this.no_of_reactions = no_of_reactions;
    }

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

    public float getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(float movie_length) {
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", releasedate=" + releasedate +
                ", movie_length=" + movie_length +
                ", likepercentage=" + likepercentage +
                ", no_of_reactions=" + no_of_reactions +
                ", avg_rating=" + avg_rating +
                ", no_of_reviews=" + no_of_reviews +
                ", director='" + director + '\'' +
                ", cities=" + cities +
                ", cast=" + cast +
                ", genre=" + genre +
                ", p=" + p +
                '}';
    }
}
