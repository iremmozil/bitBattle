package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class CircleAlien extends Alien{


    public CircleAlien(Node n) {
        this.health = 2;
        this.node = n;
    }

    @Override
    public void move() {

    }

}
