package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.dto.CompetitionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CompetitionService {
    Competition createCompetition(Competition competition);
    Page<CompetitionDTO> getCompetitionsByMember(UUID memberId, Pageable pageable);

    Page<CompetitionDTO>  getUpcomingCompetitions(Pageable pageable);

    Competition getCompetition(UUID id);
    Page<CompetitionDTO> getAllCompetitions(Pageable pageable);
    List<LeaderboardPositionDTO> getCompetitionLeaderboard(UUID competitionId);
}
