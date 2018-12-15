package edu.metu.ceng453.bitBattle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;


public class Controller extends Main{
    @FXML
    Button sbutton;



    public void sbuttonPushed(ActionEvent event) throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("./home.fxml"));
        Scene sceneHome = new Scene(home);
        sceneHome.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneHome);
        window.show();


    }


}
