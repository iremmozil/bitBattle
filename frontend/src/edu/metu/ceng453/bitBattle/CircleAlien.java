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

public class CircleAlien extends Alien{


    public CircleAlien(Node n) {
        this.node = n;
        this.health = 2;
    }

    @Override
    public void move() {
        PathTransition tt = new PathTransition(Duration.seconds(5), new Line(),this.node);
        tt.setPath(new Rectangle(20,15));
        tt.setCycleCount( Timeline.INDEFINITE );
        tt.play();

    }

}
