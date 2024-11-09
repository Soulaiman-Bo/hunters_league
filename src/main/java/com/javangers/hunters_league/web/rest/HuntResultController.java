package com.javangers.hunters_league.web.rest;


import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Hunt;
import com.javangers.hunters_league.service.HuntService;
import com.javangers.hunters_league.web.vm.HuntResultDTO;
import com.javangers.hunters_league.web.vm.mapper.HuntMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class HuntResultController {

    private final HuntService huntService;
    private final HuntMapper huntMapper;

    @PostMapping("/competition/{competitionId}")
    public ResponseEntity<Hunt> submitCompetitionResult(
            @PathVariable UUID competitionId,
            @Valid @RequestBody HuntResultDTO resultDTO) {

        Hunt hunt = huntMapper.toEntity(resultDTO);
        hunt.getParticipation().setCompetition(Competition.builder().id(competitionId).build());
        Hunt savedHunt = huntService.submitCompetitionResult(hunt);

        return ResponseEntity.ok(savedHunt);
    }
}
