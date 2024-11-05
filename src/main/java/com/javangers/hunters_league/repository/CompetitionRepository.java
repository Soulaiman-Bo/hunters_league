package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    @Query("SELECT COUNT(c) > 0 FROM Competition c WHERE c.date BETWEEN ?1 AND ?2")
    boolean existsInWeek(LocalDateTime weekStart, LocalDateTime weekEnd);

    boolean existsByCode(String code);
}
