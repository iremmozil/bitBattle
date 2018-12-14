package edu.metu.ceng453.bitBattle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller extends Main{
    @FXML
    Button sbutton;


    public void sbuttonPushed(ActionEvent event) throws IOException{
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);
        sceneOne.getRoot().requestFocus();
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneOne);
        window.show();
    }


}
