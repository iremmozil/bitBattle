package edu.metu.ceng453.bitBattle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;




public class levelThreeController extends LevelController {

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorThree;
    @FXML
    GridPane gridThree;

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
    @FXML ImageView alien11;
    @FXML ImageView circle1;
    @FXML ImageView circle2;
    @FXML ImageView circle3;
    @FXML ImageView circle4;
    @FXML ImageView parallelogram1;
    @FXML ImageView parallelogram2;

    @FXML
    Label healthCount;
    @FXML Label scoreLabel;
    @FXML Label endLevel;
    @FXML Label gameOver;
    @FXML Button homeButton;

    //private int score = Main.getCurrentGame().getScore();
    private boolean isFinished = false;



    public void initialize() {

        aliensToArray(anchorThree);

        endLevel.setVisible(false);
        gameOver.setVisible(false);
        homeButton.setVisible(false);

        animateAliens();
        gridThree.setOnKeyPressed((KeyEvent event)-> {
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            } else if (event.getCode() == KeyCode.LEFT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
            } else if (event.getCode() == KeyCode.SPACE) {
                fire(anchorThree,spaceship);
            }
            event.consume();
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                isFinished = game(anchorThree, spaceship, healthCount, gameOver, homeButton, scoreLabel, endLevel);
                if(isFinished){
                    homeButton.setVisible(true);
                }
            }
        }.start();
    }

    public void homeButtonPushed(ActionEvent event) throws IOException{
        goHomePage(event);

    }


}
