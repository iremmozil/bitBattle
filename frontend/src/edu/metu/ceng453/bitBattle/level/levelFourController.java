package edu.metu.ceng453.bitBattle.level;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class levelFourController extends LevelController{

    @FXML ImageView spaceship;
    @FXML ImageView opponent;

    @FXML AnchorPane anchorFour;
    @FXML GridPane gridFour;
    @FXML Label healthCount;
    @FXML Label scoreLabel;
    @FXML Label endLevel;
    @FXML Label gameOver;

    @FXML Button homeButton;

    private boolean isFinished = false;
    private int level = 4;

    public void initialize() {
        initializeLevel(anchorFour, endLevel, gameOver, homeButton);
        animateAliens();
        //Handle key events
        gridFour.setOnKeyPressed((KeyEvent event)-> {
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
                //servera mesaj gönderilecek
            } else if (event.getCode() == KeyCode.LEFT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
                //servera mesaj gönderilecek
            } else if (event.getCode() == KeyCode.SPACE) {
                fire(anchorFour, spaceship);
                //servera mesaj gönderilecek
            }
            event.consume();
        });
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                isFinished = game(anchorFour, healthCount, gameOver, homeButton, scoreLabel, endLevel);
                if(isFinished){
                    homeButton.setVisible(true);
                }
            }
        }.start();
    }

    //When user presses go to home button Home page will be opened.
    public void homeButtonPushed(ActionEvent event) throws IOException {
        updateDatabase();
        goHomePage(event);

    }
}
