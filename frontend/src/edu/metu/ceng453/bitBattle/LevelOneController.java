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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;


public class LevelOneController extends LevelController {

    @FXML ImageView spaceship;

    @FXML AnchorPane anchorOne;
    @FXML GridPane gridOne;

    @FXML Label scoreLabel;
    @FXML Label endLevel;
    @FXML Label healthCount;
    @FXML Label gameOver;
    @FXML Button homeButton;

    private Boolean isFinished = false;

    public void initialize() {
        aliensToArray(anchorOne);

        scoreLabel.setText(Integer.toString(getScore()));
        endLevel.setVisible(false);
        homeButton.setVisible(false);
        gameOver.setVisible(false);
        animateAliens();

        gridOne.setOnKeyPressed((KeyEvent event)->{
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            }
            else if (event.getCode() == KeyCode.LEFT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
            }
            else if (event.getCode() == KeyCode.SPACE){
                    fire(anchorOne, spaceship);
            }
            else if (event.getCode() == KeyCode.N){
                try {
                    if (isFinished){
                        secondLevel(event);
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
                isFinished = game(anchorOne, spaceship, healthCount, gameOver, homeButton, scoreLabel, endLevel);
            }
        }.start();
    }

    public void secondLevel(KeyEvent event) throws IOException {
        Parent levelTwo = FXMLLoader.load(getClass().getResource("design/levelTwo.fxml"));
        goNextLevel(event, levelTwo);
    }

    public void homeButtonPushed(ActionEvent event) throws IOException{
        goHomePage(event);
    }

}
