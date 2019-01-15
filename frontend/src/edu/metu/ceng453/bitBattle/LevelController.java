package edu.metu.ceng453.bitBattle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.metu.ceng453.bitBattle.alien.Alien;
import edu.metu.ceng453.bitBattle.alien.AlienFactory;
import javafx.animation.PathTransition;
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

public class LevelController extends Main {

    //Variables

    private Boolean isFinished = false;
    private int score = 0;
    private int health = 3;
    private int Counter = 0;

    private int windowLeftEdge = -20;
    private int windowRightEdge = 480;
    private int spaceShipMoveSize = 6;
    private double bulletRadious = 5.05;

    private boolean isShot = false;
    private boolean dbUpdate = false;
    private boolean isSpaceshipDown = false;
    private boolean finished = false;

    ArrayList<Alien> aliens = new ArrayList<Alien>();

    //Getters and setters
    int getScore() {
        return this.score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    private int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    //get aliens to an array and create aliens
    void aliensToArray(AnchorPane anchor){
        //when new level is started empty the array
        if (aliens.size() > 0){
            for (Alien alien: aliens){
                aliens.remove(alien);
            }
        }
        AlienFactory alienFactory = new AlienFactory();

        for (Node node: anchor.getChildren()){
            String tag = node.getId();
            if (tag.equals("triangle") || tag.equals("circle") || tag.equals("parallelogram")){
                aliens.add(alienFactory.createAlien(tag,node));
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
    private void alienShot(AnchorPane anchor){
        for(Node o: anchor.getChildren()){
            if (o.getId() == "bullet"){                     // it works somehow this way not with ".equals"
                if (isAlienShot(o, aliens, anchor)) break;
            }
        }
    }


    // if bullet intersects one of the aliens return true, add score 5 points and
    // if the alien's health is zero remove alien from aliens arrayList, else return false
    private boolean isAlienShot(Node o, ArrayList<Alien> aliens, AnchorPane anchor) {
        isShot =  false;
        for (Alien alien: aliens){
            // Check if this alien is shot down, if it is return true and remove bullet from anchorPane else return false
            // Check aliens's health if it is zero remove the alien from anchorPane
            if (alien.isShotDown(anchor,o)){
                this.setScore(this.getScore() + 5);
                isShot = true;
                if (alien.getHealth() == 0){
                    aliens.remove(alien);
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

    boolean game(AnchorPane anchor, ImageView spaceship, Label healthCount, Label gameOver, Button homeButton, Label scoreLabel, Label endLevel){
        Counter++;
        if (Counter % 1000 == 0){ //aliens shoot when the counter counter is 1000*n
            alienRandomize(anchor);
        }
        if (isSpaceshipDown(anchor, spaceship, healthCount)){ //Check if the spaceship's health 0 or not
            gameOver.setVisible(true);
            homeButton.setVisible(true);
        }

        alienShot(anchor); //Make sure always check is any alien been shot or not

        scoreLabel.setText(Integer.toString(getScore()));
        if (isLevelFinished()){
            finished = true;
            endLevel.setVisible(true);
            setScore(this.getScore());
        }
        return finished;
    }


    //When user presses SPACE spaceship should fire!
    void fire(AnchorPane anchor, ImageView spaceship){
        Circle bullet = new Circle(bulletRadious);
        bullet.setStroke(Color.BLACK);
        bullet.setStrokeWidth(0.0);
        bullet.setFill(Color.valueOf("997aff"));
        bullet.setCenterX(spaceship.getLayoutX() + 35.0);
        bullet.setCenterY(spaceship.getLayoutY() - 7.0);
        bullet.setId("bullet");
        anchor.getChildren().add(bullet);
        double x = bullet.getCenterX();
        double y = bullet.getCenterY();
        PathTransition tt =
                new PathTransition(Duration.seconds(3), new Line(x,y, x ,-10),bullet);
        tt.play();
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

    //Decide which alien shoot
    private void alienRandomize(AnchorPane anchor){
        Random rand = new Random();
        if (aliens.size() > 0){
            int n = rand.nextInt(aliens.size()) + 0;
            aliens.get(n).fire(anchor);
        }

    }

    /*
    this function checks if aliens could hit the spaceship or not, if there is hit,
    then checks whether spaceship's health is zero or not. If zero, the game is over function returns true.
    When the game is over database will be updated.
    */
    private boolean isSpaceshipDown(AnchorPane anchorOne, ImageView spaceship, Label healthCount) {
        isSpaceshipDown =false;
        boolean collisionDetected = false;

        for (Node bullet : anchorOne.getChildren()) {
            if(bullet != spaceship) {
                if (spaceship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    collisionDetected = true;
                    anchorOne.getChildren().remove(bullet);
                    break;
                }
            }
        }

        if (collisionDetected){
            collisionDetected = false;
            if (getHealth() > 0){
                setHealth(getHealth() -1);
                healthCount.setText(Integer.toString(getHealth()));
            }
            else{
                isSpaceshipDown = true;
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                try {
                    if (!dbUpdate) {
                        Main.getCurrentGame().setScore(this.getScore());
                        if(Main.getCurrentPlayer().getHighScore() == null || Main.getCurrentPlayer().getHighScore()<this.getScore())
                            Main.getCurrentPlayer().setHighScore(this.getScore());
                        HttpPost gameRequest = new HttpPost("http://localhost:8080/leaderboard");

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        String jsonInString = gson.toJson(Main.getCurrentGame());
                        StringEntity params = new StringEntity(jsonInString);
                        gameRequest.addHeader("content-type", "application/json");
                        //System.out.println(params);


                        gameRequest.setEntity(params);
                        httpClient.execute(gameRequest);
                        //System.out.println("POST Request Handling");

                        HttpPut playerRequest = new HttpPut("http://localhost:8080/player/" + Main.getCurrentPlayer().getId());

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
        }
        return isSpaceshipDown;
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
        Parent home = FXMLLoader.load(getClass().getResource("design/home.fxml"));
        setScene(event, home);
    }
}
