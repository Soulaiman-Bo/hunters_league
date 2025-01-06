package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species, UUID> {
    Page<Species> findAll(Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
    Page<Species> findByCategory(SpeciesType category, Pageable pageable);
}
