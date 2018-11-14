package edu.metu.ceng453.bitBattle;

import org.springframework.data.jpa.repository.JpaRepository;

interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByID(Integer playerID);
}