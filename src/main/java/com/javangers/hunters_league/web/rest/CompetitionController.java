package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.web.vm.CompetitionRequestVM;
import com.javangers.hunters_league.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;

    @PostMapping
    public ResponseEntity<Competition> CreateSpecies(@Valid @RequestBody CompetitionRequestVM request) {
        Competition competition = competitionMapper.toEntity(request);
        Competition created = competitionService.createCompetition(competition);
        return ResponseEntity.created(URI.create("/api/competitions/" + created.getId()))
                .body(created);

    }

}
