package edu.metu.ceng453.bitBattle;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import javafx.collections.ObservableList;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class LevelTwoController{
    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorTwo;
    @FXML
    GridPane gridTwo;

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
    @FXML ImageView circleAlien1;
    @FXML ImageView circleAlien2;
    @FXML ImageView circleAlien3;
    @FXML ImageView circleAlien4;

    @FXML
    Label healthCount;
    @FXML Label scoreLabel;
    @FXML Label levelend;
    @FXML Label gameOver;
    @FXML Button homeButton;


    private int Counter = 0;
    private int health = 3;
    private int score = Main.getCurrentGame().getScore();
    private boolean dbUpdate = false;
    private boolean isFinished;
    private int c1 = 2;
    private int c2 = 2;
    private int c3 = 2;
    private int c4 = 2;


    public void initialize() {
        levelend.setVisible(false);
        gameOver.setVisible(false);
        homeButton.setVisible(false);

        isFinished = false;

        animatealiens2();
        gridTwo.setOnKeyPressed((KeyEvent event)-> {
            if (event.getCode() == KeyCode.RIGHT) {
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if (x < 480) {
                    x = x + 6;
                }
                spaceship.relocate(x, y);
            } else if (event.getCode() == KeyCode.LEFT) {
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if (x > -20) {
                    x = x - 6;
                }
                spaceship.relocate(x, y);
            } else if (event.getCode() == KeyCode.SPACE) {
                Circle bullet = new Circle(5.05);
                bullet.setStroke(Color.BLACK);
                bullet.setStrokeWidth(0.0);
                bullet.setFill(Color.valueOf("997aff"));
                bullet.setCenterX(spaceship.getLayoutX() + 35.0);
                bullet.setCenterY(spaceship.getLayoutY() - 7.0);
                bullet.setId("bullet");
                anchorTwo.getChildren().add(bullet);
                double x = bullet.getCenterX();
                double y = bullet.getCenterY();
                PathTransition tt =
                        new PathTransition(Duration.seconds(3), new Line(x, y, x, -10), bullet);
                tt.play();
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
                Counter++;
                if (Counter % 1000 == 0){
                    Circle b = new Circle(5.05);
                    b.setStroke(Color.BLACK);
                    b.setStrokeWidth(0.0);
                    b.setFill(Color.valueOf("99daff"));
                    alienRandomize2(b);
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

    public int getScore(){
        return score;
    }

    private void isSpaceshipDown() {
        boolean collisionDetected = false;
        for (Node bullet : anchorTwo.getChildren()) {
            if(bullet != spaceship) {
                if (spaceship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    collisionDetected = true;
                    anchorTwo.getChildren().remove(bullet);
                    break;
                }
            }
        }

        if (collisionDetected){
            collisionDetected = false;
            if (health > 0){
                health--;
                healthCount.setText(Integer.toString(health));
            }else {

                CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                try {
                    if (!dbUpdate) {
                        Main.getCurrentGame().setScore(score);
                        if(Main.getCurrentPlayer().getHighScore() == null || Main.getCurrentPlayer().getHighScore()<score)
                            Main.getCurrentPlayer().setHighScore(score);
                        HttpPost gameRequest = new HttpPost("http://localhost:8080/leaderboard");

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        String jsonInString = gson.toJson(Main.getCurrentGame());
                        StringEntity params = new StringEntity(jsonInString);
                        gameRequest.addHeader("content-type", "application/json");

                        gameRequest.setEntity(params);
                        httpClient.execute(gameRequest);
                        System.out.println("POST Request Handling");

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
                gameOver.setVisible(true);
                homeButton.setVisible(true);
            }
        }
    }

    private void alienShot(){
        for(Node o: anchorTwo.getChildren()){
            if (o.getId() == "bullet"){
                if (isAlienShot(o, alien1) ||
                        isAlienShot(o, alien2) ||
                        isAlienShot(o, alien3) ||
                        isAlienShot(o, alien4) ||
                        isAlienShot(o, alien5) ||
                        isAlienShot(o, alien6) ||
                        isAlienShot(o, alien7) ||
                        isAlienShot(o, alien8) ||
                        isAlienShot(o, alien9) ||
                        isAlienShot(o, alien10))
                    break;
                if (anchorTwo.getChildren().contains(circleAlien1)){
                    if(o.getBoundsInParent().intersects(circleAlien1.getBoundsInParent())){
                        c1--;
                        anchorTwo.getChildren().remove(o);
                        if (c1 == 0){

                            anchorTwo.getChildren().remove(circleAlien1);
                            score += 10;

                        }
                        break;
                    }
                }
                if (anchorTwo.getChildren().contains(circleAlien2)){
                    if(o.getBoundsInParent().intersects(circleAlien2.getBoundsInParent())){
                        c2--;
                        anchorTwo.getChildren().remove(o);
                        if (c2 == 0){

                            anchorTwo.getChildren().remove(circleAlien2);
                            score += 10;

                        }
                        break;
                    }
                }
                if (anchorTwo.getChildren().contains(circleAlien3)){
                    if(o.getBoundsInParent().intersects(circleAlien3.getBoundsInParent())){
                        c3--;
                        anchorTwo.getChildren().remove(o);
                        if (c3 == 0){

                            anchorTwo.getChildren().remove(circleAlien3);
                            score += 10;

                        }
                        break;
                    }


                }
                if (anchorTwo.getChildren().contains(circleAlien4)){
                    if(o.getBoundsInParent().intersects(circleAlien4.getBoundsInParent())){
                        c4--;
                        anchorTwo.getChildren().remove(o);
                        if (c4 == 0){

                            anchorTwo.getChildren().remove(circleAlien4);
                            score += 10;

                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean isAlienShot(Node o, ImageView alien) {
        if (anchorTwo.getChildren().contains(alien)) {
            if(o.getBoundsInParent().intersects(alien.getBoundsInParent())){
                anchorTwo.getChildren().remove(o);
                anchorTwo.getChildren().remove(alien);
                score += 5;
                return true;
            }
        }
        return false;
    }


    private void animatealiens2(){
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
                new PathTransition(Duration.seconds(5), new Line(),circleAlien1);

        PathTransition tt12 =
                new PathTransition(Duration.seconds(5), new Line(),circleAlien2);

        PathTransition tt13 =
                new PathTransition(Duration.seconds(5), new Line(),circleAlien3);

        PathTransition tt14 =
                new PathTransition(Duration.seconds(5), new Line(),circleAlien4);


        tt2.setPath(new Rectangle(20,15));
        tt1.setPath(new Rectangle(20,15));
        tt4.setPath(new Rectangle(20,15));
        tt5.setPath(new Rectangle(20,15));
        tt6.setPath(new Rectangle(20,15));
        tt7.setPath(new Rectangle(20,15));
        tt8.setPath(new Rectangle(20,15));
        tt9.setPath(new Rectangle(20,15));
        tt3.setPath(new Rectangle(20,15));
        tt10.setPath(new Rectangle(20,15));
        tt11.setPath(new Rectangle(20,15));
        tt12.setPath(new Rectangle(20,15));
        tt13.setPath(new Rectangle(20,15));
        tt14.setPath(new Rectangle(20,15));

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
        tt11.setCycleCount(Timeline.INDEFINITE);
        tt12.setCycleCount(Timeline.INDEFINITE);
        tt13.setCycleCount(Timeline.INDEFINITE);
        tt14.setCycleCount(Timeline.INDEFINITE);

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
        tt12.play();
        tt13.play();
        tt14.play();
    }

    private void isLevelFinished(){
        ObservableList<Node> children = anchorTwo.getChildren();
        if (!children.contains(alien1) &&
                !children.contains(alien2) &&
                !children.contains(alien3) &&
                !children.contains(alien4) &&
                !children.contains(alien5) &&
                !children.contains(alien6) &&
                !children.contains(alien7) &&
                !children.contains(alien8) &&
                !children.contains(alien9) &&
                !children.contains(alien10) &&
                !children.contains(circleAlien1) &&
                !children.contains(circleAlien2) &&
                !children.contains(circleAlien3) &&
                !children.contains(circleAlien4)
        ) {
            levelend.setVisible(true);
            isFinished = true;
            Main.getCurrentGame().setScore(score);
        }
    }


    public void alienRandomize2(Circle b){
        anchorTwo.getChildren();
        int n = (int)(Math.random() * 14 + 1);
        double alienHeight = 40.0;
        double alienHalfWidth = 22.0;
        switch (n){
            case 1: if (anchorTwo.getChildren().contains(alien1)){
                b.setCenterX(alien1.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien1.getLayoutY() + alienHeight + alien3.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 2: if (anchorTwo.getChildren().contains(alien2)){
                b.setCenterX(alien2.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien2.getLayoutY() + alienHeight + alien3.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 3: if (anchorTwo.getChildren().contains(alien3)){
                b.setCenterX(alien3.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien3.getLayoutY() + alienHeight + alien3. getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 4: if (anchorTwo.getChildren().contains(alien4)){
                b.setCenterX(alien4.getLayoutX() + alienHalfWidth + alien4.getTranslateX());
                b.setCenterY(alien4.getLayoutY() + alienHeight + alien4.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 5: if (anchorTwo.getChildren().contains(alien5)){
                b.setCenterX(alien5.getLayoutX() + alienHalfWidth + alien5.getTranslateX());
                b.setCenterY(alien5.getLayoutY() + alienHeight + alien5.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 6:if (anchorTwo.getChildren().contains(alien6)){
                b.setCenterX(alien6.getLayoutX() + alienHalfWidth + alien6.getTranslateX());
                b.setCenterY(alien6.getLayoutY() + alienHeight + alien6.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 7: if (anchorTwo.getChildren().contains(alien7)){
                b.setCenterX(alien7.getLayoutX() + alienHalfWidth + alien7.getTranslateX());
                b.setCenterY(alien7.getLayoutY() + alienHeight + alien7.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 8: if (anchorTwo.getChildren().contains(alien8)){
                b.setCenterX(alien8.getLayoutX() + alienHalfWidth + alien8.getTranslateX());
                b.setCenterY(alien8.getLayoutY() + alienHeight + alien8.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 9:if (anchorTwo.getChildren().contains(alien9)){
                b.setCenterX(alien9.getLayoutX() + alienHalfWidth + alien9.getTranslateX());
                b.setCenterY(alien9.getLayoutY() + alienHeight + alien9.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;

            case 10: if (anchorTwo.getChildren().contains(alien10)){
                b.setCenterX(alien10.getLayoutX() + alienHalfWidth + alien10.getTranslateX());
                b.setCenterY(alien10.getLayoutY() + alienHeight + alien10.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 11: if (anchorTwo.getChildren().contains(circleAlien1)){
                b.setCenterX(circleAlien1.getLayoutX() + alienHalfWidth + circleAlien1.getTranslateX());
                b.setCenterY(circleAlien1.getLayoutY() + alienHeight + circleAlien1.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 12: if (anchorTwo.getChildren().contains(circleAlien2)){
                b.setCenterX(circleAlien2.getLayoutX() + alienHalfWidth + circleAlien2.getTranslateX());
                b.setCenterY(circleAlien2.getLayoutY() + alienHeight + circleAlien2.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 13: if (anchorTwo.getChildren().contains(circleAlien3)){
                b.setCenterX(circleAlien3.getLayoutX() + alienHalfWidth + circleAlien3.getTranslateX());
                b.setCenterY(circleAlien3.getLayoutY() + alienHeight + circleAlien3.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;
            case 14: if (anchorTwo.getChildren().contains(circleAlien4)){
                b.setCenterX(circleAlien4.getLayoutX() + alienHalfWidth + circleAlien4.getTranslateX());
                b.setCenterY(circleAlien4.getLayoutY() + alienHeight + circleAlien4.getTranslateY());
                anchorTwo.getChildren().add(b);
            }
                break;

        }
        }

        public void thirdLevel(KeyEvent event) throws IOException {
            Parent levelTwo = FXMLLoader.load(getClass().getResource("design/levelThree.fxml"));
            Scene scenetwo = new Scene(levelTwo);
            scenetwo.getRoot().requestFocus();
            Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
            window.setScene(scenetwo);
            window.show();
        }

    public void homeButtonPushed(ActionEvent event) throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("design/home.fxml"));
        Scene sceneHome = new Scene(home);
        sceneHome.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneHome);
        window.show();


    }
    }
