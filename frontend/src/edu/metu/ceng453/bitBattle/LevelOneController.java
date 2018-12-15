package edu.metu.ceng453.bitBattle;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.awt.SunToolkit;

import javax.management.timer.Timer;
import java.io.IOException;
import java.sql.Time;


public class LevelOneController extends Controller{

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorOne;
    @FXML
    GridPane gridOne;

    @FXML
    Pane ailens;
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


    int Counter = 0;
    Object[] fires;


    public void initialize() throws InterruptedException {

        animateAilens();
        gridOne.setOnKeyPressed((KeyEvent event)->{
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
                fire();

            }
            event.consume();
        });



        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Counter++;
                if (Counter % 1000 == 0){
                    Circle b = new Circle(5.05);
                    b.setStroke(Color.BLACK);
                    b.setStrokeWidth(0.0);
                    b.setFill(Color.valueOf("99daff"));
                    anchorOne.getChildren().add(b);
                    alienRandomize(b);
                    double x = b.getCenterX();
                    double y = b.getCenterY();
                    PathTransition tt =
                            new PathTransition(Duration.seconds(4), new Line(x,y, x ,650),b);
                    tt.play();
                    }
                    checkBounds();

            }

        }.start();


    }
    public void alienShot(){


    }

    private void checkBounds() {
        boolean collisionDetected = false;
        for (Node bullet : anchorOne.getChildren()) {
            if(bullet != spaceship) {
                if (spaceship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    collisionDetected = true;
                }
            }
        }

        if (collisionDetected){
            System.out.println("Shot fired!");
        }
    }
    

    public void alienRandomize(Circle b){
        int n = (int)(Math.random() * 11 + 1);
        switch (n){
            case 1: b.setCenterX(ailen1.getLayoutX() + 52.0);
                b.setCenterY(ailen1.getLayoutY() + 140.0);
                break;
            case 2: b.setCenterX(ailen2.getLayoutX() + 52.0);
                b.setCenterY(ailen2.getLayoutY() + 140.0);
                break;
            case 3: b.setCenterX(ailen3.getLayoutX() + 52.0);
                b.setCenterY(ailen3.getLayoutY() + 140.0);
                break;
            case 4: b.setCenterX(ailen4.getLayoutX() + 52.0);
                b.setCenterY(ailen4.getLayoutY() + 140.0);
                break;
            case 5: b.setCenterX(ailen5.getLayoutX() + 52.0);
                b.setCenterY(ailen5.getLayoutY() + 140.0);
                break;
            case 6: b.setCenterX(ailen6.getLayoutX() + 52.0);
                b.setCenterY(ailen6.getLayoutY() + 140.0);
                break;
            case 7: b.setCenterX(ailen7.getLayoutX() + 52.0);
                b.setCenterY(ailen7.getLayoutY() + 140.0);
                break;
            case 8: b.setCenterX(ailen8.getLayoutX() + 52.0);
                b.setCenterY(ailen8.getLayoutY() + 140.0);
                break;
            case 9: b.setCenterX(ailen9.getLayoutX() + 52.0);
                b.setCenterY(ailen9.getLayoutY() + 140.0);
                break;
            case 10: b.setCenterX(ailen10.getLayoutX() + 52.0);
                b.setCenterY(ailen10.getLayoutY() + 140.0);
                break;
            case 11: b.setCenterX(ailen11.getLayoutX() + 52.0);
                b.setCenterY(ailen11.getLayoutY() + 140.0);
                break;
        }
    }


     public void animateAilens() {

         PathTransition tt =
                new PathTransition(Duration.seconds(5), new Line(),ailens);

         tt.setPath(new Rectangle(20,15));

        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();

    }


    public void fire(){
        Circle bullet = new Circle(5.05);
        bullet.setStroke(Color.BLACK);
        bullet.setStrokeWidth(0.0);
        bullet.setFill(Color.valueOf("997aff"));
        bullet.setCenterX(spaceship.getLayoutX() + 35.0);
        bullet.setCenterY(spaceship.getLayoutY() - 7.0);
        anchorOne.getChildren().add(bullet);

            double x = bullet.getCenterX();
            double y = bullet.getCenterY();
        PathTransition tt =
                new PathTransition(Duration.seconds(3), new Line(x,y, x ,-10),bullet);
        tt.play();

    }

}
