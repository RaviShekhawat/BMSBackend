package com.example.Model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

import java.util.List;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Entity(name="movie")
public class Movie {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="movie_name")
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private MovieType type;

    @Column(name="release_date")
    @JsonFormat(shape=STRING, pattern="dd-MM-yyyy")
    @NotNull
    private Date releaseDate;

    @Column(name="movie_length")
    @Max(value=240,message = "Movie Length Exceeded")
    private Double movieLength;

    @Column(name="like_percentage")
    private Double likePercentage;

    @Column(name="no_of_reactions")
    private int noOfReactions;

    @Column(name="avg_rating")
    @Min(value=0,message = "Negative rating not allowed")
    @Max(value=5,message = "Rating>5.0 not allowed")
    private Double avgRating;

    @Column(name="no_of_reviews")
    private int noOfReviews;

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
    private PresentationType presentationType;


}
