package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public abstract class Alien {

    protected int health = 0;
    protected Node node;
    protected double alienBulletRadious = 5.05;
    protected double alienHalfWidth = 22.0;
    protected double alienHeight = 40.0;
    boolean isShot = false;

    public void fire(AnchorPane anchor){
        Circle b = new Circle(alienBulletRadious);
        b.setStrokeWidth(0.0);
        b.setFill(Color.valueOf("99daff"));
        b.setCenterX(this.node.getLayoutX() + alienHalfWidth + this.node.getTranslateX());
        b.setCenterY(this.node.getLayoutY() + alienHeight + this.node.getTranslateY());
        anchor.getChildren().add(b);
        double x = b.getCenterX();
        double y = b.getCenterY();
        PathTransition tt = new PathTransition(Duration.seconds(3), new Line(x,y, x ,650),b);
        tt.play();
    }

    boolean isShotDown(AnchorPane anchor, Node bullet) {
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

    public int getHealth() {
        return this.health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    abstract void move();
}

