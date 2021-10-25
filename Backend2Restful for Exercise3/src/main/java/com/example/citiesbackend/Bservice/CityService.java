package com.example.citiesbackend.Bservice;

import com.example.citiesbackend.Crepository.CityRepository;
import com.example.citiesbackend.data.City;
import com.example.citiesbackend.data.input.CityUpdate;

import java.util.Arrays;
import java.util.Collection;

public class CityService {

    public static final CityService INSTANCE = new CityService();

    private CityRepository repository = new CityRepository();

    public City saveCity(City city) {
        repository.save(city);
        return city;
    }

    public boolean deleteCity(String name) {
        City city = getCity(name);
        if (city == null)
            return false;

        repository.remove(city);
        return true;
    }

    public boolean deleteAll() {
        repository.clearAll();

        return getCities().size() == 0;
    }

    public boolean updateCity(String name, CityUpdate cityUpdate) {
        City city = getCity(name);
        if (city == null)
            return false;

        city.setSize(cityUpdate.getSize());
        city.setPopulation(cityUpdate.getPopulation());
        return true;
    }

    public City getCity(String name) {
        return repository.getCity(name);
    }

    public Collection<City> getCities() {
        return repository.getCities();
    }

    public Collection<City> createCities(City[] cityName) {

        Arrays.stream(cityName).forEach(s -> repository.save(s));

        return repository.getCities();
    }
}
