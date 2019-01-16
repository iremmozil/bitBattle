package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private int maxPasswordLength = 255;
    private int maxPlayernameLength = 100;

    // "Register" button push handler
    public void rbuttonPushed(ActionEvent event) throws IOException {
        String playername = pNameText.getText();
        String password = pPasswordText.getText();
        String conf_password = cPasswordText.getText();

        if (playername.length() <= maxPlayernameLength && !password.isEmpty() && password.length() <= maxPasswordLength && conf_password.equals(password)) {
            JSONObject json = new JSONObject();
            json.put("playername",playername);
            json.put("id", 0);
            json.put("password",password);
            json.put("highscore",0);

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

                HttpPost request = new HttpPost(protocol + host + port + playerPath);
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("content-type", "application/json");

                request.setEntity(params);
                httpClient.execute(request);
                System.out.println("POST Request Handling");

            } catch (Exception ex) {
                System.out.println(ex);
            }

            Parent signIn;
            signIn = FXMLLoader.load(getClass().getResource("design/signin.fxml"));
            setScene(event, signIn);

        }

        else if (password.isEmpty() || conf_password.isEmpty()) errorMes.setText("Fill in all fields!");
        else if (password.length() > maxPasswordLength) errorMes.setText("Long password! Enter a password shorter than 255 characters.");
        else if (playername.length() > maxPlayernameLength) errorMes.setText("Long player name! Enter a player name shorter than 100 characters.");
        else errorMes.setText("Passwords don't match!");
    }

    // "Back" button push handler
    public void backSIbuttonPushed(ActionEvent event) throws IOException {
        Parent signIn = FXMLLoader.load(getClass().getResource("design/signin.fxml"));
        setScene(event, signIn);
    }
}
