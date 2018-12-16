package edu.metu.ceng453.bitBattle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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


public class Main extends Application {
    private static Player player;

    public static Player getCurrentPlayer() {
        return Main.player;
    }

    public static void setCurrentPlayer(Player newPlayer) {
        Main.player = newPlayer;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./signin.fxml"));

        primaryStage.setTitle("BitBattle");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
