package com.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity(name="theatrehall")
public class TheatreHall {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long hallid;
    @Column(name="no_of_rows")
    @NotNull
    private Byte rows;
    @Column(name="no_of_columns")
    @NotNull
    private Byte columns;
    @Column(name="is_active")
    private boolean is_active;
    @Column(name="theatre_id")
    //@NotNull
    private long theatre_id;
    @Column(name="movie_id")
    private long movie_id;
    @Column(name = "movie_start_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date start_date;
    @Column(name = "movie_end_date")
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
    private Date end_date;
    @Column(name="vacant_seats")
    private int vacant_seats;

    @ElementCollection
    @MapKeyColumn()
    @Column()
    @CollectionTable(name = "halls_vacant", joinColumns = @JoinColumn(name = "theatrehall_id"))
    private Map<String,LinkedHashMap<Byte,LinkedHashSet<Byte>>> vacantseatscount = new LinkedHashMap<>();

    TheatreHall()
    {

    }

    public Map<String, LinkedHashMap<Byte, LinkedHashSet<Byte>>> getVacantseatscount() {
        return vacantseatscount;
    }

    public void setVacantseatscount(Map<String, LinkedHashMap<Byte, LinkedHashSet<Byte>>> vacantseatscount) {
        this.vacantseatscount = vacantseatscount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setHallid(long hallid) {
        this.hallid = hallid;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public long getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(long theatre_id) {
        this.theatre_id = theatre_id;
    }

    public int getVacant_seats() {
        return vacant_seats;
    }

    public void setVacant_seats(int vacant_seats) {
        this.vacant_seats = vacant_seats;
    }

    public long getHallid() {
        return hallid;
    }

    public void setHallid(Long hallid) {
        this.hallid = hallid;
    }

    public Byte getRows() {
        return rows;
    }

    public void setRows(Byte rows) {
        this.rows = rows;
    }

    public Byte getColumns() {
        return columns;
    }

    public void setColumns(Byte columns) {
        this.columns = columns;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }


    @Override
    public String toString() {
        return "TheatreHall{" +
                "hallid=" + hallid +
                ", rows=" + rows +
                ", columns=" + columns +
                ", is_active=" + is_active +
                ", theatre_id=" + theatre_id +
                ", movie_id=" + movie_id +
                ", vacant_seats=" + vacant_seats +
                ", vacantseatscount=" + vacantseatscount +
                '}';
    }
}
