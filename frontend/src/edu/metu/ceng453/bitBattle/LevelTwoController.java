package edu.metu.ceng453.bitBattle;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class LevelTwoController extends LevelController{
    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorTwo;
    @FXML
    GridPane gridTwo;

    @FXML ImageView alien1;
    @FXML ImageView alien2;
    @FXML ImageView alien3;
    @FXML ImageView alien4;
    @FXML ImageView alien5;
    @FXML ImageView alien6;
    @FXML ImageView alien7;
    @FXML ImageView alien8;
    @FXML ImageView alien9;
    @FXML ImageView alien10;
    @FXML ImageView circleAlien1;
    @FXML ImageView circleAlien2;
    @FXML ImageView circleAlien3;
    @FXML ImageView circleAlien4;

    @FXML
    Label healthCount;
    @FXML Label scoreLabel;
    @FXML Label levelend;
    @FXML Label gameOver;
    @FXML Button homeButton;
    
    private boolean isFinished = false;


    public void initialize() {

        aliensToArray(anchorTwo);

        levelend.setVisible(false);
        gameOver.setVisible(false);
        homeButton.setVisible(false);
        isFinished = false;

        animateAliens();
        gridTwo.setOnKeyPressed((KeyEvent event)-> {
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            } else if (event.getCode() == KeyCode.LEFT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
            } else if (event.getCode() == KeyCode.SPACE) {
                fire(anchorTwo, spaceship);
            }
            else if (event.getCode() == KeyCode.N){
                try {
                    if (isFinished){
                        thirdLevel(event);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            event.consume();
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                game(anchorTwo, spaceship, healthCount, gameOver, homeButton, scoreLabel, levelend);
            }
        }.start();
    }


    public void thirdLevel(KeyEvent event) throws IOException {
            Parent levelThree = FXMLLoader.load(getClass().getResource("design/levelThree.fxml"));
            goNextLevel(event, levelThree);
        }

    public void homeButtonPushed(ActionEvent event) throws IOException{
        goHomePage(event);

    }
}
