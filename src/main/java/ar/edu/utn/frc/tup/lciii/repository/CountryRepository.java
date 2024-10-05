package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.Entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Completar segun corresponda
 */
@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
