package edu.metu.ceng453.bitBattle.level;

import javafx.animation.AnimationTimer;

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
        initializeLevel(anchorOne, endLevel, gameOver, homeButton);

        scoreLabel.setText(Integer.toString(this.getGameScore()));
        setGameScore(0);
        System.out.println("Aha bir daha geldiee");
        animateAliens();
        //Handle key events
        gridOne.setOnKeyPressed((KeyEvent event)->{
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            }
            else if (event.getCode() == KeyCode.LEFT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
            }
            if (event.getCode() == KeyCode.SPACE){
                    fire(anchorOne, "bullet");
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
                isFinished = game(anchorOne, healthCount, gameOver, homeButton, scoreLabel, endLevel);
            }
        }.start();
    }

    //When user presses N second level will be opened
    private void secondLevel(KeyEvent event) throws IOException {
        Parent levelTwo = FXMLLoader.load(getClass().getResource("../design/levelTwo.fxml"));
        goNextLevel(event, levelTwo);
    }

    //When user presses go to home button Home page will be opened.
    public void homeButtonPushed(ActionEvent event) throws IOException{
        goHomePage(event);
    }

}
