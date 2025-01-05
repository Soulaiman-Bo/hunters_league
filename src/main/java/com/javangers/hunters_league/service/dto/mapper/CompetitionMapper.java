package com.javangers.hunters_league.service.dto.mapper;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.service.dto.CompetitionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompetitionMapper {

    public CompetitionDTO toDTO(Competition competition) {
        return CompetitionDTO.builder()
            .id(competition.getId())
            .code(competition.getCode())
            .location(competition.getLocation())
            .date(competition.getDate())
            .speciesType(competition.getSpeciesType())
            .minParticipants(competition.getMinParticipants())
            .maxParticipants(competition.getMaxParticipants())
            .openRegistration(competition.getOpenRegistration())
            .currentParticipationCount(competition.getParticipations() != null ? competition.getParticipations().size() : 0) // Calculate participation count
            .build();
    }

    public List<CompetitionDTO> toDTOList(List<Competition> competitions) {
        return competitions.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
