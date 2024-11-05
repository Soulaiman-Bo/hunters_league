package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Participation;

import java.util.UUID;

public interface ParticipationService {
    Participation registerForCompetition(UUID competitionId, UUID userId);
}
