package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;


import java.io.IOException;
import java.util.Date;

public class HomeController extends Main {

    // Bind variables to related FXML entities
    @FXML
    Button newButton;

    // "New Game" button push handler
    public void newButtonPushed(ActionEvent event) throws IOException {
        Leaderboard currentGame = new Leaderboard(Main.getCurrentPlayer().getId(),0,new Date());

        Main.setCurrentGame(currentGame);
        Parent levelOne = FXMLLoader.load(getClass().getResource("./design/levelOne.fxml"));
        setScene(event, levelOne);
    }

    // "Leaderboard" button push handler
    public void lbuttonPushed(ActionEvent event) throws IOException {
        Parent lBoard = FXMLLoader.load(getClass().getResource("design/leaderboard.fxml"));
        setScene(event, lBoard);
    }

    // "Log Out" button push handler
    public void logoutbuttonPushed(ActionEvent event) throws IOException {
        Parent signIn = FXMLLoader.load(getClass().getResource("design/signin.fxml"));
        setScene(event, signIn);
    }
}
