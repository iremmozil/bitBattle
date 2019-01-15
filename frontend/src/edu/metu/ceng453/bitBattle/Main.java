package edu.metu.ceng453.bitBattle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

class Player {
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


    Integer getHighScore() {
        return highScore;
    }

    void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }
}

class Leaderboard {

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

public class Main extends Application {
    private static Player player;
    private static Leaderboard game;
    private static int score = 0;
    String protocol = "http://";
    String host = "localhost:";
    String  port = "8080/";
    String playerPath = "player/";    // "http://localhost:8080/player/"
    String leaderboardPath = "leaderboard/";


    public static int fromMaingetScore(){return score;};
    static Player getCurrentPlayer() {
        return Main.player;
    }

    static void setCurrentPlayer(Player newPlayer) {
        Main.player = newPlayer;
    }

    static Leaderboard getCurrentGame() {
        return Main.game;
    }

    static void setCurrentGame(Leaderboard newGame) {
        Main.game = newGame;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("design/signin.fxml"));

        primaryStage.setTitle("BitBattle");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    void setScene(ActionEvent event, Parent next){
        Scene scene = new Scene(next);
        scene.getRoot().requestFocus();
        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
