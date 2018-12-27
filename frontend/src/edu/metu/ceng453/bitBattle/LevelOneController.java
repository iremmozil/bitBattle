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


public class LevelOneController extends LevelController {

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

    Node aliens[] = new Node[11];
    Node aliensToShoot[] = new Node[11];

    private int Counter = 0;
    private Boolean isFinished = false;
    private boolean dbUpdate = false;
    private boolean isAlienDown = false;
    public int length = 11;

    public void initialize() {
        aliensToArray(aliens, alien1, alien2, alien3, alien4, alien5, alien6, alien7, alien8, alien9, alien10, alien11);
        aliensToShoot = aliens.clone();

        scoreLabel.setText(Integer.toString(getScore()));
        endLevel.setVisible(false);
        homeButton.setVisible(false);
        gameOver.setVisible(false);

        animatealiens();
        gridOne.setOnKeyPressed((KeyEvent event)->{
            double y = spaceship.getLayoutY();
            if (event.getCode() == KeyCode.RIGHT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "RIGHT"),y);
            }
            else if (event.getCode() == KeyCode.LEFT){
                spaceship.relocate(goDirection(spaceship.getLayoutX(), "LEFT"),y);
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
                    alienRandomize(anchorOne);
                }
                isSpaceshipDown();
                alienShot();
                scoreLabel.setText(Integer.toString(getScore()));
                isLevelFinished();
            }
        }.start();
    }

    static void aliensToArray(Node[] aliens, ImageView alien1, ImageView alien2, ImageView alien3, ImageView alien4, ImageView alien5, ImageView alien6, ImageView alien7, ImageView alien8, ImageView alien9, ImageView alien10, ImageView alien11) {
        aliens[0] = alien1;
        aliens[1] = alien2;
        aliens[2] = alien3;
        aliens[3] = alien4;
        aliens[4] = alien5;
        aliens[5] = alien6;
        aliens[6] = alien7;
        aliens[7] = alien8;
        aliens[8] = alien9;
        aliens[9] = alien10;
        aliens[10] = alien11;
    }

    private void alienShot(){
        for(Node o: anchorOne.getChildren()){
            if (o.getId() == "bullet"){
                if (isAlienShot(o, aliens, anchorOne)) break;
            }
        }
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
            if (getHealth() > 0){
                setHealth(getHealth() -1);
                healthCount.setText(Integer.toString(getHealth()));
            }
            else{
                gameOver.setVisible(true);
                homeButton.setVisible(true);

                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                try {
                    if (!dbUpdate) {
                        Main.getCurrentGame().setScore(getScore());
                        if(Main.getCurrentPlayer().getHighScore() == null || Main.getCurrentPlayer().getHighScore()<getScore())
                            Main.getCurrentPlayer().setHighScore(getScore());
                        HttpPost gameRequest = new HttpPost("http://localhost:8080/leaderboard");

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        String jsonInString = gson.toJson(Main.getCurrentGame());
                        StringEntity params = new StringEntity(jsonInString);
                        gameRequest.addHeader("content-type", "application/json");
                        System.out.println(params);


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
    }

    private void isLevelFinished(){
        ObservableList<Node> children = anchorOne.getChildren();
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
                !children.contains(alien11)
        ) {
                endLevel.setVisible(true);
                isFinished = true;
                setScore(getScore());
        }
    }

    private void alienRandomize(AnchorPane anchorOne){
        Circle b = new Circle(5.05);
        b.setStroke(Color.BLACK);
        b.setStrokeWidth(0.0);
        b.setFill(Color.valueOf("99daff"));
        anchorOne.getChildren();
        int n = (int)(Math.random() * 11 + 0);
        double triangleHeight = 40.0;
        double triangleHalfWidth = 22.0;
        if (anchorOne.getChildren().contains(aliens[n])){
            b.setCenterX(aliens[n].getLayoutX() + triangleHalfWidth + aliens[n].getTranslateX());
            b.setCenterY(aliens[n].getLayoutY() + triangleHeight + aliens[n].getTranslateY());
            anchorOne.getChildren().add(b);
        }
        double x = b.getCenterX();
        double y = b.getCenterY();
        PathTransition tt =
                new PathTransition(Duration.seconds(3), new Line(x,y, x ,650),b);
        tt.play();
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
        Parent levelTwo = FXMLLoader.load(getClass().getResource("design/levelTwo.fxml"));
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
