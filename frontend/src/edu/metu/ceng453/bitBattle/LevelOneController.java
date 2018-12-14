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
import javafx.scene.layout.Pane;
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
    Pane ailens;

    public void initialize(){
        animateLogo();
    }


     public void animateLogo() {

         PathTransition tt =
                new PathTransition(Duration.seconds(5), new Line(),ailens);

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
