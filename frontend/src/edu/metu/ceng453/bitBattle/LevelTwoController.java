package edu.metu.ceng453.bitBattle;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelTwoController {
    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorTwo;
    @FXML
    GridPane gridTwo;

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
    @FXML ImageView circleAlien1;
    @FXML ImageView circleAlien2;
    @FXML ImageView circleAlien3;
    @FXML ImageView circleAlien4;
    @FXML ImageView circleAlien5;
    @FXML ImageView circleAlien6;

    int Counter = 0;
    int bulletCount = 0;

    public void initialize() {
        Circle bullet = new Circle(5.05);
        animateAilens2();
        gridTwo.setOnKeyPressed((KeyEvent event)->{
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

                bullet.setStroke(Color.BLACK);
                bullet.setStrokeWidth(0.0);
                bullet.setFill(Color.valueOf("997aff"));
                bullet.setCenterX(spaceship.getLayoutX() + 35.0);
                bullet.setCenterY(spaceship.getLayoutY() - 7.0);
                bullet.setId(Integer.toString(bulletCount));
                anchorTwo.getChildren().add(bullet);
                double x = bullet.getCenterX();
                double y = bullet.getCenterY();
                PathTransition tt =
                        new PathTransition(Duration.seconds(3), new Line(x,y, x ,-10),bullet);
                tt.play();

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
                    anchorTwo.getChildren().add(b);
                    alienRandomize2(b);
                    double x = b.getCenterX();
                    double y = b.getCenterY();
                    PathTransition tt =
                            new PathTransition(Duration.seconds(4), new Line(x,y, x ,650),b);
                    tt.play();
                }
                checkBounds();
            }
        }.start();
    }

    private void checkBounds() {
        boolean collisionDetected = false;
        for (Node bullet : anchorTwo.getChildren()) {
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

    public void animateAilens2() {
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

    }


    public void alienRandomize2(Circle b){
        anchorTwo.getChildren();
        int n = (int)(Math.random() * 9 + 0);
        if ((anchorTwo.getChildren().get(n).getId() != null)){
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
                }
            }
        }
    }
