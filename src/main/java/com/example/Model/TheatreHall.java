package com.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Entity(name="theatrehall")
public class TheatreHall {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long hallId;

    @Column(name="no_of_rows")
    @NotNull
    private Byte rows;

    @Column(name="no_of_columns")
    @NotNull
    private Byte columns;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="theatre_id")
    private long theatreId;

    @Column(name="movie_id")
    private long movieId;

    @Column(name = "movie_start_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date startDate;

    @Column(name = "movie_end_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date endDate;

    @Column(name="vacant_seats")
    private int vacantSeats;

    @ElementCollection
    @MapKeyColumn()
    @Column()
    @CollectionTable(name = "halls_vacant", joinColumns = @JoinColumn(name = "theatrehall_id"))
    private Map<String,LinkedHashMap<Byte,LinkedHashSet<Byte>>> vacantSeatsCount = new LinkedHashMap<>();



}
