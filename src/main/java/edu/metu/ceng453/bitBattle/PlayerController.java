package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController    // This means that this class is a Controller
public class PlayerController {

    private final PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    //Gets all players
    @GetMapping("/players")
    List<Player> all() {
        return repository.findAll();
    }

    //Get a player
    @GetMapping("/player/{playername}")
    Player getPlayer(@PathVariable String playername) {
       return repository.findByPlayername(playername).orElseThrow(()->new EntityNotFoundException("This player does not exist!"));
    }

    //Register a player
    @PostMapping("/register")
    Player newPlayer(@RequestBody Player player) {
        player.setPassword(my_hash(player.getPassword()));// after this function is done set password = my_hash(password)
        return repository.save(player);
    }

    //Log_in a player
    @PostMapping("/log_in")
    Player logPlayer(@RequestBody Player player){
        Player p;
        p = repository.findByPlayername(player.getPlayername()).orElseThrow(() -> new EntityNotFoundException("This player does not exist, please try another name"));
        if(p.getPassword().equals(player.getPassword())) {
                    return player;
                } //Is password correct? If it is go! if not send exception!
                else
                    throw new EntityNotFoundException("Password you entered is not correct, please try again");
    }

    //implement log_out player function!!!


    //Change password of a player
    // In this function also check the old password
    @PutMapping("/change_password")
    Player replacePlayerPassword(@RequestBody Player player) {
        //Player p = repository.findByPlayername(player.getPlayername()).orElseThrow(() -> new EntityNotFoundException("Your playername is not valid, you cannot change password!"));
        player.setPassword(player.getPassword());
            return repository.save(player);
    }

    //Deletes selected player from table
    @DeleteMapping("/delete_player/{id}")
    void deletePlayer(@PathVariable Integer id) {
        Player p = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("The player is not exist."));
        repository.deleteById(id);
    }

    //password hash function
    String my_hash(String password){
        return "iamasuperuser";
    }
}