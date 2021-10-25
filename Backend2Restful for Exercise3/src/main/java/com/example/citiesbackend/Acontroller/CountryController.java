package com.example.citiesbackend.Acontroller;

import com.example.citiesbackend.Bservice.CountryService;
import com.example.citiesbackend.data.Country;
import com.example.citiesbackend.data.CountryDTO;
import com.example.citiesbackend.data.input.CountryCreate;
import com.example.citiesbackend.data.input.UpdateCountry;
import com.example.citiesbackend.shared.Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/*
-Skapa länder och städer
-Ta bort länder och städer
-Visa upp/Hämta länder och städer
-Hämta specifikt land och stad
 */
@CrossOrigin
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService service = new CountryService();

    @PostMapping("/add")
    public CountryDTO addCountry(@RequestBody CountryCreate countryCreate, HttpServletResponse response) throws IOException {
        Country countryName = service.createCountry(countryCreate);

        if (countryName == null) {
            response.setStatus(404);
            response.getWriter().print(
                    "Could not create '" + countryCreate.getName() + "'.\n" +
                    "'cause " + countryCreate.getCapital() + " does not exists!");
            return null;
        }
        return new CountryDTO(countryName);
    }
    @PostMapping("/create/many")
    public Collection<CountryDTO> createCountries(@RequestBody CountryCreate[] countryNames) {

        Collection<Country> newCountries = service.createCountries(countryNames);

        return newCountries
                .stream()
                .map(CountryDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public Collection<Country> getAllCountries(){
        return service.getAll();
    };

    @GetMapping("/{countryName}")
    public Collection<Country> getSpecificCountry(@PathVariable("countryName") String name, HttpServletResponse response) throws IOException {
        String countryName = Utils.capitalize(name);

        if(!service.countryExists(countryName)) {
            response.setStatus(404);
            response.getWriter().print("'" + countryName + "' does not exists!");
            return null;
        }
        return service.getSpecific(countryName);
    };

    @DeleteMapping("/delete/{countryName}")
    public String deleteCountry(@PathVariable("countryName") String name, HttpServletResponse response) {
        if(!service.delete(name)) {
            response.setStatus(404);
            return "There is no country named '" + name + "'";
        }

        return "You've deleted '" + name + "'";
    }
    @DeleteMapping("/deleteall")
    public String deleteAll(HttpServletResponse response) {

        if(!service.deleteall()) {
            response.setStatus(404);
            return "Couldn't delete all countries";
        }

        return "You've deleted all countries";
    }

    @PatchMapping("/update/{countryName}")
    public Country updateCountry(@PathVariable("countryName") String countryName,
                                 @RequestBody UpdateCountry update, HttpServletResponse response) throws IOException {
        Country result = service.updateCountry(Utils.capitalize(countryName), update.getCityName());

        if (result == null) {
            response.setStatus(404);
            response.getWriter().print(
                    "'" + countryName + "' couldn't be updated for some unknown reason!");
            return null;
        } else {
            return result;
        }
    }
}


