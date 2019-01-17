package edu.metu.ceng453.bitBattle.level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.metu.ceng453.bitBattle.Main;
import edu.metu.ceng453.bitBattle.alien.Alien;
import edu.metu.ceng453.bitBattle.alien.AlienFactory;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Random;

import java.util.ArrayList;

public abstract class LevelController extends Main {

    //Variables
    private Boolean isFinished = false;
    private static int gameScore = 0;
    private int health = 3;
    private int Counter = 0;

    private int windowLeftEdge = -20;
    private int windowRightEdge = 455;
    private int spaceShipMoveSize = 6;
    private double bulletRadious = 5.05;
    private double spaceshipHalfWidth = 35.0;
    private double  spaceshipsNoseY = 7.0;
    private String purple = "997aff";
    private int aboveOfWindow = -10;
    private int alienShootTime = 1100;

    private boolean isShot = false;
    private boolean dbUpdate = false;
    private boolean isSpaceshipDown = false;
    private boolean finished = false;


    ArrayList<Alien> aliens = new ArrayList<Alien>();
    ArrayList<Node> spaceships = new ArrayList<>();

    //Getters and setters
    int getGameScore() {
        return this.gameScore;
    }

    void setGameScore(int score) {
        this.gameScore = score;
    }

    private int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }


    void initializeLevel(AnchorPane anchor, Label endLevel, Label gameOver, Button homeButton){
        spaceshipsToArray(anchor);
        aliensToArray(anchor);
        endLevel.setVisible(false);
        gameOver.setVisible(false);
        homeButton.setVisible(false);
    }

    //get aliens to an array and create aliens
    private void aliensToArray(AnchorPane anchor){
        /*when new level is started empty the array
        if (aliens.size() > 0){
            for (Alien alien: aliens){
                aliens.remove(alien);
            }
        }*/
        AlienFactory alienFactory = new AlienFactory();

        for (Node node: anchor.getChildren()){
            String tag = node.getId();
            if (tag.equals("triangle") || tag.equals("circle") || tag.equals("parallelogram")){
                aliens.add(alienFactory.createAlien(tag,node));
            }
        }
    }

    //get spaceships to array
    private void spaceshipsToArray(AnchorPane anchor){
        for (Node node: anchor.getChildren()){
            String tag = node.getId();
            if (tag.equals("spaceship") || tag.equals("opponent")){
                spaceships.add(node);
            }
        }
    }

    //Alien animations
    void animateAliens(){
        for (Alien a: aliens){
            a.move();
        }
    }

    //When spaceship shoot find that bullet from anchorPane's children and check if it intersect with any alien
    void alienShot(AnchorPane anchor){
        for(Node o: anchor.getChildren()){
            if (o.getId() == "bullet"){                     // it works somehow this way not with ".equals()"
                if (isAlienShot(o, aliens, anchor)) break;
            }
        }
    }


    // if bullet intersects one of the aliens return true, add score 5 points and
    // if the alien's health is zero remove alien from aliens arrayList, else return false
    boolean isAlienShot(Node o, ArrayList<Alien> aliens, AnchorPane anchor) {
        isShot =  false;
        for (Alien alien: aliens){
            // Check if this alien is shot down, if it is return true and remove bullet from anchorPane else return false
            // Check aliens's health if it is zero remove the alien from anchorPane
            if (alien.isShotDown(anchor,o)){
                isShot = true;
                if (alien.getHealth() == 0){
                    aliens.remove(alien);
                    this.setGameScore(this.getGameScore() + alien.getPoint());
                }
                break;
            }
        }
        return isShot;
    }

    //if there is no alien then the level is finished
     boolean isLevelFinished(){
        isFinished = false;
        isFinished = aliens.size() <= 0;
        return isFinished;
    }

    boolean game(AnchorPane anchor, Label healthCount, Label gameOver, Button homeButton, Label scoreLabel, Label endLevel){
        makeAlienShoot(anchor);
        checkisSpaceShipDead(anchor, healthCount, gameOver, homeButton, scoreLabel);
        if (isLevelFinished()){
            finished = true;
            levelFinished(endLevel);
        }
        return finished;
    }

    void makeAlienShoot(AnchorPane anchor) {
        Counter++;
        if (Counter % alienShootTime == 0) { //aliens shoot when the counter counter is 1100*n
            randomAlienShoot(anchor);
        }
    }
    void checkisSpaceShipDead(AnchorPane anchor, Label healthCount, Label gameOver, Button homeButton, Label scoreLabel){
        if (isSpaceshipDown(anchor, healthCount)){ //Check if the spaceship's health 0 or not
            gameOver.setVisible(true);
            homeButton.setVisible(true);
        } else {
            scoreLabel.setText(Integer.toString(getGameScore()));
            alienShot(anchor); //Make sure always check is any alien been shot or not
        }
    }

    void levelFinished(Label endLevel){
        endLevel.setVisible(true);
        setGameScore(this.getGameScore());
    }


    //When user presses SPACE spaceship should fire!
    void fire(AnchorPane anchor, String type){
        Circle bullet = new Circle(bulletRadious);
        bullet.setStrokeWidth(0.0);
        bullet.setFill(Color.valueOf(purple));
        if (type.equals("bullet")){
            bullet.setCenterX(spaceships.get(0).getLayoutX() + spaceshipHalfWidth);
            bullet.setCenterY(spaceships.get(0).getLayoutY() - spaceshipsNoseY);
            bullet.setId("bullet");
        }
        else {
            bullet.setCenterX(spaceships.get(1).getLayoutX() + spaceshipHalfWidth);
            bullet.setCenterY(spaceships.get(1).getLayoutY() - spaceshipsNoseY);
            bullet.setId("obullet");
        }

        Alien.sendBullet(anchor, bullet, aboveOfWindow);
    }

    //When user presses RIGHT or LEFT key calculate spaceship's new location
    double goDirection(double x, String direction){
        if( direction.equals("RIGHT")){
            if (x < windowRightEdge){
                x = x + spaceShipMoveSize;
            }
        }
        else if(direction.equals("LEFT")){
            if( x > windowLeftEdge){
                x = x - spaceShipMoveSize;
            }
        }
        return x;
    }

    //Decide which alien is gonna shoot
    private void randomAlienShoot(AnchorPane anchor){
        Random rand = new Random();
        if (aliens.size() > 0){
            int n = rand.nextInt(aliens.size()) + 0;
            alienShoot(n, anchor);
        }
    }
    //alien with the given index will be shoot
    void alienShoot(int n, AnchorPane anchor){
        Platform.runLater(() -> {aliens.get(n).fire(anchor);});
    }

    //check if one of the spaceships is hit
    private boolean isSpaceshipHit(AnchorPane anchor){
        boolean spaceshipHit = false;
        for (Node node:anchor.getChildren()){
            if (spaceships.size() == 1){ //if there is no opponent
                if (node != spaceships.get(0)){
                    if(spaceships.get(0).getBoundsInParent().intersects(node.getBoundsInParent())) {
                        removeBullet(anchor, node);
                        spaceshipHit = true;
                        break;
                    }
                }
            }else{
                if (node != spaceships.get(0) && node != spaceships.get(1)){
                    if(spaceships.get(0).getBoundsInParent().intersects(node.getBoundsInParent())) {
                        removeBullet(anchor, node);
                        spaceshipHit = true;
                        break;
                    }
                    if(spaceships.get(1).getBoundsInParent().intersects(node.getBoundsInParent())) {
                        removeBullet(anchor, node);
                        break;
                    }
                }
            }
        }
        return spaceshipHit;
    }
    //romeves bullet from anchorPane
    private void removeBullet (AnchorPane anchor, Node node){
        anchor.getChildren().remove(node);
    }

    // calculates the health point of spaceship and updates health Label
    // if health point is zero notify that spaceship is down and update database
     boolean isSpaceshipDown(AnchorPane anchor, Label healthCount) {

        if (isSpaceshipHit(anchor) && !isLevelFinished()){
            if (getHealth() > 0){
                setHealth(getHealth() -1);
                healthCount.setText(Integer.toString(getHealth()));
            }
            if (getHealth() == 0){
                isSpaceshipDown = true;
                updateDatabase();
            }
        }
        return isSpaceshipDown;
    }

    void updateDatabase(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            if (!dbUpdate) {
                Main.getCurrentGame().setScore(this.getGameScore());
                if(Main.getCurrentPlayer().getHighScore() == null || Main.getCurrentPlayer().getHighScore()<this.getGameScore())
                    Main.getCurrentPlayer().setHighScore(this.getGameScore());
                HttpPost gameRequest = new HttpPost(protocol + host + port + leaderboardPath);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                String jsonInString = gson.toJson(Main.getCurrentGame());
                StringEntity params = new StringEntity(jsonInString);
                gameRequest.addHeader("content-type", "application/json");
                //System.out.println(params);


                gameRequest.setEntity(params);
                httpClient.execute(gameRequest);
                //System.out.println("POST Request Handling");

                HttpPut playerRequest = new HttpPut(protocol + host + port + playerPath + Main.getCurrentPlayer().getId());

                params = new StringEntity(Main.getCurrentPlayer().getHighScore().toString());
                playerRequest.addHeader("content-type", "application/json");
                playerRequest.setEntity(params);
                httpClient.execute(playerRequest);
                dbUpdate = true;
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //When user presses key N, the next level will be opened
    void goNextLevel(KeyEvent event, Parent next) throws IOException{
        Scene sceneNext = new Scene(next);
        sceneNext.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneNext);
        window.show();
    }

    //When user presses Go to Home button go Home Page
    void goHomePage(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("../design/home.fxml"));
        setScene(event, home);
    }
}
