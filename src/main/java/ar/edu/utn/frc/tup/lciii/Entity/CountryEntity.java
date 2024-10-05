package ar.edu.utn.frc.tup.lciii.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Countrys")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContry;
    @Column
    private String code;
    @Column
    private long population;
    @Column
    private String name;
    @Column
    private double area;

}
