package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.IOException;

public class HomeController {

    // Bind variables to related FXML entities
    @FXML
    Button newButton;

    // "New Game" button push handler
    public void newButtonPushed(ActionEvent event) throws IOException {
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);
        sceneOne.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneOne);
        window.show();
    }

    // "Leaderboard" button push handler
    public void lbuttonPushed(ActionEvent event) throws IOException {
        Parent lBoard = FXMLLoader.load(getClass().getResource("./leaderboard.fxml"));
        Scene sceneLBoard = new Scene(lBoard);
        sceneLBoard.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneLBoard);
        window.show();
    }

    // "Log Out" button push handler
    public void logoutbuttonPushed(ActionEvent event) throws IOException {
        Parent signIn = FXMLLoader.load(getClass().getResource("./signin.fxml"));
        Scene sceneSignIn = new Scene(signIn);
        sceneSignIn.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneSignIn);
        window.show();
    }
}
