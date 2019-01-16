package edu.metu.ceng453.bitBattle.level;

import edu.metu.ceng453.bitBattle.alien.Alien;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

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
    private int Counter = 0;

    private boolean isFinished = false;
    private int alienShootTime = 1100;
    private boolean isShot = false;

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
                makeAlienShoot(anchorFour);
                checkisSpaceShipDead(anchorFour, healthCount, gameOver, homeButton, scoreLabel);
                isFinished = checkLevelFinished(endLevel);
                if(isFinished){
                    homeButton.setVisible(true);
                }
            }
        }.start();
    }

    @Override
    void makeAlienShoot(AnchorPane anchor){
        Counter++;
        if (Counter % alienShootTime == 0){ //aliens shoot when the counter counter is 1100*n
            alienShoot(5,anchor);
        }

    }
    //if spaceship's health is zero than it is dead, so we should show that game is over and send info to server
    //id spaceship is not dead update score and always check for any alien is shot or not.
    @Override
    void checkisSpaceShipDead(AnchorPane anchor, Label healthCount, Label gameOver, Button homeButton, Label scoreLabel){
        if (isSpaceshipDown(anchor, healthCount)){ //Check if the spaceship's health 0 or not
            gameOver.setVisible(true);
            homeButton.setVisible(true);
            //sela oku
        } else {
            scoreLabel.setText(Integer.toString(getGameScore()));
            alienShot(anchor);              //Make sure always check is any alien been shot or not
        }
    }

    //When spaceship shoot find that bullet from anchorPane's children and check if it intersect with any alien
    @Override
    void alienShot(AnchorPane anchor){
        for(Node o: anchor.getChildren()){
            if (o.getId() == "bullet" || o.getId() == "obullet"){                     // it works somehow this way not with ".equals()"
                if (isAlienShot(o, aliens, anchor)) break;
            }
        }
    }


    // if bullet intersects one of the aliens return true, add score 5 points and
    // if the alien's health is zero remove alien from aliens arrayList, else return false
    @Override
    boolean isAlienShot(Node o, ArrayList<Alien> aliens, AnchorPane anchor) {
        isShot =  false;
        for (Alien alien: aliens){
            // Check if this alien is shot down, if it is return true and remove bullet from anchorPane else return false
            // Check aliens's health if it is zero remove the alien from anchorPane
            if (alien.isShotDown(anchor,o)){
                isShot = true;
                if (alien.getHealth() == 0){
                    aliens.remove(alien);
                    if (o.getId() == "bullet"){
                        this.setGameScore(this.getGameScore() + alien.getPoint());
                    }else {
                        //servera yollaaaa sela fonksiyonu yollaaa
                    }
                }
                break;
            }
        }
        return isShot;
    }

    //When user presses go to home button Home page will be opened.
    public void homeButtonPushed(ActionEvent event) throws IOException {
        updateDatabase();
        goHomePage(event);

    }
}
