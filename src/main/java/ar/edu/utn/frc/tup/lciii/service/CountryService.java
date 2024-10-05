package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {
    List<CountryDto> getCountriesByCodeOrName(String s, String s1);
    List<CountryDto> getCountriesByRegion( String cont);
     List<CountryDto> getCountriesByLang( String lang);
     CountryDto Mostborders();
    List<CountryDto> saveContrys(int cant);
}
