package com.example.citiesbackend;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*
-Skapa länder och städer
-Ta bort länder och städer
-Visa upp/Hämta länder och städer
-Hämta specifikt land och stad
 */
@CrossOrigin
@RestController
public class MainController {

    private Map<String, Country> countries = new HashMap<>();
    private Map<String, City> cities = new HashMap<>();

    public MainController() {
        createCountry("Sverige", "Stockholm");
        createCountry("Finland", "Helsingfors");
    }

    @PutMapping("/countries/add")
    public Country addCountry(@RequestBody CountryDTO countryDTO) {
        return createCountry(countryDTO.getCityLink(), countryDTO.getName());
    }

    @DeleteMapping("/countries/delete/{countryName}")
    public boolean deleteCountry(@PathVariable("countryName") String name, HttpServletResponse response) {
        if(!countries.containsKey(name)) response.setStatus(404);
        return removeCountry(name);
    }

    @GetMapping("/countries/all")
    public Collection<Country> getAllCountries(){
        return countries.values();
    };

    @GetMapping("/countries/{countryName}")
    public Collection<Country> getAllCountries(@PathVariable("countryName") String name, HttpServletResponse response){
        if(!countries.containsKey(name)) response.setStatus(404);
        return countries.values()
                .stream()
                .filter(x -> x.getName().toLowerCase().matches(".*" + name.toLowerCase() + ".*") ||
                        x.getCapital().getName().toLowerCase().matches(".*" + name.toLowerCase() + ".*"))

                .collect(Collectors.toList());
    };


    public Country createCountry(String cityName, String countryName) {
        City city = new City(cityName);
        Country country = new Country(countryName, city);
        cities.put(cityName, city);
        countries.put(countryName, country);
        return country;
    }

    public boolean removeCountry(String name) {
        if(countries.containsKey(name)) {
            String city = countries.get(name).getCapital().getName();
            cities.remove(city);
            countries.remove(name);
            return true;
        } return false;
    }
}


