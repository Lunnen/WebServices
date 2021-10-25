package com.example.citiesbackend.Crepository;

import com.example.citiesbackend.data.Country;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CountryRepository {
    private Map<String, Country> countries = new HashMap<>();

    public Collection<Country> getAll() {
        return countries.values();
    }

    public boolean countryExists(String name) {
        return countries.containsKey(name.toLowerCase());
    }

    public void remove(Country country) {
        countries.remove(country.getName().toLowerCase());
    }

    public void createCountry(Country country) {
         countries.put(country.getName().toLowerCase(), country);
    }
    public void clearAll() { countries.clear(); }

    public Country getCountry(String name) {
        return countries.get(name.toLowerCase());
    }
}
