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

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerTest {

    @Autowired
    private WebApplicationContext wac;

    ArrayList<Player> players = new ArrayList<>();

    @Before // Here, test variables are defined and context setup is done.
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Player player1 = new Player("player1","asdfgh",0);
        Player player2 = new Player("player2","qwerty",0);
        Player player4 = new Player("player4","zxcvbn",0);

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player4);

        players.add(player1);
        players.add(player2);
        players.add(player4);

    }

    private MockMvc mvc;

    @Autowired  // Object mapper is for objects to be mapped to JSON paths.
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    // Unit tests for PlayerController class
    @Test
    public void getPlayerByName() throws Exception {

        mvc.perform(get("/player/player1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playername", is("player1")));
    }

    @Test
    public void all() throws Exception {

        mvc.perform(get("/players")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playername", is("player1")))
                .andExpect(jsonPath("$[1].playername", is("player2")));

    }

    @Test
    public void newPlayer() throws Exception {
        Player player3 = new Player("player3","dflşgkd",0);

        mvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player3)))
                .andExpect(status().isOk());
    }

    @Test
    public void replaceHighScore() throws Exception {

        mvc.perform(put("/player/"+players.get(2).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(100)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePlayer() throws Exception {
        mvc.perform(delete("/player/"+players.get(2).getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}