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

    private final PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/players")
    List<Player> all() {
        return repository.findAll();
    }

    @GetMapping("/player/{playerid}")
    Player getPlayer(@PathVariable Integer playerid) {
       return repository.findById(playerid).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
    }

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    @PutMapping("/player/{playerid}")
    Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Integer playerid) {
        return repository.findById(playerid)
                .map(player -> {
                    player.setHighScore(newPlayer.getHighScore());
                    player.setName(newPlayer.getName());
                    player.setPassword(newPlayer.getPassword());
                    return repository.save(player);
                })
                .orElseGet(() -> {
                    newPlayer.setID(playerid);
                    return repository.save(newPlayer);
                });
    }

    @DeleteMapping("/player/{playerid}")
    void deletePlayer(@PathVariable Integer playerid) {
        repository.deleteById(playerid);
    }
}