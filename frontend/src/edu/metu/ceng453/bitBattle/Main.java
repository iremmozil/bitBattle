package edu.metu.ceng453.bitBattle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {
    private static Player player;
    private static Leaderboard game;
    private static int score = 0;
    public static String protocol = "http://";
    public static String host = "localhost:";
    public static String  port = "8080/";
    public static String playerPath = "player/";    // "http://localhost:8080/player/"
    public static String leaderboardPath = "leaderboard/";


    public static int fromMaingetScore(){return score;};
    public static Player getCurrentPlayer() {
        return Main.player;
    }

    static void setCurrentPlayer(Player newPlayer) {
        Main.player = newPlayer;
    }

    public static Leaderboard getCurrentGame() {
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

    public void setScene(ActionEvent event, Parent next){
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
