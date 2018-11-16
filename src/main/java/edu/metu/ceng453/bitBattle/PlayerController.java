package edu.metu.ceng453.bitBattle;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
        player.setPassword(myHash(player.getPassword()));// after this function is done set password = my_hash(password)
        return repository.save(player);
    }

    //Log_in a player
    @PostMapping("/log_in")
    Player logPlayer(@RequestBody Player player){
        Player p;
        p = repository.findByPlayername(player.getPlayername()).orElseThrow(() -> new EntityNotFoundException("This player does not exist, please try another name"));
        if(p.getPassword().equals(myHash(player.getPassword()))) {
                    return p;
                }
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
    String myHash(String password){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}