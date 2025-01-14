package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.service.ParticipationService;
import com.javangers.hunters_league.service.dto.CompetitionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import com.javangers.hunters_league.service.dto.ParticipationDTO;
import com.javangers.hunters_league.web.vm.CompetitionRequestVM;
import com.javangers.hunters_league.web.vm.ParticipationRequestVM;
import com.javangers.hunters_league.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<Page<CompetitionDTO>> getAllCompetitions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompetitionDTO> competitions = competitionService.getAllCompetitions(pageable);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{competitionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Competition> getCompetition(
        @PathVariable UUID competitionId) {
        Competition competition = competitionService.getCompetition(competitionId);
        return ResponseEntity.ok(competition);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<CompetitionDTO>> getCompetitionsByMember(
        @PathVariable UUID memberId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompetitionDTO> competitions = competitionService.getCompetitionsByMember(memberId, pageable);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<CompetitionDTO>> getUpcomingCompetitions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompetitionDTO> competitions = competitionService.getUpcomingCompetitions(pageable);
        return ResponseEntity.ok(competitions);
    }

    @PostMapping("/{competitionId}/register")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<ParticipationDTO> registerForCompetition(
        @PathVariable UUID competitionId,
        @Valid @RequestBody ParticipationRequestVM request) {

        ParticipationDTO participation = participationService.registerForCompetition(
            competitionId,
            request.getUserId()
        );

        return ResponseEntity.ok(participation);
    }

    @GetMapping("/{competitionId}/leaderboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<LeaderboardPositionDTO>> getCompetitionLeaderboard(
        @PathVariable UUID competitionId) {
        List<LeaderboardPositionDTO> leaderboard = competitionService.getCompetitionLeaderboard(competitionId);
        return ResponseEntity.ok(leaderboard);
    }

}
