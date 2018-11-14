package edu.metu.ceng453.bitBattle;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    @OneToMany
    @JoinColumn (name = "playerID")
    private Integer playerID;

    private Integer score;
    private Date gameTime;



    Leaderboard(Integer playerID, Integer score, Date gameTime) {
        this.playerID = playerID;
        this.score = score;
        this.gameTime = gameTime;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public Integer getScore() {
        return score;
    }
}
