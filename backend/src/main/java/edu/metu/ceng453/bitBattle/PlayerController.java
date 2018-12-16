package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController    // This means that this class is a Controller
public class PlayerController {

    private PlayerRepository playerRepository;

    PlayerController(PlayerRepository repository) {
        this.playerRepository = repository;
    }

    @GetMapping("/players")
    List<Player> all() {
        return playerRepository.findAll();
    }


    @GetMapping("/player/{playername}")
    Player getPlayerByName(@PathVariable String playername) {
        return playerRepository.findByPlayername(playername).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
    }

    @GetMapping("/player/id/{id}")
    String getPlayernameById(@PathVariable Integer id) {
        Player tempPlayer =  playerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
        return tempPlayer.getPlayername();
    }

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return playerRepository.save(newPlayer);
    }

    @PutMapping("/player/{id}")
    Player replaceHighScore(@RequestBody Integer highScore, @PathVariable Integer id) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setHighScore(highScore);
                    return playerRepository.save(player);
                }) .orElseThrow(() -> new RuntimeException("No player exists!"));
    }

    @DeleteMapping("/player/{id}")
    void deletePlayer(@PathVariable Integer id) {
        playerRepository.deleteById(id);
    }
}