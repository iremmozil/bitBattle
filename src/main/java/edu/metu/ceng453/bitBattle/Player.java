package edu.metu.ceng453.bitBattle;

import javax.persistence.*;

@Entity
public class Player {
    public Player() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "playerid")
    private Integer playerid;

    @Column (name = "username")
    private String playerName;

    @Column (name = "password")
    private String playerPassword;

    @Column (name = "highscore")
    private Integer highScore;

    Player(String playerName, Integer playerId, String playerPassword, Integer highScore) {
        this.playerid = playerId;
        this.playerName = playerName;
        this.playerPassword = playerPassword;
        this.highScore = highScore;
    }


    public Integer getID() {
        return playerid;
    }

    public void setID(Integer playerId) {
        this.playerid = playerId;
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
