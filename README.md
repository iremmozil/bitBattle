## **WELCOME TO BITBATTLE!**

If you played bitBattle before, you can directly sign in and continue to have fun!

If you are new to bitBattle, you should register by clicking to "Register Now!" button at the bottom of the page.

You should enter a valid player name and password. Please use English characters in your player name and password, choose player names no longer than 100 characters and password no longer than 255 characters to be able to register. After filling in the fields, click to "Register". This will direct you to Sign in page.
(If accidentally pushed to "Register Now!" button, you can go back  to Sign In page with the "Back" button.)

Now, you are a member of bitBattle! Sign in and enjoy!


You are in Home page. Click to "New Game" and play bitBattle, or see how you and your counterparts did in bitBattle from "Leaderboard" button.

In leaderboard, you can see the players and their bitBattle scores. The table on left contains all games from the invention of computer, and right table contains games of last 7 days.

## **HOW TO ROLL IN BITBATTLE:**

bitBattle contains 4 levels. Level 4 is under construction, so let's look how to play bitBattle with 3 levels.

After clicking to "New Game" button, you encounter with Level 1. Game directly starts. So get prepared!
You are the coolest, colorful spaceship, at the bottom of the page. You can move with left and right arrow keys to avoid from the shots of aliens, and shoot to enemy aliens with space key. You have 4 health points for each level.

There is 1 enemy alien type in Level 1, triangle alien. It moves in a circular path, and shoots randomly. You can kill the triangle alien with one shoot, and you will gain 5 points per 1 triangle alien. When you kill all the triangle aliens, you will pass to Level 2. Each shoot that reaches to you will decrease your health by 1 point.

There are 2 enemy alien types in Level 2, triangle alien and circle alien. Triangle alien has the same effects on you. However, circle alien is a more advanced alien. It also moves in a circular path, and shoots randomly. You can kill the circle alien with one shoot, and you will gain 10 points per 1 circle alien. When you kill all aliens, you will pass to Level 3. Each shoot that reaches to you will decrease your health by 1 point.

Your final score is the last score value when you finish the game, or you are killed and your game is over.


## Project Structure
In this project there is two main folders: frontend and backend.
Backend includes the database operations. 
In the src/main/java/edu/metu/ceng453/bitBattle/backend folder, there are 6 .java files. 

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
