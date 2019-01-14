package edu.metu.ceng453.bitBattle;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

public class LevelController extends Main {

    //Variables

    private Boolean isFinished = false;
    private int score = Main.fromMaingetScore();
    private int health = 3;
    private int windowLeftEdge = -20;
    private int windowRightEdge = 480;
    private int spaceShipMoveSize = 6;
    private double bulletRadious = 5.05;

    private Boolean isShot = false;

    ArrayList<Alien> aliens = new ArrayList<Alien>();
    ArrayList<Node> nodeAliens = new ArrayList<Node>();



    //Getters and setters
    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }

    //get aliens to an array and create aliens
    void aliensToArray(AnchorPane anchor){
        //when new level is started empty the array
        AlienFactory alienFactory = new AlienFactory();

        for (Node node: anchor.getChildren()){
            String tag = node.getId();
            if (tag.equals("triangle") || tag.equals("circle") || tag.equals("parallelogram")){
                nodeAliens.add(node);
                aliens.add(alienFactory.createAlien(tag,node));
            }
        }
    }

    void animateAliens(){
        for (Alien a: aliens){
            a.move();
        }
    }


    void alienShot(AnchorPane anchor){
        for(Node o: anchor.getChildren()){
            if (o.getId() == "bullet"){
                if (isAlienShot(o, aliens, anchor)) break;
            }
        }
    }


    // if spaceship could shoot one of the aliens if yes return true else return false
    boolean isAlienShot(Node o, ArrayList<Alien> aliens, AnchorPane anchor) {
        isShot =  false;
        for (Alien alien: aliens){
            if (anchor.getChildren().contains(alien)){
                isShot = alien.isShotDown(anchor,o);
            }
        }
        return isShot;
    }

    //remove alien from anchorPane
    private void removeAlien(AnchorPane anchor, Node o, Node alien){
        anchor.getChildren().remove(o);
        anchor.getChildren().remove(alien);
    }

    //When user presses SPACE spaceship should fire!
    void fire(AnchorPane anchor, ImageView spaceship){
        Circle bullet = new Circle(bulletRadious);
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

    //When user presses RIGHT or LEFT key calculate spaceship's new location
    double goDirection(double x, String direction){
        if( direction.equals("RIGHT")){
            if (x < windowRightEdge){
                x = x + spaceShipMoveSize;
            }
        }
        else if(direction.equals("LEFT")){
            if( x > windowLeftEdge){
                x = x - spaceShipMoveSize;
            }
        }
        return x;
    }




}
