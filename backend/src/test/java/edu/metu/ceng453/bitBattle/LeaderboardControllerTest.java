package edu.metu.ceng453.bitBattle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class LeaderboardControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private ArrayList<Leaderboard> games = new ArrayList<>();

    @Before  // Here, test variables are defined and context setup is done.
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date game1Date = sdf.parse("13/12/2018");
        Date game2Date = sdf.parse("21/12/2012");
        Date game3Date = sdf.parse("10/12/2018");

        Leaderboard game1 = new Leaderboard(1,10,game1Date);
        Leaderboard game2 = new Leaderboard(2,20,game2Date);
        Leaderboard game3 = new Leaderboard(3,30,game3Date);

        leaderboardRepository.save(game1);
        leaderboardRepository.save(game2);
        leaderboardRepository.save(game3);

        games.add(game1);
        games.add(game2);
        games.add(game3);
    }

    private MockMvc mvc;

    @Autowired // Object mapper is for objects to be mapped to JSON paths.
    private ObjectMapper objectMapper;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    // Unit tests for LeaderboardController class
    @Test
    public void getLeaderboard() throws Exception {

        mvc.perform(get("/leaderboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(games.get(2).getId())))
                .andExpect(jsonPath("$[1].id", is(games.get(1).getId())))
                .andExpect(jsonPath("$[2].id", is(games.get(0).getId())));
    }

    @Test
    public void getLeaderboardLastWeek() throws Exception {

        mvc.perform(get("/leaderboard/last_week")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(games.get(2).getId())))
                .andExpect(jsonPath("$[1].id", is(games.get(0).getId())));
    }

    @Test
    public void newLeaderboard() throws Exception {
        Leaderboard game4 = new Leaderboard(4,40,new Date());
        games.add(game4);
        mvc.perform(post("/leaderboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(game4)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteGame() throws Exception {
        mvc.perform(delete("/leaderboard/" + games.get(3).getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAll() throws Exception {
        mvc.perform(delete("/leaderboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}