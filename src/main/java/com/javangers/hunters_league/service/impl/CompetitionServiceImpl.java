package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.repository.CompetitionRepository;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;


    @Override
    public Competition createCompetition(Competition competition) {
        validateCompetition(competition);
        return competitionRepository.save(competition);
    }


    @Override
    public Competition getCompetition(UUID id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id));
    }

    private void validateCompetition(Competition competition) {
        // Validate min/max participants
        if (competition.getMinParticipants() >= competition.getMaxParticipants()) {
            throw new BusinessValidationException("Minimum participants must be less than maximum participants");
        }

        // Validate code format
        String expectedCode = String.format("%s-%s", competition.getLocation(), competition.getDate().toLocalDate());
        if (!competition.getCode().equals(expectedCode)) {
            throw new BusinessValidationException("Invalid competition code format");
        }

        // Validate one competition per week
        LocalDateTime weekStart = competition.getDate().truncatedTo(ChronoUnit.DAYS).with(DayOfWeek.MONDAY);
        LocalDateTime weekEnd = weekStart.plusDays(6);

        if (competitionRepository.existsInWeek(weekStart, weekEnd)) {
            throw new BusinessValidationException("Only one competition per week is allowed");
        }
    }
}