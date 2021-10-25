package com.example.citiesbackend.data;

import lombok.Data;

@Data
public class Country{

    private String name;
    private City capital;
    private UrlLink _links;

    public Country(String name, City capital) {
        this.name = name;
        this.capital = capital;
        _links = new UrlLink("/countries/" + this.getName(),
                "/city/info/" + this.getCapital().getName());

    }

    public void updateObject(City city){
        this.capital = city;
        this._links = new UrlLink("/countries/" + this.getName(),
                "/city/info/" + this.getCapital().getName());
    }
}
