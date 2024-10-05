package ar.edu.utn.frc.tup.lciii.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


class CountryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    void getCountries_WithNoParams_ShouldReturnAllCountries() throws Exception {

        List<CountryDto> mockCountries = Arrays.asList(
                new CountryDto("ESP", "Spain"),
                new CountryDto("FRA", "France")
        );
        when(countryService.getCountriesByCodeOrName(null, null)).thenReturn(mockCountries);


        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ESP"))
                .andExpect(jsonPath("$[0].name").value("Spain"))
                .andExpect(jsonPath("$[1].code").value("FRA"))
                .andExpect(jsonPath("$[1].name").value("France"));
    }

    @Test
    void getCountries_WithCodeParam_ShouldReturnFilteredCountries() throws Exception {

        List<CountryDto> mockCountries = Arrays.asList(
                new CountryDto("ESP", "Spain")
        );
        when(countryService.getCountriesByCodeOrName("ESP", null)).thenReturn(mockCountries);


        mockMvc.perform(get("/api/countries").param("code", "ESP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ESP"))
                .andExpect(jsonPath("$[0].name").value("Spain"));
    }

    @Test
    void getCountriesByRegion_ShouldReturnFilteredCountries() throws Exception {

        List<CountryDto> mockCountries = Arrays.asList(
                new CountryDto("ESP", "Spain"),
                new CountryDto("FRA", "France")
        );
        when(countryService.getCountriesByRegion("Europe")).thenReturn(mockCountries);

        mockMvc.perform(get("/api/countries/Europe/continent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ESP"))
                .andExpect(jsonPath("$[1].code").value("FRA"));
    }

    @Test
    void getCountriesByLanguage_ShouldReturnFilteredCountries() throws Exception {

        List<CountryDto> mockCountries = Arrays.asList(
                new CountryDto("ESP", "Spain")
        );
        when(countryService.getCountriesByLang("Spanish")).thenReturn(mockCountries);

        mockMvc.perform(get("/api/countries/Spanish/language"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ESP"));
    }

    @Test
    void getCountryWithMostBorders_ShouldReturnCountry() throws Exception {

        CountryDto mockCountry = new CountryDto("BRA", "Brazil");
        when(countryService.Mostborders()).thenReturn(mockCountry);

        mockMvc.perform(get("/api/countries/most-borders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("BRA"))
                .andExpect(jsonPath("$.name").value("Brazil"));
    }

    @Test
    void postCountries_ShouldReturnSavedCountries() throws Exception {

        List<CountryDto> mockSavedCountries = Arrays.asList(
                new CountryDto("ESP", "Spain"),
                new CountryDto("FRA", "France")
        );
        when(countryService.saveContrys(2)).thenReturn(mockSavedCountries);

        String requestBody = "{\"amountOfCountryToSave\": 2}";


        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ESP"))
                .andExpect(jsonPath("$[1].code").value("FRA"));
    }
}