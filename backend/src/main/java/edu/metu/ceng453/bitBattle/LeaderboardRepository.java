package edu.metu.ceng453.bitBattle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

interface LeaderboardRepository extends JpaRepository<Leaderboard, Integer> {
    Optional<List<Leaderboard>> findAllByGameTimeAfterOrderByScoreDesc (Date afterDate);
}