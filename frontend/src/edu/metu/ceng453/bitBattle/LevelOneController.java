package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class LevelOneController extends Controller{

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorOne;

    @FXML
    Button newButton;

    @FXML ImageView ailen1;
    @FXML ImageView ailen2;
    @FXML ImageView ailen3;
    @FXML ImageView ailen4;
    @FXML ImageView ailen5;
    @FXML ImageView ailen6;
    @FXML ImageView ailen7;
    @FXML ImageView ailen8;
    @FXML ImageView ailen9;
    @FXML ImageView ailen10;
    @FXML ImageView ailen11;

    Group alienGroup = new Group();



    public void newButtonPushed(ActionEvent event) throws IOException {
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);
        sceneOne.getRoot().requestFocus();
        Node node = ((Node)event.getSource());
        Stage window = (Stage) (node.getScene().getWindow());
        window.setScene(sceneOne);
        window.show();
    }


     public void animateLogo() {
         alienGroup.getChildren().add(ailen1);
         alienGroup.getChildren().add(ailen2);
         alienGroup.getChildren().add(ailen3);
         alienGroup.getChildren().add(ailen4);
         alienGroup.getChildren().add(ailen5);
         alienGroup.getChildren().add(ailen6);
         alienGroup.getChildren().add(ailen7);
         alienGroup.getChildren().add(ailen8);
         alienGroup.getChildren().add(ailen9);
         alienGroup.getChildren().add(ailen10);
         alienGroup.getChildren().add(ailen11);

         PathTransition tt =
                new PathTransition(Duration.seconds(5), new Line(),alienGroup);

         tt.setPath(new Rectangle(20,15));

        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();

    }

    @FXML
    private void setKeyListener(KeyEvent event){
        animateLogo();

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
