package edu.metu.ceng453.bitBattle.level;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LevelFourController extends LevelController{

    @FXML ImageView spaceship;
    @FXML ImageView opponent;

    @FXML AnchorPane anchorFour;

    private boolean isFinished = false;

    public void initialize() {
        aliensToArray(anchorFour);

        animateAliens();
        //Handle key events
        anchorFour.setOnKeyPressed((KeyEvent event)-> {
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            } else if (event.getCode() == KeyCode.LEFT) {
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
            } else if (event.getCode() == KeyCode.SPACE) {
                fire(anchorFour, spaceship);
            }
            else if (event.getCode() == KeyCode.N){

            }
            event.consume();
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        }.start();
    }


}
