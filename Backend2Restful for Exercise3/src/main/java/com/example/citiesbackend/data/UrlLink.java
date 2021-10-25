package com.example.citiesbackend.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlLink {

    String self;
    String cityLink;

    public UrlLink(String self, String cityLink) {
        this.self = self;
        this.cityLink = cityLink;
    }

}

