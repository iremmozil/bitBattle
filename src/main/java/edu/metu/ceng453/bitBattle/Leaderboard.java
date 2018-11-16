package edu.metu.ceng453.bitBattle;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Leaderboard {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "player_id")
    private Integer playerId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "game_time")
    private Date gameTime;


    Leaderboard(Integer gameID, Integer playerid, Integer score, Date gameTime) {
        this.id = gameID;
        this.playerId = playerid;
        this.score = score;
        this.gameTime = gameTime;
    }

    public Integer getGameid() {
        return id;
    }

    public void setId(Integer gameID) {
        this.id = gameID;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerid) {
        this.playerId = playerid;
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
