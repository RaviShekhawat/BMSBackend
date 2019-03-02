package com.example.Model;

import java.util.Arrays;

public enum Genre {

    Drama("Drama"),
    Romcom("Romcom"),
    Mystery("Mystery"),
    Thriller("Thriller"),
    SciFi("SciFi"),
    Biopic("Biopic"),
    Historical("Historical"),
    Crime("Crime"),
    Comedy("Comedy");

    private String name;

    public String getName() {
        return name;
    }

    Genre(String name) {
        this.name = name;
    }

    public static Genre fromValue(String value) {
        for (Genre category : values()) {
            if (category.name.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}
