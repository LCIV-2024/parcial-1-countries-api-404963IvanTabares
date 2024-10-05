package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CountryController {
    @Autowired
    private CountryService CountryService;

    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryDto>> GetContrys(@RequestParam Optional<String> code,
                                                       @RequestParam Optional<String> name){
        return ResponseEntity.ok(CountryService.getCountriesByCodeOrName(code.orElse(null), name.orElse(null)));
    }
    @GetMapping("/api/countries/{continent}/continent")
    public ResponseEntity<List<CountryDto>> GetContrysbyregion(@PathVariable String continent) {
        return ResponseEntity.ok(CountryService.getCountriesByRegion(continent));
    }
    @GetMapping("/api/countries/{language}/language")
    public ResponseEntity<List<CountryDto>> GetContrysbylang(@PathVariable String language){
        return ResponseEntity.ok(CountryService.getCountriesByLang(language));
    }
    @GetMapping("/api/countries/most-borders")
    public ResponseEntity<CountryDto> getcontrymostBorders(){
        return ResponseEntity.ok(CountryService.Mostborders());
    }
    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryDto>> postCountrys(@RequestBody CountryRequestDto request) {
        int cant = request.getAmountOfCountryToSave();
        return ResponseEntity.ok(CountryService.saveContrys(cant));
    }

}