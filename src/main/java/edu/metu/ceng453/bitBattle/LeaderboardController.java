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

    @GetMapping("/leaderboard/{afterDate}")
    List<Leaderboard> getLeaderboard(@PathVariable Date afterDate) {
        return repository.findByGameTimeAfter(afterDate);
    }

    @PostMapping("/leaderboard")
    Leaderboard newGame(@RequestBody Leaderboard newLeaderboard) {
        return repository.save(newLeaderboard);
    }

    @PutMapping("/leaderboard/{id}")
    Leaderboard replaceLeaderboard(@RequestBody Leaderboard newGame, @PathVariable Integer id) {

        return repository.findById(id)
                .map(leaderboard -> {
                    leaderboard.setPlayerId(newGame.getPlayerId());
                    leaderboard.setScore(newGame.getScore());
                    leaderboard.setGameTime(newGame.getGameTime());
                    return repository.save(leaderboard);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    return repository.save(newGame);
                });
    }

    @DeleteMapping("/Leaderboard/{id}")
    void deleteGame(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
