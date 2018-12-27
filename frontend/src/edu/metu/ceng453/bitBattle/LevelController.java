package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class LevelController extends Main {

    //Variables

    private Boolean isFinished = false;
    private int score = Main.fromMaingetScore();
    private int health = 3;

    //Getters and setters
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }

    public boolean isAlienShot(Node o, ImageView alien, AnchorPane anchor) {
        if (anchor.getChildren().contains(alien)) {
            if(o.getBoundsInParent().intersects(alien.getBoundsInParent())){
                anchor.getChildren().remove(o);
                anchor.getChildren().remove(alien);
                score += 5;
                return true;
            }
        }
        return false;
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

    public double goDirection( double x, String direction ){
        if( direction.equals("RIGHT")){
            if (x < 480){
                x = x + 6;
            }
        }
        else if(direction.equals("LEFT")){
            if( x > -20){
                x = x - 6;
            }
        }

        return x;
    }




}
