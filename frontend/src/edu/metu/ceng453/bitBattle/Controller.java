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

    @FXML
    ImageView spaceship;

    @FXML
    Circle bullet;

    @FXML
    AnchorPane anchorOne;

    public void sbuttonPushed(ActionEvent event) throws IOException{
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);
        sceneOne.getRoot().requestFocus();
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneOne);
        window.show();
    }

    @FXML
    private void setKeyListener(KeyEvent event){
        if (event.getCode() == KeyCode.RIGHT){
            double x = spaceship.getLayoutX();
            double y = spaceship.getLayoutY();
            if (x < 630){
                x = x + 4;
            }
            spaceship.relocate(x,y);
        }
        else if (event.getCode() == KeyCode.LEFT){
            double x = spaceship.getLayoutX();
            double y = spaceship.getLayoutY();
            if( x > -20){
                x = x - 4;
            }
            spaceship.relocate(x,y);

        }
        else if (event.getCode() == KeyCode.SPACE){
            Circle b = new Circle(5.05);
            b.setStroke(Color.BLACK);
            b.setStrokeWidth(0.0);
            b.setFill(Color.valueOf("99daff"));
            b.setCenterX(spaceship.getLayoutX() + 35.0);
            b.setCenterY(spaceship.getLayoutY());
            anchorOne.getChildren().add(b);
            /*
            while(b.getCenterY() > 0){
                double x = b.getCenterX();
                double y = b.getCenterY();
                y = y - 6;
                b.relocate(x,y);
            }*/

        }
        event.consume();
    }

}
