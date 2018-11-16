# bitBattle

bitBattle is a simple two-dimensional game. The game's aim is shooting the aliens in order to save the world. In order to achieve this, 
a player has to pass all four levels. 
In the first three levels, the player has to kill all aliens by him/herself,  with a spaceship shaped like a square! 
At the fourth level, the player has an opponent. This time they have to kill aliens together. 
The player who killed more alien than the other gets more points. 
At the end of the game, the player enters the leaderboard through his/her high score.


## Project Structure

In the src/main/java/edu/metu/ceng453/bitBattle folder, there are 6 .java files. 

* In the Player.java and Leaderboard.java files we simply implemented constructors and the getters and the setters and we bind the local variables to our database variables.
* In PlayerRepository.java and LeaderboardRepository.java files, we implemented one method each in order to get **player** and **leaderboard** object with a given parameter.
* In PlayerController.java and LeaderboardController.java files, we implemented the HTTP mapping methods that are **PUT, POST, GET, DELETE**.
    * *GET: /players* : this method returns all players.
    * *GET: /player/{id}*: this method returns a single player from given id.
    * *GET: /leaderboard*:  this method returns all leaderboard.
    * *GET: /leaderboard/last_week*: This method returns leaderboard of last X days.
    * *POST: /register*: this method saves the given player(playername, password, highScore) to the database with a unique id and playername, a highscore value 0 and hashed password.
    * *POST: /log_in*: this method checks the password entered is correct or not. If not returns an exception.
    * *POST: /leaderboard*: this method adds gameTime, playerId and score to the leaderboard with a unique id.
    * *PUT: /change_password*: this method changes the player's password to given password.
    * *DELETE: /delete_player/{id}*: this method deletes the player with the given id from the database.
    * *DELETE: /delete_leaderboard/{id}*: this method deletes a "battle score" with a score and playerId by given id.
    * *DELETE: /delete_leaderboard*:  this method clears the leaderboard.
    * In addtion to all methods above, there is myHash method to hash a given password in the playerControl.java file.
    
In the src/main/resources folder, there is a file named application.properties. In this file, we specify the database connection properties.

## Database Structure

In the database, we have 2 tables named Player and Leaderboard.

Player includes:

    id
    playername
    password
    highscore
    
Leaderboard includes:

    id
    score
    game_time
    player_id
    
*player_id is a foreign key referenced by Player table.

