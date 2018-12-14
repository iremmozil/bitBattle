package edu.metu.ceng453.bitBattle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

import static java.awt.Event.LEFT;
import static java.awt.Event.RIGHT;


public class LevelOneController extends Controller{

    public EventHandler<KeyEvent> keyListener = event -> {
        if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
            spaceship.relocate(132,410);
        }
        else if(event.getCode() == KeyCode.SPACE) {
            System.out.print("Hello");

        }
        event.consume();
    };

}
