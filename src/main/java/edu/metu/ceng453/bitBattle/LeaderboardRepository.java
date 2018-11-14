package edu.metu.ceng453.bitBattle;

import org.springframework.data.jpa.repository.JpaRepository;

interface LeaderboardRepository extends JpaRepository<Player, Integer> {

}