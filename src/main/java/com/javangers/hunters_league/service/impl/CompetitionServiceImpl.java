package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.repository.CompetitionRepository;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.service.dto.CompetitionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardProjectionInterface;
import com.javangers.hunters_league.service.dto.mapper.CompetitionMapper;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;


    @Override
    public Competition createCompetition(Competition competition) {
        validateCompetition(competition);
        return competitionRepository.save(competition);
    }

    @Override
    public Page<CompetitionDTO> getCompetitionsByMember(UUID memberId, Pageable pageable) {
        Page<Competition> competitions = competitionRepository.findCompetitionsByMember(memberId, pageable);
        return competitions.map(competitionMapper::toDTO);
    }

    @Override
    public Page<CompetitionDTO> getUpcomingCompetitions(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Competition> competitions = competitionRepository.findUpcomingCompetitions(now, pageable);
        return competitions.map(competitionMapper::toDTO);
    }


    @Override
    public Competition getCompetition(UUID id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id));
    }

    @Override
    public Page<CompetitionDTO> getAllCompetitions(Pageable pageable) {
        Page<Competition> competitions = competitionRepository.findAll(pageable);
        return competitions.map(competitionMapper::toDTO);
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

    @Override
    @Transactional
    public List<LeaderboardPositionDTO> getCompetitionLeaderboard(UUID competitionId) {
        List<LeaderboardProjectionInterface> leaderboard = competitionRepository.findTop3ByCompetitionIdNative(competitionId);

        if (leaderboard.isEmpty()) {
            throw new BusinessValidationException("No participants found in this competition");
        }

        return leaderboard.stream()
                .map(projection -> LeaderboardPositionDTO.builder()
                        .userFullName(projection.getUsername())
                        .score(projection.getScore())
                        .rank(leaderboard.indexOf(projection) + 1)
                        .competitionCode(projection.getCompetitionCode())
                        .build())
                .collect(Collectors.toList());
    }
}
