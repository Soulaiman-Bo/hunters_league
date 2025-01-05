package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CompetitionService {
    Competition createCompetition(Competition competition);
    Page<Competition> getCompetitionsByMember(UUID memberId, Pageable pageable);
    Competition getCompetition(UUID id);
    Page<Competition> getAllCompetitions(Pageable pageable);
    List<LeaderboardPositionDTO> getCompetitionLeaderboard(UUID competitionId);
}
