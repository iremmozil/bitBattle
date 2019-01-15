package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class LeaderboardController extends Main{

    // Bind variables to related FXML entities
    @FXML
    private ListView allTimeList;

    @FXML
    private ListView Last7DaysList;

    // Leaderboard initializer for allTimeList and LAst7DaysList
    public void initialize() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {

            System.out.println("GET Request Handling");

            HttpGet request = new HttpGet("http://localhost:8080/leaderboard");
            showLeaderBoard(httpClient, request);
            ObservableList<String> obsList = null;
            allTimeList.setItems(obsList);

        } catch (Exception ex) {

            System.out.println(ex);

        } finally {
            httpClient.close();
        }

        httpClient = HttpClientBuilder.create().build();

        try {

            System.out.println("GET Request Handling");

            HttpGet request = new HttpGet("http://localhost:8080/leaderboard/last_week");
            showLeaderBoard(httpClient, request);
            ObservableList<String> obsList = null;
            Last7DaysList.setItems(obsList);

        } catch (Exception ex) {

            System.out.println(ex);

        } finally {
            httpClient.close();
        }
    }

    private void showLeaderBoard(CloseableHttpClient httpClient, HttpGet request) throws IOException {
        request.addHeader("content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONArray jsonGames = new JSONArray(responseString);

        ObservableList<String> obsList = FXCollections.observableArrayList();
        String allTimeListItem;
        for (int i=0; i<jsonGames.length(); i++) {
            JSONObject game = jsonGames.getJSONObject(i);

            HttpGet playerReq = new HttpGet("http://localhost:8080/player/id/" + game.getInt("playerId"));
            request.addHeader("content-type", "application/json");
            HttpResponse playerResponse = httpClient.execute(playerReq);

            HttpEntity entity = playerResponse.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");

            allTimeListItem = String.format("%d      %s      %s      %s", i+1, responseString, game.get("score"), String.valueOf(game.get("gameTime")).substring(0,10));
            System.out.println(allTimeListItem);
            obsList.add(allTimeListItem);

        }
    }

    // "Back" button push handler
    public void backHbuttonPushed(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("design/home.fxml"));
        setScene( event, home);
    }

}
