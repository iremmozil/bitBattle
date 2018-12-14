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

    @GetMapping("/player/{id}")
    Player getPlayer(@PathVariable Integer id) {
       return playerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
    }

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return playerRepository.save(newPlayer);
    }

    @PutMapping("/player/{id}")
    Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Integer id) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setHighScore(newPlayer.getHighScore());
                    player.setPlayername(newPlayer.getPlayername());
                    player.setPassword(newPlayer.getPassword());
                    return playerRepository.save(player);
                })
                .orElseGet(() -> {
                    newPlayer.setId(id);
                    return playerRepository.save(newPlayer);
                });
    }

    @DeleteMapping("/player/{id}")
    void deletePlayer(@PathVariable Integer id) {
        playerRepository.deleteById(id);
    }
}