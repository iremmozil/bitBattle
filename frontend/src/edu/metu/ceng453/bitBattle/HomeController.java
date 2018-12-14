package edu.metu.ceng453.bitBattle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.IOException;

public class HomeController {
    @FXML
    Button newButton;

    public void newButtonPushed(ActionEvent event) throws IOException {
        Parent levelOne = FXMLLoader.load(getClass().getResource("./levelOne.fxml"));
        Scene sceneOne = new Scene(levelOne);
        sceneOne.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneOne);
        window.show();
    }
}
