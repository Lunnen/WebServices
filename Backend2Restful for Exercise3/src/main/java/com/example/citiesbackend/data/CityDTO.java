package com.example.citiesbackend.data;

import lombok.Data;

@Data
public class CityDTO {
    private String name;
    private int population;
    private int size;

    public CityDTO(City city) {
        this.name = city.getName();
        this.population = city.getPopulation();
        this.size = city.getSize();
    }
}
