package com.example.citiesbackend.Acontroller;

import com.example.citiesbackend.Bservice.CityService;
import com.example.citiesbackend.data.City;
import com.example.citiesbackend.data.CityDTO;
import com.example.citiesbackend.data.input.CityUpdate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CityController {

    private CityService service = CityService.INSTANCE;

    public CityController() {
        createCity(new City("Helsinki", 1305893, 683));
        createCity(new City("Stockholm", 16111776, 416));
    }

    @PostMapping("/city/create")
    public CityDTO createCity(@RequestBody City city) {
        city = service.saveCity(city);
        return new CityDTO(city);
    }

    @PostMapping("/city/create/many")
    public Collection<CityDTO> createCities(@RequestBody City[] cityName) {

        Collection<City> newCities = service.createCities(cityName);

        return newCities
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    @PatchMapping("/city/update/{cityName}")
    public CityDTO updateCity(@PathVariable("cityName") String cityName, @RequestBody CityUpdate cityUpdate, HttpServletResponse response) {
        if (!service.updateCity(cityName, cityUpdate)) {
            response.setStatus(404);
            return null;
        }

        City city = service.getCity(cityName);
        return new CityDTO(city);
    }

    @GetMapping("/city/all")
    public List<CityDTO> getAllCities() {
        return service.getCities()
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/city/info/{cityName}")
    public CityDTO getInfo(@PathVariable("cityName") String cityName, HttpServletResponse response) {
        City city = service.getCity(cityName);
        if (city == null) {
            response.setStatus(404);
            return null;
        }

        return new CityDTO(city);
    }

    @DeleteMapping("/city/delete/{cityName}")
    public String deleteCity(@PathVariable("cityName") String cityName, HttpServletResponse response) {
        if (!service.deleteCity(cityName)) {
            response.setStatus(404);
            return "There is no city named '" + cityName + "'";
        }
        return "You have deleted '" + cityName + "'";
    }

    @DeleteMapping("/city/deleteall")
    public String deleteAllCities(HttpServletResponse response) {
        if (!service.deleteAll()) {
            response.setStatus(404);
            return "Unknown error when trying to delete all cities";
        }
        return "You have deleted all cities";
    }


}
