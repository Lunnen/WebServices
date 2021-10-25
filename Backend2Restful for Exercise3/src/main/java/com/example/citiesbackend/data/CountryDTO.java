package com.example.citiesbackend.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryDTO {

    private String name;
    private String capital;
    private UrlLink cityLink;

    public CountryDTO(Country country) {
        this.name = country.getName();
        this.capital = country.getCapital().getName();
        this.cityLink = country.get_links();
    }
}
