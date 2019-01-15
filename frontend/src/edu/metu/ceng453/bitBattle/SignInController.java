package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class SignInController extends Main{

    // Bind variables to related FXML entities
    @FXML
    Button sbutton;

    @FXML
    Button rNowbutton;

    @FXML
    TextField pNameSignInText;

    @FXML
    PasswordField pPasswordSignInText;

    @FXML
    Label errorMesSignIn;


    // "Sign In" button push handler
    public void sbuttonPushed(ActionEvent event) throws IOException {
        String playername = pNameSignInText.getText();
        String enteredPassword = pPasswordSignInText.getText();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {

            System.out.println("GET Request Handling");

            HttpGet request = new HttpGet("http://localhost:8080/player/" + playername);
            request.addHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");

            JSONObject player = new JSONObject(responseString);

            if(player.getString("password").equals(enteredPassword)) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Player currentPlayer = gson.fromJson(String.valueOf(player), Player.class);

                Main.setCurrentPlayer(currentPlayer);
                Parent home = FXMLLoader.load(getClass().getResource("design/home.fxml"));
                setScene(event, home);

            }
            else {
                errorMesSignIn.setText("Invalid password!");
            }

        } catch (Exception ex) {
            errorMesSignIn.setText("Invalid player name or password!");
            System.out.println(ex);

        } finally {
            httpClient.close();
        }
    }

    // "Register Now!" button push handler
    public void rNowbuttonPushed(ActionEvent event) throws IOException{

        Parent register = FXMLLoader.load(getClass().getResource("design/register.fxml"));
        setScene(event, register);
    }
}
