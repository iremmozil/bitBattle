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
    private Boolean isShot = false;
    int circle1Health = 2;
    int circle2Health = 2;
    int circle3Health = 2;
    int circle4Health = 2;

    public boolean circle1HitOnce = false;
    public boolean circle2HitOnce = false;
    public boolean circle3HitOnce = false;
    public boolean circle4HitOnce = false;

    //Getters and setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlienShot(Node o, Node aliens[], AnchorPane anchor) {
        isShot =  false;
        for (int i = 0; i < aliens.length; i++) {
            if (anchor.getChildren().contains(aliens[i])) {
                if (o.getBoundsInParent().intersects(aliens[i].getBoundsInParent())) {
                    if (aliens[i].getId().equals("triangle") ){
                        removeAlien(anchor, o, aliens[i]);
                        removeAlienFromArray(aliens, i);
                        setScore(getScore() + 5);
                    }
                    else if (aliens[i].getId().equals("circle")){
                        if (circle1HitOnce){
                            System.out.println("e vuruyo");
                            removeAlien(anchor, o, aliens[i]);
                            removeAlienFromArray(aliens, i);
                            setScore(getScore() + 10);
                        }
                        else {
                            circle1HitOnce = true;
                        }
                    }
                    isShot = true;
                }
            }
        }
        return isShot;
    }

    private void removeAlien(AnchorPane anchor, Node o, Node alien){
        anchor.getChildren().remove(o);
        anchor.getChildren().remove(alien);
    }
    private void removeAlienFromArray(Node aliens[], int i ){
        for (int j = i; j< aliens.length -2; j ++){
            aliens[j] = aliens[j+1];
        }
        aliens[aliens.length-1] = null;
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
