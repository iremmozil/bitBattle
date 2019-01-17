## **WELCOME TO BITBATTLE!**

If you played bitBattle before, you can directly sign in and continue to have fun!

If you are new to bitBattle, you should register by clicking to "Register Now!" button at the bottom of the page.

You should enter a valid player name and password. Please use English characters in your player name and password, choose player names no longer than 100 characters and password no longer than 255 characters to be able to register. After filling in the fields, click to "Register". This will direct you to Sign in page.
(If accidentally pushed to "Register Now!" button, you can go back  to Sign In page with the "Back" button.)

Now, you are a member of bitBattle! Sign in and enjoy!


You are in Home page. Click to "New Game" and play bitBattle, or see how you and your counterparts did in bitBattle from "Leaderboard" button.

In leaderboard, you can see the players and their bitBattle scores. The table on left contains all games from the invention of computer, and right table contains games of last 7 days.

## **HOW TO ROLL IN BITBATTLE:**

bitBattle contains 4 levels: the first, second and third levels are single player, fourth level is multiplayer. Let's look how to play bitBattle.

After clicking to "New Game" button, you encounter with Level 1. Game directly starts. So get prepared!
You are the coolest, colorful spaceship, at the bottom of the page. You can move with left and right arrow keys to avoid from the shots of aliens, and shoot to enemy aliens with space key. You have 3 health points for each level.

There is 1 enemy alien type in Level 1, triangle alien. It moves in a rectangular path, and alien that shoots chosen randomly. You can kill the triangle alien with one shot, and you will gain 5 points per 1 triangle alien. When you kill all the triangle aliens, you will pass to Level 2. In order to pass to level 2, you should press "N".  The reason that player should press "N" to pass level 2 is being intuitive. Each alien shot that reaches to you will decrease your health by 1 point.

There are 2 enemy alien types in Level 2, triangle alien and circle alien. Triangle alien has the same effects on you. However, circle alien is a more advanced alien. It also moves in a rectangular path, and each circle alien shoots randomly. You can kill the circle alien with two shot, and you will gain 10 points per 1 circle alien. When you kill all aliens, you will pass to Level 3. In order to pass to level 3, you should press "N". Each alien shot that reaches to you will decrease your health by 1 point.

There are 3 enemy alien types in level 3, triangle alien, circle alien and parallelogram alien. Triangle alien and circle alien has the same effects on you. However, parallelogram alien is more advanced alien than the others. It also moves in a rectangular path, and each parallelogram alien shoots randomly. You can kill the parallelogram alien with three shot, and you will gain 15 points per 1 parallelogram alien. When you kill all aliens, you will pass to Level 4. In order to pass to level 4, you should press "N". Each alien shot that reaches to you will decrease your health by 1 point.

In level 4, first you have to wait for an opponent. As soon as the server finds you a oppenent you will start the level four. There are 3 enemy alien types in level 4, triangle alien, circle alien and parallelogram alien. All aliens has the same affects on you. The player that "kills" an alien gets the alien's points. In order to get point a player should kill more alien than opponent. When opponent is dead, player wins the game and collects the points. After finishing the level, player can go home page and see the score of his/her. 

If you are killed and your game is over. However, do not worry you can go home by pressing go home button and play bitBattle again!


## Project Structure
In this project there is three main folders: frontend, backend and multiplayer server.
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

Frontend includes the GUI. In frontend there are design, alien, level folders and java files. In the design folder, there are fxml and png files. The design of the pages of the GUI is mainly implemented in fxml files. In the alien folder, there are alien interface, alien abstract class, alien Factory class, triangle alien class, circle alien class and parallelogram alien class. They includes alien's constructions and basic methods that belongs to the aliens. In the level folder there are abstract level controller class and each level's controller classes. Basicly, the methods that belongs to the levels are implemented in those classes. SigninController, registerController, leaderboardController includes the methods related to those pages. 

The main GUI structure is stated in:
tp-repo/frontend/gui-images/GUI.png

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
