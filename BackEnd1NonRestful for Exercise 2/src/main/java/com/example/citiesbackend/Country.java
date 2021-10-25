package com.example.citiesbackend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country{

    private String name;
    private City capital;

    public Country(String name, City capital) {
        this.name = name;
        this.capital = capital;
    }
}
