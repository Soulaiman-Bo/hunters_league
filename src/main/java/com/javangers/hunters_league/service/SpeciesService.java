package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpeciesService {
    Page<Species> findAll(Pageable pageable);
    Species createSpecies(Species species);

    Page<Species> findByCategory(SpeciesType category, Pageable pageable);

    Species updateSpecies(Species species);
}
