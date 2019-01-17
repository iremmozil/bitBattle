package edu.metu.ceng453.bitBattle.level;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.metu.ceng453.bitBattle.Event;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    int Counter = 0;
    private int spaceshipHitCounter = 0;
    private int opponentHitCounter = 0;
    private int loserScore = 270;
    private boolean start = false;


    private boolean opponentDead = false;
    private int noAlienShoot = -1;
    private boolean isFinished = false;

    private boolean isShot = false;
    static Client player;

    public void initialize() {
        String host = "192.168.2.105";
        player = new Client();
        player.start();
        Event.register(player);
        Event.MovePlayer movePlayer = new Event.MovePlayer();
        Event.FirePlayer firePlayer = new Event.FirePlayer();

        player.addListener(new Listener.ThreadedListener(new Listener() {
            public void connected (Connection connection) {

            }

            public void received (Connection connection, Object object) {
                if (object instanceof Event.AlienFired) {
                    noAlienShoot = ((Event.AlienFired) object).alienIndex;
                }

                if (object instanceof Event.StartandMoveAliens) {
                    animateAliens();
                    start = true;
                    spaceship.setLayoutX(((Event.StartandMoveAliens) object).spaceshipx);
                    opponent.setLayoutX(((Event.StartandMoveAliens) object).opponentx);
                    return;
                }

                if (object instanceof Event.MovePlayer) {
                    opponent.setLayoutX(((Event.MovePlayer) object).x);
                    return;
                }

                if (object instanceof Event.FirePlayer) {
                    fire(anchorFour,"obullet");
                    return;
                }

                if (object instanceof  Event.OpponentDead) {
                    opponentDead = true;
                    // TODO: You win page
                    return;
                }

                if (object instanceof Event.OpponentDisconnected) {
                    opponentDead = true;
                    // TODO: You win page
                    return;
                }
            }

            public void disconnected (Connection connection) {
                // TODO: Server closed message or sth like this
            }
        }));

        try {
            player.connect(5000, host, Event.port);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        initializeLevel(anchorFour, endLevel, gameOver, homeButton);
        /*animateAliens();*/
        //Handle key events
        gridFour.setOnKeyPressed((KeyEvent event)-> {
            if (start){
                double y = spaceship.getLayoutY();
                if (event.getCode() == KeyCode.RIGHT) {
                    movePlayer.x = goDirection(spaceship.getLayoutX(), "RIGHT");
                    spaceship.relocate(movePlayer.x,y);         //relocate spaceship
                    player.sendTCP(movePlayer);                 //send message to server
                } else if (event.getCode() == KeyCode.LEFT) {
                    movePlayer.x = goDirection(spaceship.getLayoutX(), "LEFT");
                    spaceship.relocate(movePlayer.x,y);          //relocate spaceship
                    player.sendTCP(movePlayer);                 //send message to server

                } else if (event.getCode() == KeyCode.SPACE) {
                    fire(anchorFour, "bullet");                //spaceship shoots here
                    firePlayer.x = spaceship.getLayoutX();
                    player.sendTCP(firePlayer);                 //send message to server

                }
            }

            event.consume();
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                makeAlienShoot(anchorFour);
                if (!isFinished){
                    checkisSpaceShipDead(anchorFour, healthCount, gameOver, homeButton, scoreLabel);
                }

                if(isLevelFinished()){
                    isFinished = true;
                    if(opponentHitCounter > spaceshipHitCounter){
                        setGameScore(loserScore);
                    }
                    homeButton.setVisible(true);
                    levelFinished(endLevel);
                }
                if (opponentDead){
                    isFinished = true;
                    homeButton.setVisible(true);
                    levelFinished(endLevel);
                }
            }
        }.start();
    }

    @Override
    void makeAlienShoot(AnchorPane anchor){
        Counter++;
        if (noAlienShoot != -1/*Counter % alienShootTime == 0*/){ //aliens shoot when the counter counter is 1100*n
            //kimden geliyo bunlar evladım
            alienShoot(noAlienShoot,anchor);
            noAlienShoot = -1;
        }

    }
    //if spaceship's health is zero than it is dead, so we should show that game is over and send info to server
    //id spaceship is not dead update score and always check for any alien is shot or not.
    @Override
    void checkisSpaceShipDead(AnchorPane anchor, Label healthCount, Label gameOver, Button homeButton, Label scoreLabel){
        if (isSpaceshipDown(anchor, healthCount)){ //Check if the spaceship's health 0 or not
            gameOver.setVisible(true);
            homeButton.setVisible(true);
            setGameScore(loserScore);
            Event.OpponentDead deadPlayer = new Event.OpponentDead();
            player.sendTCP(deadPlayer);

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
                    int ind = aliens.indexOf(alien);
                    aliens.remove(alien);
                    if (o.getId() == "bullet"){
                        this.setGameScore(this.getGameScore() + alien.getPoint());
                        spaceshipHitCounter++;
                    }
                    else {
                        opponentHitCounter++;
                    }
                    Event.AlienIsDead deadAlien = new Event.AlienIsDead();
                    deadAlien.alienIndex = ind;
                    player.sendTCP(deadAlien);
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
