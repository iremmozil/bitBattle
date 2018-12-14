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

    @FXML
    HBox ship;
    ImageView spaceship;

    public void setKeyListener(KeyEvent event){
        if (event.getCode() == KeyCode.RIGHT){
            spaceship.setSize(45,85);
        }
        else if (event.getCode() == KeyCode.LEFT){

        }
        else if (event.getCode() == KeyCode.SPACE){

        }
        event.consume();
    }
        private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                    ship.setAlignment(Pos.BASELINE_RIGHT);
                }
                else if(event.getCode() == KeyCode.SPACE) {

                }
                event.consume();
            }
        };

}
