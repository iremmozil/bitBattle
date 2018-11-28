package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;

@RestController
public class LeaderboardController {

    private final LeaderboardRepository repository;

    LeaderboardController(LeaderboardRepository repository) {
        this.repository = repository;
    }

    // This method returns all leaderboard.
    @GetMapping("/leaderboard")
    List<Leaderboard> getLeaderboard() {
        return repository.findAllByGameTimeAfterOrderByScoreDesc(Date.valueOf("1900-01-01")).orElseThrow(() -> new EntityNotFoundException("Leaderboard does not exist!"));
    }

    // This method returns leaderboard of last X days.
    @GetMapping("/leaderboard/last_week")
    List<Leaderboard> getLeaderboardLastWeek(){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date tempDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        return repository.findAllByGameTimeAfterOrderByScoreDesc(tempDate).orElseThrow(() -> new EntityNotFoundException("Leaderboard does not exist!"));
    }

    //this method adds gameTime, playerId and score to the leaderboard with a unique id.
    @PostMapping("/leaderboard")
    Leaderboard newLeaderboard(@RequestBody Leaderboard newLeaderboard) {
        return repository.save(newLeaderboard);
    }

    @DeleteMapping("/delete_leaderboard/{id}")
    void deleteGame(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/delete_leaderboard")
    void deleteAll() {
        repository.deleteAll();
    }
}
