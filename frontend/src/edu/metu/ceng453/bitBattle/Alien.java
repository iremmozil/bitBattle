package edu.metu.ceng453.bitBattle;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public interface Alien {

    int health = 0;
    public double alienBulletRadious = 5.05;

    public void fire();
    public void move();
    public void alienShotDown(AnchorPane anchor, Node bullet);
}

