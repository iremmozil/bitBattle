package edu.metu.ceng453.bitBattle;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;

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

import java.io.IOException;


public class LevelOneController extends Controller{

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorOne;
    @FXML
    GridPane gridOne;

    @FXML ImageView alien1;
    @FXML ImageView alien2;
    @FXML ImageView alien3;
    @FXML ImageView alien4;
    @FXML ImageView alien5;
    @FXML ImageView alien6;
    @FXML ImageView alien7;
    @FXML ImageView alien8;
    @FXML ImageView alien9;
    @FXML ImageView alien10;
    @FXML ImageView alien11;

    @FXML Label scoreLabel;
    @FXML Label endLevel;
    @FXML Label healthCount;
    @FXML Label gameOver;
    @FXML
    Button homeButton;

    private int score = 0;
    private int Counter = 0;
    private Boolean isFinished = false;
    private int health = 3;

    public void initialize() {
        scoreLabel.setText(Integer.toString(score));
        endLevel.setVisible(false);
        homeButton.setVisible(false);
        gameOver.setVisible(false);

        animatealiens();
        gridOne.setOnKeyPressed((KeyEvent event)->{
            if (event.getCode() == KeyCode.RIGHT){
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if (x < 480){
                    x = x + 6;
                }
                spaceship.relocate(x,y);
            }
            else if (event.getCode() == KeyCode.LEFT){
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if( x > -20){
                    x = x - 6;
                }
                spaceship.relocate(x,y);
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
                Counter++;
                if (Counter % 1000 == 0){
                    Circle b = new Circle(5.05);
                    b.setStroke(Color.BLACK);
                    b.setStrokeWidth(0.0);
                    b.setFill(Color.valueOf("99daff"));
                    alienRandomize(b);
                    double x = b.getCenterX();
                    double y = b.getCenterY();
                    PathTransition tt =
                            new PathTransition(Duration.seconds(3), new Line(x,y, x ,650),b);
                    tt.play();
                }
                isSpaceshipDown();
                alienShot();
                scoreLabel.setText(Integer.toString(getScore()));
                isLevelFinished();
            }
        }.start();
    }
    public void fire(AnchorPane anchor, ImageView spaceship){
        Circle bullet = new Circle(5.05);
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

    public int getScore(){
        return score;
    }

    private void alienShot(){
        for(Node o: anchorOne.getChildren()){
            if (o.getId() == "bullet"){
                if (isAlienShot(o, alien1)) break;
                if (isAlienShot(o, alien2)) break;
                if (isAlienShot(o, alien3)) break;
                if (isAlienShot(o, alien4)) break;
                if (isAlienShot(o, alien5)) break;
                if (isAlienShot(o, alien6)) break;
                if (isAlienShot(o, alien7)) break;
                if (isAlienShot(o, alien8)) break;
                if (isAlienShot(o, alien9)) break;
                if (isAlienShot(o, alien10)) break;
                if (isAlienShot(o, alien11)) break;
            }
        }
    }

    private boolean isAlienShot(Node o, ImageView alien) {
        if (anchorOne.getChildren().contains(alien)) {
            if(o.getBoundsInParent().intersects(alien.getBoundsInParent())){
                anchorOne.getChildren().remove(o);
                anchorOne.getChildren().remove(alien);
                score += 5;
                return true;
            }
        }
        return false;
    }

    private void isSpaceshipDown() {
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
            if (health > 0){
                health--;
                healthCount.setText(Integer.toString(health));
            }
            else{
                gameOver.setVisible(true);
                homeButton.setVisible(true);
            }
        }
    }

    private void isLevelFinished(){
        if (anchorOne.getChildren().contains(alien1) ||
                anchorOne.getChildren().contains(alien2) ||
                anchorOne.getChildren().contains(alien3) ||
                anchorOne.getChildren().contains(alien4) ||
                anchorOne.getChildren().contains(alien5) ||
                anchorOne.getChildren().contains(alien6) ||
                anchorOne.getChildren().contains(alien7) ||
                anchorOne.getChildren().contains(alien8) ||
                anchorOne.getChildren().contains(alien9) ||
                anchorOne.getChildren().contains(alien10) ||
                anchorOne.getChildren().contains(alien11)
        ){ } else{
                endLevel.setVisible(true);
                isFinished = true;
        }
    }

    private void alienRandomize(Circle b){
        anchorOne.getChildren();
        int n = (int)(Math.random() * 11 + 1);
        double alienHeight = 40.0;
        double alienHalfWidth = 22.0;
        switch (n){
                case 1: if (anchorOne.getChildren().contains(alien1)){
                            b.setCenterX(alien1.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                            b.setCenterY(alien1.getLayoutY() + alienHeight + alien3.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 2: if (anchorOne.getChildren().contains(alien2)){
                            b.setCenterX(alien2.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                            b.setCenterY(alien2.getLayoutY() + alienHeight + alien3.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 3: if (anchorOne.getChildren().contains(alien3)){
                            b.setCenterX(alien3.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                            b.setCenterY(alien3.getLayoutY() + alienHeight + alien3. getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 4: if (anchorOne.getChildren().contains(alien4)){
                            b.setCenterX(alien4.getLayoutX() + alienHalfWidth + alien4.getTranslateX());
                            b.setCenterY(alien4.getLayoutY() + alienHeight + alien4.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 5: if (anchorOne.getChildren().contains(alien5)){
                            b.setCenterX(alien5.getLayoutX() + alienHalfWidth + alien5.getTranslateX());
                            b.setCenterY(alien5.getLayoutY() + alienHeight + alien5.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 6:if (anchorOne.getChildren().contains(alien6)){
                            b.setCenterX(alien6.getLayoutX() + alienHalfWidth + alien6.getTranslateX());
                            b.setCenterY(alien6.getLayoutY() + alienHeight + alien6.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                        break;
                case 7: if (anchorOne.getChildren().contains(alien7)){
                            b.setCenterX(alien7.getLayoutX() + alienHalfWidth + alien7.getTranslateX());
                            b.setCenterY(alien7.getLayoutY() + alienHeight + alien7.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                            break;
                case 8: if (anchorOne.getChildren().contains(alien8)){
                            b.setCenterX(alien8.getLayoutX() + alienHalfWidth + alien8.getTranslateX());
                            b.setCenterY(alien8.getLayoutY() + alienHeight + alien8.getTranslateY());
                            anchorOne.getChildren().add(b);
                         }
                            break;
                case 9:if (anchorOne.getChildren().contains(alien9)){
                            b.setCenterX(alien9.getLayoutX() + alienHalfWidth + alien9.getTranslateX());
                            b.setCenterY(alien9.getLayoutY() + alienHeight + alien9.getTranslateY());
                            anchorOne.getChildren().add(b);
                            }
                            break;

                case 10: if (anchorOne.getChildren().contains(alien10)){
                            b.setCenterX(alien10.getLayoutX() + alienHalfWidth + alien10.getTranslateX());
                            b.setCenterY(alien10.getLayoutY() + alienHeight + alien10.getTranslateY());
                            anchorOne.getChildren().add(b);
                        }
                            break;
                case 11: if (anchorOne.getChildren().contains(alien11)){
                             b.setCenterX(alien11.getLayoutX() + alienHalfWidth + alien11.getTranslateX());
                             b.setCenterY(alien11.getLayoutY() + alienHeight + alien11.getTranslateY());
                            anchorOne.getChildren().add(b);
                            }
                            break;
            }
    }

     private void animatealiens() {
         PathTransition tt1 =
                 new PathTransition(Duration.seconds(5), new Line(),alien1);
         PathTransition tt2 =
                new PathTransition(Duration.seconds(5), new Line(),alien2);
         PathTransition tt3 =
                 new PathTransition(Duration.seconds(5), new Line(),alien3);
         PathTransition tt4 =
                 new PathTransition(Duration.seconds(5), new Line(),alien4);
         PathTransition tt5 =
                 new PathTransition(Duration.seconds(5), new Line(),alien5);
         PathTransition tt6 =
                 new PathTransition(Duration.seconds(5), new Line(),alien6);
         PathTransition tt7 =
                 new PathTransition(Duration.seconds(5), new Line(),alien7);
         PathTransition tt8 =
                 new PathTransition(Duration.seconds(5), new Line(),alien8);
         PathTransition tt9 =
                 new PathTransition(Duration.seconds(5), new Line(),alien9);
         PathTransition tt10 =
                 new PathTransition(Duration.seconds(5), new Line(),alien10);
         PathTransition tt11 =
                 new PathTransition(Duration.seconds(5), new Line(),alien11);

         tt2.setPath(new Rectangle(20,15));
         tt1.setPath(new Rectangle(20,15));
         tt4.setPath(new Rectangle(20,15));
         tt5.setPath(new Rectangle(20,15));
         tt6.setPath(new Rectangle(20,15));
         tt7.setPath(new Rectangle(20,15));
         tt8.setPath(new Rectangle(20,15));
         tt9.setPath(new Rectangle(20,15));
         tt10.setPath(new Rectangle(20,15));
         tt11.setPath(new Rectangle(20,15));
         tt3.setPath(new Rectangle(20,15));


        tt2.setCycleCount( Timeline.INDEFINITE );
         tt1.setCycleCount( Timeline.INDEFINITE );
         tt3.setCycleCount( Timeline.INDEFINITE );
         tt4.setCycleCount( Timeline.INDEFINITE );
         tt5.setCycleCount( Timeline.INDEFINITE );
         tt6.setCycleCount( Timeline.INDEFINITE );
         tt7.setCycleCount( Timeline.INDEFINITE );
         tt8.setCycleCount( Timeline.INDEFINITE );
         tt9.setCycleCount( Timeline.INDEFINITE );
         tt10.setCycleCount( Timeline.INDEFINITE );
         tt11.setCycleCount( Timeline.INDEFINITE );

        tt2.play();
         tt1.play();
         tt3.play();
         tt4.play();
         tt5.play();
         tt6.play();
         tt7.play();
         tt8.play();
         tt9.play();
         tt10.play();
         tt11.play();
    }

    public void secondLevel(KeyEvent event) throws IOException {
        Parent levelTwo = FXMLLoader.load(getClass().getResource("./levelTwo.fxml"));
        Scene scenetwo = new Scene(levelTwo);
        scenetwo.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(scenetwo);
        window.show();
    }

    public void homeButtonPushed(ActionEvent event) throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("./home.fxml"));
        Scene sceneHome = new Scene(home);
        sceneHome.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneHome);
        window.show();


    }

}
