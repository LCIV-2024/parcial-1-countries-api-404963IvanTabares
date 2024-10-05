package ar.edu.utn.frc.tup.lciii.service.impl;

import ar.edu.utn.frc.tup.lciii.Entity.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class CountryServiceImplTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries_ShouldReturnMappedCountries() {
        // Arrange
        List<Map<String, Object>> mockApiResponse = createMockApiResponse();
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

        // Act
        List<Country> result = countryService.getAllCountries();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spain", result.get(0).getName());
        assertEquals("ESP", result.get(0).getCode());
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }

    @Test
    void getCountriesByCodeOrName_WithCode_ShouldReturnFilteredCountries() {
        // Arrange
        List<Map<String, Object>> mockApiResponse = createMockApiResponse();
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

        // Act
        List<CountryDto> result = countryService.getCountriesByCodeOrName("ESP", null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ESP", result.get(0).getCode());
        assertEquals("Spain", result.get(0).getName());
    }

    @Test
    void getCountriesByRegion_ShouldReturnFilteredCountries() {
        // Arrange
        List<Map<String, Object>> mockApiResponse = createMockApiResponse();
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

        // Act
        List<CountryDto> result = countryService.getCountriesByRegion("Europe");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ESP", result.get(0).getCode());
    }

    @Test
    void getCountriesByLang_ShouldReturnFilteredCountries() {
        // Arrange
        List<Map<String, Object>> mockApiResponse = createMockApiResponse();
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

        // Act
        List<CountryDto> result = countryService.getCountriesByLang("spa");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ESP", result.get(0).getCode());
    }

    @Test
    void saveCountrys_ShouldSaveAndReturnCountries() {
        // Arrange
        List<Map<String, Object>> mockApiResponse = createMockApiResponse();
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

        // Act
        List<CountryDto> result = countryService.saveContrys(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(countryRepository, times(1)).save(any(CountryEntity.class));
    }

    private List<Map<String, Object>> createMockApiResponse() {
        Map<String, Object> country = new HashMap<>();
        Map<String, Object> name = new HashMap<>();
        name.put("common", "Spain");
        country.put("name", name);
        country.put("population", 47000000L);
        country.put("area", 505990.0);
        country.put("cca3", "ESP");
        country.put("region", "Europe");

        Map<String, String> languages = new HashMap<>();
        languages.put("spa", "Spanish");
        country.put("languages", languages);

        List<String> borders = Arrays.asList("FRA", "PRT", "AND");
        country.put("borders", borders);

        return Collections.singletonList(country);
    }
}