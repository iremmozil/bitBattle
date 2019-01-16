package edu.metu.ceng453.bitBattle;

import java.util.Date;

public class Leaderboard {

    private Integer id;

    private Integer playerId;

    private Integer score;

    private Date gameTime;

    Leaderboard() {
    }

    Leaderboard(Integer playerid, Integer score, Date gameTime) {
        this.id = 0;
        this.playerId = playerid;
        this.score = score;
        this.gameTime = gameTime;
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

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }
}
