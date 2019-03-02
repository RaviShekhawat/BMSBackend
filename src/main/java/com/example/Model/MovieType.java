package com.example.Model;

import java.util.Arrays;

public enum MovieType {

    Hindi("Hindi"),
    English("English"),
    Telugu("Telugu"),
    Malyalam("Malyalam"),
    Kannada("Kannnada");

    private String name;

    public String getName() {
        return name;
    }

    MovieType(String name) {
        this.name = name;
    }

    public static MovieType fromValue(String value) {
        for (MovieType category : values()) {
            if (category.name.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }

}
