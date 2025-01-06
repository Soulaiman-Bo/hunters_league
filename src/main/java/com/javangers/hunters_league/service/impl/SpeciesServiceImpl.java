package com.javangers.hunters_league.service.impl;


import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import com.javangers.hunters_league.repository.SpeciesRepository;
import com.javangers.hunters_league.service.SpeciesService;
import com.javangers.hunters_league.web.errors.SpeciesAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Override
    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    @Override
    public Species createSpecies(Species species){

        if (speciesRepository.existsByNameIgnoreCase(species.getName())) {
            throw new SpeciesAlreadyExistsException("Species with this name already exists");
        }

        return speciesRepository.save(species);
    }

    @Override
    public Page<Species> findByCategory(SpeciesType category, Pageable pageable) {
        return speciesRepository.findByCategory(category, pageable);
    }

    public Species updateSpecies(Species species) {
        Species existingSpecies = speciesRepository.findById(species.getId())
                .orElseThrow(() -> new EntityNotFoundException("Species not found"));

        if (!existingSpecies.getName().equalsIgnoreCase(species.getName()) &&
            speciesRepository.existsByNameIgnoreCase(species.getName())) {
            throw new SpeciesAlreadyExistsException("Species with this name already exists");
        }

        return speciesRepository.save(species);
    }

}
