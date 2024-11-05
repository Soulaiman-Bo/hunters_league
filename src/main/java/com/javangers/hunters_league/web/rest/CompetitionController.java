package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.service.ParticipationService;
import com.javangers.hunters_league.web.vm.CompetitionRequestVM;
import com.javangers.hunters_league.web.vm.ParticipationRequestVM;
import com.javangers.hunters_league.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;
    private final ParticipationService participationService;


    @PostMapping
    public ResponseEntity<Competition> CreateSpecies(@Valid @RequestBody CompetitionRequestVM request) {
        Competition competition = competitionMapper.toEntity(request);
        Competition created = competitionService.createCompetition(competition);
        return ResponseEntity.created(URI.create("/api/competitions/" + created.getId()))
                .body(created);

    }

    @GetMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetition(
            @PathVariable UUID competitionId) {
        Competition competition = competitionService.getCompetition(competitionId);
        return ResponseEntity.ok(competition);
    }

    @PostMapping("/{competitionId}/register")
    public ResponseEntity<Participation> registerForCompetition(
            @PathVariable UUID competitionId,
            @Valid @RequestBody ParticipationRequestVM request) {

        Participation participation = participationService.registerForCompetition(
                competitionId,
                request.getUserId()
        );

        return ResponseEntity.ok(participation);
    }

}