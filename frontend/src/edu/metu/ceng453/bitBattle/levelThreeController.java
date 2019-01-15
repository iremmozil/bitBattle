package edu.metu.ceng453.bitBattle;

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




public class levelThreeController extends LevelController {

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorThree;
    @FXML
    GridPane gridThree;

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
        //Handle key events
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

    //When user presses go to home button Home page will be opened.
    public void homeButtonPushed(ActionEvent event) throws IOException{
        updateDatabase();
        goHomePage(event);

    }
}
