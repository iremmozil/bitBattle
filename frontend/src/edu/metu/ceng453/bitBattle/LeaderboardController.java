package edu.metu.ceng453.bitBattle;

// Import necessary libraries
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
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
    private ListView<String> allTimeList;

    @FXML
    private ListView<String> Last7DaysList;

    // Leaderboard initializer for allTimeList and LAst7DaysList
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private ObservableList<String> obsListAllTime = FXCollections.observableArrayList();
    private ObservableList<String> obsListSevenDays = FXCollections.observableArrayList();

    public void initialize() throws IOException {

        try {
            System.out.println("GET Request Handling");
            HttpGet request = new HttpGet(protocol + host + port + leaderboardPath);
            String allTimeListItem = "allTimeListItem";
            addToTable(request,allTimeListItem);
            allTimeList.setItems(obsListAllTime);

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            httpClient.close();
        }
        httpClient = HttpClientBuilder.create().build();
        try {
            System.out.println("GET Request Handling");
            HttpGet request = new HttpGet(protocol + host + port + leaderboardPath+ "last_week");
            String Last7DaysListItem = "Last7DaysListItem";
            addToTable(request, Last7DaysListItem);
            Last7DaysList.setItems(obsListSevenDays);

        } catch (Exception ex) {

            System.out.println(ex);

        } finally {
            httpClient.close();
        }
    }


    private void addToTable(HttpGet request, String ListItem) throws IOException{
        request.addHeader("content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONArray jsonGames = new JSONArray(responseString);
        for (int i=0; i<jsonGames.length(); i++) {
            JSONObject game = jsonGames.getJSONObject(i);

            HttpGet playerReq = new HttpGet(protocol + host + port + playerPath+ "id/" + game.getInt("playerId"));
            request.addHeader("content-type", "application/json");
            HttpResponse playerResponse = httpClient.execute(playerReq);

            HttpEntity entity = playerResponse.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            String item;
            item = String.format("%2d %12s %5s %12s", i+1, responseString, game.get("score"), String.valueOf(game.get("gameTime")).substring(0,10));
            System.out.println(item);
            if (ListItem.equals("allTimeListItem")){
                obsListAllTime.add(item);
            }
            else if (ListItem.equals("Last7DaysListItem")){
                obsListSevenDays.add(item);
            }
        }
    }

    // "Back" button push handler
    public void backHbuttonPushed(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("design/home.fxml"));
        setScene( event, home);
    }

}
