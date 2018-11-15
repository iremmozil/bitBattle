package edu.metu.ceng453.bitBattle;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name = "playerID")
    private Integer playerID;

    @Column (name = "username")
    private String playerName;

    @Column (name = "password")
    private String playerPassword;

    @Column (name = "highScore")
    private Integer highScore;

    Player(String playerName, Integer playerID, String playerPassword, Integer highScore) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerPassword = playerPassword;
        this.highScore = highScore;
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

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }
}
