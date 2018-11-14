package edu.metu.ceng453.bitBattle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer playerID;
    private String playerName;
    private String playerPassword;

    Player(String playerName, Integer playerID, String playerPassword) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerPassword = playerPassword;
    }

    public Integer getID() {
        return playerID;
    }

    public void setID(Integer playerID) {
        this.playerID = playerID;
    }

    public String getPassword() {
        return playerPassword;
    }

    public void setPassword(String playerPassword) {
        this.playerPassword = playerPassword;
    }

    public String getName() {
        return playerName;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }
}
