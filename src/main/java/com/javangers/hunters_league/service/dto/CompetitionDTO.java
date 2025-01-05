package com.javangers.hunters_league.service.dto;

import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetitionDTO {
    private UUID id;
    private String code;
    private String location;
    private LocalDateTime date;
    private SpeciesType speciesType;
    private Integer minParticipants;
    private Integer maxParticipants;
    private Boolean openRegistration;
    private int currentParticipationCount;
}
