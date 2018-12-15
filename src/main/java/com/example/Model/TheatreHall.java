package com.example.Model;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class TheatreHall {

    private byte hallid;
    private byte rows;
    private byte columns;
    private boolean is_active;
    private Movie movie;
    private int vacant_seats;
    private boolean arr[][];
    Map<Byte,Byte> vacantseatscount;

    TheatreHall(byte hallid,byte rows,byte columns)
    {
        this.hallid = hallid;
        this.rows = rows;
        this.columns = columns;
        this.arr = new boolean[rows][columns];
        vacant_seats = rows*columns;
        for(byte i=0;i<rows;i++)
        {
            vacantseatscount.put(i,columns);
        }
    }

    public static <K, V extends Comparable<V>> Map<K, V>
    sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k1).compareTo(map.get(k2));
                        if (compare == 0)
                            return 1;
                        else
                            return compare*-1;
                    }
                };
        Map<K, V> sortedByValues =
                new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public Map<Byte, Byte> getVacantseatscount() {
        return vacantseatscount;
    }

    public void setVacantseatscount(Map<Byte, Byte> vacantseatscount) {
        this.vacantseatscount = vacantseatscount;
    }

    public int getVacant_seats() {
        return vacant_seats;
    }

    public void setVacant_seats(int vacant_seats) {
        this.vacant_seats = vacant_seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public byte getHallid() {
        return hallid;
    }

    public void setHallid(byte hallid) {
        this.hallid = hallid;
    }

    public byte getRows() {
        return rows;
    }

    public void setRows(byte rows) {
        this.rows = rows;
    }

    public byte getColumns() {
        return columns;
    }

    public void setColumns(byte columns) {
        this.columns = columns;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean[][] getArr() {
        return arr;
    }

    public void setArr(boolean[][] arr) {
        this.arr = arr;
    }

}
