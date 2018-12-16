package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;

import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.activation.DataHandler;
import javax.xml.ws.AsyncHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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
                Parent home = FXMLLoader.load(getClass().getResource("./home.fxml"));
                Scene sceneHome = new Scene(home);
                sceneHome.getRoot().requestFocus();
                Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
                window.setScene(sceneHome);
                window.show();

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

        Parent register = FXMLLoader.load(getClass().getResource("./register.fxml"));
        Scene sceneRegister = new Scene(register);
        sceneRegister.getRoot().requestFocus();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(sceneRegister);
        window.show();
    }
}
