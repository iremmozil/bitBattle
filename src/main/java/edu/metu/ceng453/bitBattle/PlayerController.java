package edu.metu.ceng453.bitBattle;

import java.util.function.Function;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController    // This means that this class is a Controller
public class PlayerController {

    private PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/Player/{playerID}")
    Optional<Player> getPlayer(@PathVariable Integer playerID) {
       return repository.findById(Long.valueOf(playerID));
    }

    @PostMapping("/Player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }


    @PutMapping("/Player/{playerID}")
    Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Integer playerID) {


        return repository.findById(Long.valueOf(playerID))
                .map(Player -> {
                    Player.setName(newPlayer.getName());
                    Player.setPassword(newPlayer.getPassword());
                    return repository.save(Player);
                })
                .orElseGet(() -> {
                    // Throw exception
                });

    }

    @DeleteMapping("/Player/{id}")
    void deletePlayer(@PathVariable Long id) {
        repository.deleteById(id);
    }


    // Several get functions can be implemented with a specified path.
    // GET all, GET one by ID etc.
}