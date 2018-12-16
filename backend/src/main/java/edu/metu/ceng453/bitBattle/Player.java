package edu.metu.ceng453.bitBattle;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
public class Player {
    public Player() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "playername")
    private String playername;

    @Column(name = "password")
    private String password;

    @Column(name = "highscore")
    private Integer highScore;

    Player(String playerName, String playerPassword, Integer highScore) {
        this.playername = playerName;
        this.password = playerPassword;
        this.highScore = highScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer playerId) {
        this.id = playerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String playerPassword) {
        this.password = playerPassword;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }
}
