package edu.metu.ceng453.bitBattle.alien;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Alien {

    int health = 0;
    int point = 0;
    Node node;
    private double alienBulletRadious = 5.05;
    private double alienHalfWidth = 22.0;
    private double alienHeight = 40.0;
    private boolean isShot = false;

    // this alien shoots
    public void fire(AnchorPane anchor){
        Circle b = new Circle(alienBulletRadious);
        b.setStrokeWidth(0.0);
        b.setFill(Color.valueOf("99daff"));         //color blue
        b.setCenterX(this.node.getLayoutX() + alienHalfWidth + this.node.getTranslateX());
        b.setCenterY(this.node.getLayoutY() + alienHeight + this.node.getTranslateY());
        anchor.getChildren().add(b);
        double x = b.getCenterX();
        double y = b.getCenterY();
        PathTransition tt = new PathTransition(Duration.seconds(3), new Line(x,y, x ,650),b);
        tt.play();
    }

    // Check if this alien is shot down, if it is return true and remove bullet from anchorPane else return false
    // Check aliens's health if it is zero remove the alien from anchorPane
    public boolean isShotDown(AnchorPane anchor, Node bullet) {
        isShot = false;
        if (bullet.getBoundsInParent().intersects(this.node.getBoundsInParent())) {
            this.setHealth(this.getHealth()-1);
            anchor.getChildren().remove(bullet);
            isShot = true;
        }
        if (this.getHealth() == 0){
            anchor.getChildren().remove(this.node);
        }
        return isShot;
    }

    //health's getter
    public int getHealth() {
        return this.health;
    }

    //health's setter
    private void setHealth(int health) {
        this.health = health;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    //Aliens animate function
    public void move() {
        PathTransition tt = new PathTransition(Duration.seconds(5), new Line(),this.node);
        tt.setPath(new Rectangle(20,15));
        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();

    }

}

