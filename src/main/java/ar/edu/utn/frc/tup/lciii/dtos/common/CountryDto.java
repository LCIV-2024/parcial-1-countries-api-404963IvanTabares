package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private String code;
    private String name;
}
