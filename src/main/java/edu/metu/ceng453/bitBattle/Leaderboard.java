package edu.metu.ceng453.bitBattle;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer gameID;

    @OneToMany
    @JoinColumn (name = "playerID")
    private Integer playerID;

    private Integer score;
    private Date gameTime;


    Leaderboard(Integer gameID, Integer playerID, Integer score, Date gameTime) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.score = score;
        this.gameTime = gameTime;
    }

    public Integer getID() {
        return gameID;
    }

    public void setID(Integer gameID) {
        this.gameID = gameID;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime() {
        this.gameTime = gameTime;
    }
}
