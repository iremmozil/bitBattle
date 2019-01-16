package edu.metu.ceng453.bitBattle;

public class Player {

        public Player() {
        }

        private Integer id;

        private String playername;

        private String password;

        private Integer highScore;

        Player(String playerName, String playerPassword, Integer highScore) {
            this.playername = playerName;
            this.password = playerPassword;
            this.highScore = highScore;
        }

        public Integer getId() {
            return id;
        }


        public Integer getHighScore() {
            return highScore;
        }

        public void setHighScore(Integer highScore) {
            this.highScore = highScore;
        }
}
