package edu.metu.ceng453.bitBattle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

interface LeaderboardRepository extends JpaRepository<Leaderboard, Integer> {
    List<Leaderboard> findByGameTimeAfter(Date afterDate);
}