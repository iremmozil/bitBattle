package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;



public class LevelOneController extends Controller{

    @FXML
    ImageView spaceship;

    @FXML
    AnchorPane anchorOne;

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

    Double alienMove = 48.0;



     public void animateLogo() {

         PathTransition tt =
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

         tt.setPath(new Circle(20));
         tt2.setPath(new Circle(20));
         tt3.setPath(new Circle(20));
         tt4.setPath(new Circle(20));
         tt5.setPath(new Circle(20));
         tt6.setPath(new Circle(20));
         tt7.setPath(new Circle(20));
         tt8.setPath(new Circle(20));
         tt9.setPath(new Circle(20));
         tt10.setPath(new Circle(20));
         tt11.setPath(new Circle(20));

        tt.setCycleCount( Timeline.INDEFINITE );
         tt2.setCycleCount( Timeline.INDEFINITE );
         tt3.setCycleCount( Timeline.INDEFINITE );
         tt4.setCycleCount( Timeline.INDEFINITE );
         tt5.setCycleCount( Timeline.INDEFINITE );
         tt6.setCycleCount( Timeline.INDEFINITE );
         tt7.setCycleCount( Timeline.INDEFINITE );
         tt8.setCycleCount( Timeline.INDEFINITE );
         tt9.setCycleCount( Timeline.INDEFINITE );
         tt10.setCycleCount( Timeline.INDEFINITE );
         tt11.setCycleCount( Timeline.INDEFINITE );

        tt.play();
         tt2.play();
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

    @FXML
    private void setKeyListener(KeyEvent event){
        animateLogo();
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
            Circle b = new Circle(5.05);
            b.setStroke(Color.BLACK);
            b.setStrokeWidth(0.0);
            b.setFill(Color.valueOf("99daff"));
            b.setCenterX(spaceship.getLayoutX() + 35.0);
            b.setCenterY(spaceship.getLayoutY());
            anchorOne.getChildren().add(b);
            /*
            while(b.getCenterY() > 0){
                double x = b.getCenterX();
                double y = b.getCenterY();
                y = y - 6;
                b.relocate(x,y);
            }*/

        }
        event.consume();
    }

}
