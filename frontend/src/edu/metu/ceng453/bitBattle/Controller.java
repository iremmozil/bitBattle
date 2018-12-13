package edu.metu.ceng453.bitBattle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller extends Main{
    @FXML
    Button sbutton;


    public void sbuttonPushed(ActionEvent event) throws IOException{
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneOne);
        window.show();
    }
}
