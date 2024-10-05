package ar.edu.utn.frc.tup.lciii.service.impl;

import ar.edu.utn.frc.tup.lciii.Entity.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Country> getAllCountries() {
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
    }

    /**
     * Agregar mapeo de campo cca3 (String)
     * Agregar mapeo campos borders ((List<String>))
     */
    private Country mapToCountry(Map<String, Object> countryData) {
        Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
        List<String> borders = new ArrayList<>();
        if (countryData.containsKey("borders")) {
            List<String> borderList = (List<String>) countryData.get("borders");
            borders.addAll(borderList);
        }
        return Country.builder()
                .name((String) nameData.get("common"))
                .population(((Number) countryData.get("population")).longValue())
                .area(((Number) countryData.get("area")).doubleValue())
                .code((String) countryData.get("cca3"))
                .region((String) countryData.get("region"))
                .languages((Map<String, String>) countryData.get("languages"))
                .borders(borders)
                .build();
    }


    public List<CountryDto> getCountriesByCodeOrName(String code, String name) {
        List<CountryDto> filteredCountries = new ArrayList<>();

        List<Country> countries = getAllCountries();
        for (Country country : countries) {
            if (code != null || name != null ){if ((country.getCode().equalsIgnoreCase(code)) ||
                    ( country.getName().equalsIgnoreCase(name))) {
                filteredCountries.add(new CountryDto(country.getCode(), country.getName()));
            }}
            else
            {
                filteredCountries.add(mapToDTO(country));
            }
        }


        return filteredCountries;
    }
    public List<CountryDto> getCountriesByRegion( String cont) {
        List<CountryDto> filteredCountries = new ArrayList<>();

        List<Country> countries = getAllCountries();
        for (Country country : countries) {
            if (country.getRegion().equalsIgnoreCase(cont)) {
                filteredCountries.add(mapToDTO(country));
            }

        }


        return filteredCountries;
    }
    public List<CountryDto> getCountriesByLang( String lang) {
        List<CountryDto> filteredCountries = new ArrayList<>();

        List<Country> countries = getAllCountries();
        for (Country country : countries) {
            if (country.getLanguages().containsKey(lang)) {
                filteredCountries.add(mapToDTO(country));
            }

        }
        return filteredCountries;
    }
    public CountryDto Mostborders() {
        CountryDto contry = new CountryDto();
        int fronteras = 0;
        List<Country> countries = getAllCountries();
        for (Country country : countries) {
            if (country.getBorders().size() > fronteras) {
                contry.setCode(country.getCode());
                contry.setName(country.getName());
                fronteras = country.getBorders().size();
            }

        }
        return contry;
    }


    private CountryDto mapToDTO(Country country) {
        return new CountryDto(country.getCode(), country.getName());
    }
    public List<CountryDto> saveContrys(int cant) {
        List<CountryDto> countriesDto = new ArrayList<>();
        List<Country> countries = getAllCountries();
        Collections.shuffle(countries);
        for (int i = 0; i < cant; i++) {
            countriesDto.add(mapToDTO(countries.get(i)));
            CountryEntity entity = new CountryEntity();
            entity.setCode(countries.get(i).getCode());
            entity.setName(countries.get(i).getName());
            entity.setPopulation(countries.get(i).getPopulation());
            entity.setArea(countries.get(i).getArea());
            countryRepository.save(entity);
        }

        return countriesDto;
    }
}
