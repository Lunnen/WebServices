package com.example.citiesbackend;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryDTO {

    private String name;
    private String cityLink;

    public CountryDTO(String name, String cityLink) {
        this.name = name;
        this.cityLink = cityLink;
    }
}
