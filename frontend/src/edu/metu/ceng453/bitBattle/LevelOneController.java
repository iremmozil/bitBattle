package edu.metu.ceng453.bitBattle;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    @FXML ImageView ailen1;
    @FXML ImageView ailen2;
    @FXML ImageView ailen3;
    @FXML ImageView ailen4;
    @FXML ImageView ailen5;
    @FXML ImageView ailen6;
    @FXML ImageView ailen7;
    @FXML ImageView ailen8;
    @FXML ImageView ailen9;
    @FXML ImageView ailen10;
    @FXML ImageView ailen11;
    Integer[] bId = new Integer[1500];
    int bulletCount = 0; // there are 11 alien but their id's start with 1
    ImageView[] aliens = new ImageView[12];

    int Counter = 0;


    public void initialize() {

        animateAilens();
        gridOne.setOnKeyPressed((KeyEvent event)->{
            if (event.getCode() == KeyCode.RIGHT){
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if (x < 630){
                    x = x + 4;
                }
                spaceship.relocate(x,y);
            }
            else if (event.getCode() == KeyCode.LEFT){
                double x = spaceship.getLayoutX();
                double y = spaceship.getLayoutY();
                if( x > -20){
                    x = x - 4;
                }
                spaceship.relocate(x,y);
            }
            else if (event.getCode() == KeyCode.SPACE){
                Circle bullet = new Circle(5.05);
                bullet.setStroke(Color.BLACK);
                bullet.setStrokeWidth(0.0);
                bullet.setFill(Color.valueOf("997aff"));
                bullet.setCenterX(spaceship.getLayoutX() + 35.0);
                bullet.setCenterY(spaceship.getLayoutY() - 7.0);
                bullet.setId("bullet");
                anchorOne.getChildren().add(bullet);
                //bId[bulletCount] = Integer.parseInt(bullet.getId());
                bulletCount++;
                double x = bullet.getCenterX();
                double y = bullet.getCenterY();
                PathTransition tt =
                        new PathTransition(Duration.seconds(3), new Line(x,y, x ,-10),bullet);
                tt.play();

            }
            else if (event.getCode() == KeyCode.N){
                try {
                    secondLevel(event);
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
                    anchorOne.getChildren().add(b);
                    alienRandomize(b);
                    double x = b.getCenterX();
                    double y = b.getCenterY();
                    PathTransition tt =
                            new PathTransition(Duration.seconds(4), new Line(x,y, x ,650),b);
                    tt.play();
                }
                checkBounds();
                alienShot();
            }


        }.start();


    }
    public void alienShot(){

        for(int i = 0; i < bulletCount; i++){
            if (anchorOne.getChildren().get(i).getId() == "bullet"){
                if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen1.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen1);
                    ailen1.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen2.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen2);
                    ailen2.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen3.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen3);
                    ailen3.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen4.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen4);
                    ailen4.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen5.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen5);
                    ailen5.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen6.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen6);
                    ailen6.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen7.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen7);
                    ailen7.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen8.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen8);
                    ailen8.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen9.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen9);
                    ailen9.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen10.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen10);
                    ailen10.setId(null);
                }
                else if(anchorOne.getChildren().get(i).getBoundsInParent().intersects(ailen11.getBoundsInParent())){
                    anchorOne.getChildren().remove(anchorOne.getChildren().get(i));
                    anchorOne.getChildren().remove(ailen11);
                    ailen11.setId(null);
                }
            }
        }
    }

    private void checkBounds() {
        boolean collisionDetected = false;
        for (Node bullet : anchorOne.getChildren()) {
            if(bullet != spaceship) {
                if (spaceship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    collisionDetected = true;
                }
            }
        }

        if (collisionDetected){
            System.out.println("Shot fired!");
        }
    }

    public void alienRandomize(Circle b){
        anchorOne.getChildren();
        int n = (int)(Math.random() * 10 + 0);
        System.out.println(anchorOne.getChildren().get(n).getId());
        if ((anchorOne.getChildren().get(n).getId() != null)){
            switch (n){
                case 1: b.setCenterX(ailen1.getLayoutX() + 20.0);
                    b.setCenterY(ailen1.getLayoutY() + 40.0);
                    break;
                case 2: b.setCenterX(ailen2.getLayoutX() + 22.0);
                    b.setCenterY(ailen2.getLayoutY() + 40.0);
                    break;
                case 3: b.setCenterX(ailen3.getLayoutX() + 22.0);
                    b.setCenterY(ailen3.getLayoutY() + 40.0);
                    break;
                case 4: b.setCenterX(ailen4.getLayoutX() + 22.0);
                    b.setCenterY(ailen4.getLayoutY() + 40.0);
                    break;
                case 5: b.setCenterX(ailen5.getLayoutX() + 22.0);
                    b.setCenterY(ailen5.getLayoutY() + 40.0);
                    break;
                case 6: b.setCenterX(ailen6.getLayoutX() + 22.0);
                    b.setCenterY(ailen6.getLayoutY() + 40.0);
                    break;
                case 7: b.setCenterX(ailen7.getLayoutX() + 22.0);
                    b.setCenterY(ailen7.getLayoutY() + 40.0);
                    break;
                case 8: b.setCenterX(ailen8.getLayoutX() + 22.0);
                    b.setCenterY(ailen8.getLayoutY() + 40.0);
                    break;
                case 9: b.setCenterX(ailen9.getLayoutX() + 22.0);
                    b.setCenterY(ailen9.getLayoutY() + 40.0);
                    break;
                case 10: b.setCenterX(ailen10.getLayoutX() + 22.0);
                    b.setCenterY(ailen10.getLayoutY() + 40.0);
                    break;
                case 11: b.setCenterX(ailen11.getLayoutX() + 22.0);
                    b.setCenterY(ailen11.getLayoutY() + 40.0);
                    break;
            }
        }


    }

     public void animateAilens() {
         PathTransition tt1 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen1);
         PathTransition tt2 =
                new PathTransition(Duration.seconds(5), new Line(),ailen2);
         PathTransition tt3 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen3);
         PathTransition tt4 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen4);
         PathTransition tt5 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen5);
         PathTransition tt6 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen6);
         PathTransition tt7 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen7);
         PathTransition tt8 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen8);
         PathTransition tt9 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen9);
         PathTransition tt10 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen10);
         PathTransition tt11 =
                 new PathTransition(Duration.seconds(5), new Line(),ailen11);

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

}
