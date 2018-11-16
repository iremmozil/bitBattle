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

    @PutMapping("/leaderboard/{gameid}")
    Leaderboard replaceLeaderboard(@RequestBody Leaderboard newGame, @PathVariable Integer gameid) {

        return repository.findById(gameid)
                .map(leaderboard -> {
                    leaderboard.setplayerid(newGame.getplayerid());
                    leaderboard.setScore(newGame.getScore());
                    leaderboard.setGameTime(newGame.getGameTime());
                    return repository.save(leaderboard);
                })
                .orElseGet(() -> {
                    newGame.setGameid(gameid);
                    return repository.save(newGame);
                });
    }

    @DeleteMapping("/Leaderboard/{gameid}")
    void deleteGame(@PathVariable Integer gameid) {
        repository.deleteById(gameid);
    }
}
