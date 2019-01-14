package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TriangleAlien implements Alien {
    //variables
    private int health = 1;//TRIANGLE_ALIEN_HEALTH;
    private Node node;

    //getters and setters
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }


    //constructor
    public TriangleAlien(Node n) {
        this.node = n;
    }

    @Override
    public void fire() {
        Circle b = new Circle(alienBulletRadious);
        b.setStroke(Color.BLACK);
        b.setStrokeWidth(0.0);
        b.setFill(Color.valueOf("99daff"));

        double x = b.getCenterX();
        double y = b.getCenterY();
        PathTransition tt = new PathTransition(Duration.seconds(3), new Line(x,y, x ,650),b);
        tt.play();

    }

    @Override
    public void move() {
        PathTransition tt = new PathTransition(Duration.seconds(5), new Line(),this.node);
        tt.setPath(new Rectangle(20,15));
        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();

    }

    @Override
    public void alienShotDown(AnchorPane anchor, Node bullet) {
        if (bullet.getBoundsInParent().intersects(this.node.getBoundsInParent())) {
            this.setHealth(getHealth()-1);
        }

        if (getHealth() == 0){
            anchor.getChildren().removeAll(this.node);
        }
    }
}
