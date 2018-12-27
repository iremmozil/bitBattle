package edu.metu.ceng453.bitBattle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

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




public class levelThreeController extends LevelController {

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorThree;
    @FXML
    GridPane gridThree;

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
    @FXML ImageView circle1;
    @FXML ImageView circle2;
    @FXML ImageView circle3;
    @FXML ImageView circle4;
    @FXML ImageView parallelogram1;
    @FXML ImageView parallelogram2;

    @FXML
    Label healthCount;
    @FXML Label scoreLabel;
    @FXML Label endLevel;
    @FXML Label gameOver;
    @FXML Button homeButton;

    private int score = Main.getCurrentGame().getScore();
    private int Counter = 0;
    private Boolean isFinished = false;
    private boolean dbUpdate = false;
    private int health = 3;
    private int c1 = 2;
    private int c2 = 2;
    private int c3 = 2;
    private int c4 = 2;


    public void initialize() {
        endLevel.setVisible(false);
        gameOver.setVisible(false);
        homeButton.setVisible(false);

        isFinished = false;

        animatealiens3();
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
                Counter++;
                if (Counter % 1000 == 0){
                    Circle b = new Circle(5.05);
                    b.setStroke(Color.BLACK);
                    b.setStrokeWidth(0.0);
                    b.setFill(Color.valueOf("99daff"));
                    alienRandomize3(b);
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
        for (Node bullet : anchorThree.getChildren()) {
            if(bullet != spaceship) {
                if (spaceship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    collisionDetected = true;
                    anchorThree.getChildren().remove(bullet);
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
                        if(Main.getCurrentPlayer().getHighScore()<score)
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
        for(Node o: anchorThree.getChildren()){
            if (o.getId() == "bullet"){
                if (isAlienShot(o, alien1, anchorThree)) break;
                if (isAlienShot(o, alien2, anchorThree)) break;
                if (isAlienShot(o, alien3, anchorThree)) break;
                if (isAlienShot(o, alien4, anchorThree)) break;
                if (isAlienShot(o, alien5, anchorThree)) break;
                if (isAlienShot(o, alien6, anchorThree)) break;
                if (isAlienShot(o, alien7, anchorThree)) break;
                if (isAlienShot(o, alien8, anchorThree)) break;
                if (isAlienShot(o, alien9, anchorThree)) break;
                if (isAlienShot(o, alien10, anchorThree)) break;
                if (isAlienShot(o, alien11, anchorThree)) break;
                if (anchorThree.getChildren().contains(circle1)){
                    if(o.getBoundsInParent().intersects(circle1.getBoundsInParent())){
                        c1--;
                        anchorThree.getChildren().remove(o);
                        if (c1 == 0){

                            anchorThree.getChildren().remove(circle1);
                            score += 10;

                        }
                        break;
                    }
                }
                if (anchorThree.getChildren().contains(circle2)){
                    if(o.getBoundsInParent().intersects(circle2.getBoundsInParent())){
                        c2--;
                        anchorThree.getChildren().remove(o);
                        if (c2 == 0){

                            anchorThree.getChildren().remove(circle2);
                            score += 10;

                        }
                        break;
                    }
                }
                if (anchorThree.getChildren().contains(circle3)){
                    if(o.getBoundsInParent().intersects(circle3.getBoundsInParent())){
                        c3--;
                        anchorThree.getChildren().remove(o);
                        if (c3 == 0){

                            anchorThree.getChildren().remove(circle3);
                            score += 10;

                        }
                        break;
                    }


                }
                if (anchorThree.getChildren().contains(circle4)){
                    if(o.getBoundsInParent().intersects(circle4.getBoundsInParent())){
                        c4--;
                        anchorThree.getChildren().remove(o);
                        if (c4 == 0){

                            anchorThree.getChildren().remove(circle4);
                            score += 10;

                        }
                        break;
                    }
                }
                if (isAlienShot(o, parallelogram1, anchorThree)) break;
                if (isAlienShot(o, parallelogram2, anchorThree)) break;
            }
        }
    }


    private void animatealiens3(){
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
        PathTransition tt15 =
                new PathTransition(Duration.seconds(5), new Line(),alien11);

        PathTransition tt11 =
                new PathTransition(Duration.seconds(5), new Line(),circle1);

        PathTransition tt12 =
                new PathTransition(Duration.seconds(5), new Line(),circle2);

        PathTransition tt13 =
                new PathTransition(Duration.seconds(5), new Line(),circle3);

        PathTransition tt14 =
                new PathTransition(Duration.seconds(5), new Line(),circle4);
        PathTransition tt16 =
                new PathTransition(Duration.seconds(5), new Line(),parallelogram1);
        PathTransition tt17 =
                new PathTransition(Duration.seconds(5), new Line(),parallelogram2);


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
        tt15.setPath(new Rectangle(20,15));
        tt16.setPath(new Rectangle(20,15));
        tt17.setPath(new Rectangle(20,15));

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
        tt15.setCycleCount(Timeline.INDEFINITE);
        tt16.setCycleCount(Timeline.INDEFINITE);
        tt17.setCycleCount(Timeline.INDEFINITE);



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
        tt15.play();
        tt16.play();
        tt17.play();
    }

    private void isLevelFinished(){
        if (!anchorThree.getChildren().contains(alien1) &&
                !anchorThree.getChildren().contains(alien2) &&
                !anchorThree.getChildren().contains(alien3) &&
                !anchorThree.getChildren().contains(alien4) &&
                !anchorThree.getChildren().contains(alien5) &&
                !anchorThree.getChildren().contains(alien6) &&
                !anchorThree.getChildren().contains(alien7) &&
                !anchorThree.getChildren().contains(alien8) &&
                !anchorThree.getChildren().contains(alien9) &&
                !anchorThree.getChildren().contains(alien10) &&
                !anchorThree.getChildren().contains(alien11) &&
                !anchorThree.getChildren().contains(circle1) &&
                !anchorThree.getChildren().contains(circle2) &&
                !anchorThree.getChildren().contains(circle3) &&
                !anchorThree.getChildren().contains(circle4) &&
                !anchorThree.getChildren().contains(parallelogram1) &&
                !anchorThree.getChildren().contains(parallelogram2)
        ) {
            endLevel.setVisible(true);
            homeButton.setVisible(true);
            isFinished = true;

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
        }
    }


    public void alienRandomize3(Circle b){
        anchorThree.getChildren();
        int n = (int)(Math.random() * 17 + 1);
        double alienHeight = 40.0;
        double alienHalfWidth = 22.0;
        switch (n){
            case 1: if (anchorThree.getChildren().contains(alien1)){
                b.setCenterX(alien1.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien1.getLayoutY() + alienHeight + alien3.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 2: if (anchorThree.getChildren().contains(alien2)){
                b.setCenterX(alien2.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien2.getLayoutY() + alienHeight + alien3.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 3: if (anchorThree.getChildren().contains(alien3)){
                b.setCenterX(alien3.getLayoutX() + alienHalfWidth + alien3.getTranslateX());
                b.setCenterY(alien3.getLayoutY() + alienHeight + alien3. getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 4: if (anchorThree.getChildren().contains(alien4)){
                b.setCenterX(alien4.getLayoutX() + alienHalfWidth + alien4.getTranslateX());
                b.setCenterY(alien4.getLayoutY() + alienHeight + alien4.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 5: if (anchorThree.getChildren().contains(alien5)){
                b.setCenterX(alien5.getLayoutX() + alienHalfWidth + alien5.getTranslateX());
                b.setCenterY(alien5.getLayoutY() + alienHeight + alien5.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 6:if (anchorThree.getChildren().contains(alien6)){
                b.setCenterX(alien6.getLayoutX() + alienHalfWidth + alien6.getTranslateX());
                b.setCenterY(alien6.getLayoutY() + alienHeight + alien6.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 7: if (anchorThree.getChildren().contains(alien7)){
                b.setCenterX(alien7.getLayoutX() + alienHalfWidth + alien7.getTranslateX());
                b.setCenterY(alien7.getLayoutY() + alienHeight + alien7.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 8: if (anchorThree.getChildren().contains(alien8)){
                b.setCenterX(alien8.getLayoutX() + alienHalfWidth + alien8.getTranslateX());
                b.setCenterY(alien8.getLayoutY() + alienHeight + alien8.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 9:if (anchorThree.getChildren().contains(alien9)){
                b.setCenterX(alien9.getLayoutX() + alienHalfWidth + alien9.getTranslateX());
                b.setCenterY(alien9.getLayoutY() + alienHeight + alien9.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;

            case 10: if (anchorThree.getChildren().contains(alien10)){
                b.setCenterX(alien10.getLayoutX() + alienHalfWidth + alien10.getTranslateX());
                b.setCenterY(alien10.getLayoutY() + alienHeight + alien10.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 11: if (anchorThree.getChildren().contains(alien11)){
                b.setCenterX(alien11.getLayoutX() + alienHalfWidth + alien11.getTranslateX());
                b.setCenterY(alien11.getLayoutY() + alienHeight + alien11.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 12: if (anchorThree.getChildren().contains(circle1)){
                b.setCenterX(circle1.getLayoutX() + alienHalfWidth + circle1.getTranslateX());
                b.setCenterY(circle1.getLayoutY() + alienHeight + circle1.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 13: if (anchorThree.getChildren().contains(circle2)){
                b.setCenterX(circle2.getLayoutX() + alienHalfWidth + circle2.getTranslateX());
                b.setCenterY(circle2.getLayoutY() + alienHeight + circle2.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 14: if (anchorThree.getChildren().contains(circle3)){
                b.setCenterX(circle3.getLayoutX() + alienHalfWidth + circle3.getTranslateX());
                b.setCenterY(circle3.getLayoutY() + alienHeight + circle3.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 15: if (anchorThree.getChildren().contains(circle4)){
                b.setCenterX(circle4.getLayoutX() + alienHalfWidth + circle4.getTranslateX());
                b.setCenterY(circle4.getLayoutY() + alienHeight + circle4.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;

            case 16: if (anchorThree.getChildren().contains(parallelogram1)){
                b.setCenterX(parallelogram1.getLayoutX() + alienHalfWidth + parallelogram1.getTranslateX());
                b.setCenterY(parallelogram1.getLayoutY() + alienHeight + parallelogram1.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;
            case 17: if (anchorThree.getChildren().contains(parallelogram2)){
                b.setCenterX(parallelogram2.getLayoutX() + alienHalfWidth + parallelogram2.getTranslateX());
                b.setCenterY(parallelogram2.getLayoutY() + alienHeight + parallelogram2.getTranslateY());
                anchorThree.getChildren().add(b);
            }
                break;

        }
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
