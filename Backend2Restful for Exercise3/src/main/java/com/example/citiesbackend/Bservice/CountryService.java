package com.example.citiesbackend.Bservice;

import com.example.citiesbackend.Crepository.CountryRepository;
import com.example.citiesbackend.data.City;
import com.example.citiesbackend.data.Country;
import com.example.citiesbackend.data.input.CountryCreate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CountryService {

    CountryRepository repository = new CountryRepository();
    private CityService cityService = CityService.INSTANCE;

    public Collection<Country> getAll() {
        return repository.getAll();
    }

    //Returns null if country doesnt exist
    public boolean countryExists(String name) {
        return repository.countryExists(name);
    }

    public Collection<Country> getSpecific(String name) {

        return repository.getAll()
                .stream()
                .filter(x -> x.getName().toLowerCase().matches(".*" + name.toLowerCase() + ".*") ||
                        x.getCapital().getName().toLowerCase().matches(".*" + name.toLowerCase() + ".*"))
                .collect(Collectors.toList());
    }

    public boolean delete(String name) {
        Country chosenCountry = getCountry(name);
        if (chosenCountry == null)
            return false;

        repository.remove(chosenCountry);
        return true;
    }

    public Country getCountry(String name) {
        return repository.getCountry(name);
    }

    public boolean deleteall() {
        repository.clearAll();

        return repository.getAll().size() == 0;
    }

    public Country createCountry(CountryCreate countryCreate) {
        City city = cityService.getCity(countryCreate.getCapital());
        if (city == null)
            return null;

        Country country = new Country(countryCreate.getName(), city);
        repository.createCountry(country);
        return country;
    }

    public Collection<Country> createCountries(CountryCreate[] countryNames) {
        Collection<Country> theseCountries = new ArrayList();

        for (CountryCreate countryName : countryNames) {

            City city = cityService.getCity(countryName.getCapital());
            if (city == null)
                return null;

            Country country = new Country(countryName.getName(), city);

            theseCountries.add(country);
        }

       theseCountries.forEach(s -> repository.createCountry(s));

        return repository.getAll();
    }

    public Country updateCountry(String countryName, String newCityName) {
        Country country = repository.getCountry(countryName);
        if(country == null) return null;

        City newCity = cityService.getCity(newCityName);
        if (newCity == null)
            return null;

        country.updateObject(newCity);

        return country;
    }


}
