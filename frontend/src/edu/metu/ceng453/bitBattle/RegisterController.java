package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class RegisterController extends Main{

    // Bind variables to related FXML entities
    @FXML
    private TextField pNameText;

    @FXML
    private TextField pPasswordText;

    @FXML
    private TextField cPasswordText;

    @FXML
    private Label errorMes;

    // "Register" button push handler
    public void rbuttonPushed(ActionEvent event) throws IOException {
        String playername = pNameText.getText();
        String password = pPasswordText.getText();
        String conf_password = cPasswordText.getText();

        if (playername.length() <= 100 && !password.isEmpty() && password.length() <= 255 && conf_password.equals(password)) {
            JSONObject json = new JSONObject();
            json.put("playername",playername);
            json.put("id", 0);
            json.put("password",password);
            json.put("highscore",0);

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            try {

                HttpPost request = new HttpPost("http://localhost:8080/player");
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("content-type", "application/json");

                request.setEntity(params);
                httpClient.execute(request);
                System.out.println("POST Request Handling");

            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                httpClient.close();
            }

            Parent signIn;
            signIn = FXMLLoader.load(getClass().getResource("design/signin.fxml"));
            Scene sceneSignIn = new Scene(signIn);
            sceneSignIn.getRoot().requestFocus();
            Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
            window.setScene(sceneSignIn);
            window.show();

        }

        else if (password.isEmpty() || conf_password.isEmpty()) errorMes.setText("Fill in all fields!");
        else if (password.length() > 255) errorMes.setText("Long password! Enter a password shorter than 255 characters.");
        else if (playername.length() > 100) errorMes.setText("Long player name! Enter a player name shorter than 100 characters.");
        else errorMes.setText("Passwords don't match!");
    }

    // "Back" button push handler
    public void backSIbuttonPushed(ActionEvent event) throws IOException {
        Parent signIn = FXMLLoader.load(getClass().getResource("design/signin.fxml"));
        Scene sceneSignIn = new Scene(signIn);
        sceneSignIn.getRoot().requestFocus();
        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
        window.setScene(sceneSignIn);
        window.show();
    }
}
