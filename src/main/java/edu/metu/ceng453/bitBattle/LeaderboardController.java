package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LeaderboardController {

    private final LeaderboardRepository repository;

    LeaderboardController(LeaderboardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/Leaderboard/{afterDate}")
    List<Leaderboard> getLeaderboard(@PathVariable Date afterDate) {
        return repository.findByGameTimeAfter(afterDate);
    }

    @PostMapping("/Leaderboard")
    Leaderboard newGame(@RequestBody Leaderboard newLeaderboard) {
        return repository.save(newLeaderboard);
    }

    @PutMapping("/Leaderboard/{gameID}")
    Leaderboard replaceLeaderboard(@RequestBody Leaderboard newGame, @PathVariable Integer gameID) {

        return repository.findById(gameID)
                .map(leaderboard -> {
                    leaderboard.setPlayerID(newGame.getPlayerID());
                    leaderboard.setScore(newGame.getScore());
                    leaderboard.setGameTime(newGame.getGameTime());
                    return repository.save(leaderboard);
                })
                .orElseGet(() -> {
                    newGame.setID(gameID);
                    return repository.save(newGame);
                });
    }

    @DeleteMapping("/Leaderboard/{gameID}")
    void deleteGame(@PathVariable Integer gameID) {
        repository.deleteById(gameID);
    }
}
