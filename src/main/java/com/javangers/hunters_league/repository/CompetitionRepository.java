package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.service.dto.LeaderboardProjectionInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    @Query("SELECT COUNT(c) > 0 FROM Competition c WHERE c.date BETWEEN ?1 AND ?2")
    boolean existsInWeek(LocalDateTime weekStart, LocalDateTime weekEnd);

    boolean existsByCode(String code);

    @Query(value = """
        SELECT
            u.username,
            c.code AS competition_code,
            c.id AS competition_id,
            p.score
        FROM
            "user" u
        JOIN
            participation p ON p.user_id = u.id
        LEFT JOIN
            competition c ON p.competition_id = c.id
        WHERE p.competition_id = :competitionId
        ORDER BY p.score DESC
        FETCH FIRST 3 ROWS ONLY
        """, nativeQuery = true)
    List<LeaderboardProjectionInterface> findTop3ByCompetitionIdNative(@Param("competitionId") UUID competitionId);


    @Query("""
            SELECT c
            FROM Competition c
            JOIN c.participations p
            WHERE p.user.id = :memberId
        """)
    Page<Competition> findCompetitionsByMember(@Param("memberId") UUID memberId, Pageable pageable);

    @Query("""
            SELECT c
            FROM Competition c
            WHERE c.date > :currentDate
            ORDER BY c.date ASC
        """)
    Page<Competition> findUpcomingCompetitions(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

}
