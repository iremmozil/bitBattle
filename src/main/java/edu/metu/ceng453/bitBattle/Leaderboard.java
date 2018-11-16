package edu.metu.ceng453.bitBattle;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "gameid")
    private Integer gameid;

    @JoinColumn(name = "playerid")
    private Integer playerid;

    @Column(name = "score")
    private Integer score;

    @Column(name = "gametime")
    private Date gameTime;


    Leaderboard(Integer gameID, Integer playerid, Integer score, Date gameTime) {
        this.gameid = gameID;
        this.playerid = playerid;
        this.score = score;
        this.gameTime = gameTime;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameID) {
        this.gameid = gameID;
    }

    public Integer getplayerid() {
        return playerid;
    }

    public void setplayerid(Integer playerid) {
        this.playerid = playerid;
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

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }
}
