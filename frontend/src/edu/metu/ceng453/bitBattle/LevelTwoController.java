package edu.metu.ceng453.bitBattle;

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

public class LevelTwoController extends LevelController{
    @FXML ImageView spaceship;

    @FXML AnchorPane anchorTwo;
    @FXML GridPane gridTwo;

    @FXML Label healthCount;
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
        //Handle key events
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
                isFinished = game(anchorTwo, spaceship, healthCount, gameOver, homeButton, scoreLabel, levelend);
            }
        }.start();
    }

    //When user presses N third level will be opened
    private void thirdLevel(KeyEvent event) throws IOException {
            Parent levelThree = FXMLLoader.load(getClass().getResource("design/levelThree.fxml"));
            goNextLevel(event, levelThree);
        }

    //When user presses go to home button Home page will be opened.
    public void homeButtonPushed(ActionEvent event) throws IOException{
        goHomePage(event);

    }
}
