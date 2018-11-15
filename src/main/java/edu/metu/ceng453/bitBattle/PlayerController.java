package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController    // This means that this class is a Controller
public class PlayerController {

    private PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/Player/{playerID}")
    Player getPlayer(@PathVariable Integer playerID) {
       return repository.findById(playerID).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
    }

    @PostMapping("/Player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    @PutMapping("/Player/{playerID}")
    Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Integer playerID) {
        return repository.findById(playerID)
                .map(player -> {
                    player.setHighScore(newPlayer.getHighScore());
                    player.setName(newPlayer.getName());
                    player.setPassword(newPlayer.getPassword());
                    return repository.save(player);
                })
                .orElseGet(() -> {
                    newPlayer.setID(playerID);
                    return repository.save(newPlayer);
                });
    }

    @DeleteMapping("/Player/{playerID}")
    void deletePlayer(@PathVariable Integer playerID) {
        repository.deleteById(playerID);
    }
}