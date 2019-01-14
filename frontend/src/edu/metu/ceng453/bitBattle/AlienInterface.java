package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public interface AlienInterface {


    public void fire(AnchorPane anchor);

    public void isShotDown(AnchorPane anchor, Node bullet);

    public int getHealth();

    public void setHealth(int health);

    public void move();
}
