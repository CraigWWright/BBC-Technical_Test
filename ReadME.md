# Craig Wright - BBC Technical Test 2024

This is my submission for the BBC technical test to create a blackjack simulation.
My submission is a fully interactive text based game for up to 4 players.

# How to Run the Program

1. Ensure java is installed on your machine by typing in the cmd line.
	`` java -version ``
	Java can be downloaded here: 	https://www.oracle.com/java/technologies/downloads/?er=221886
2. Ensure Maven is installed by typing in the cmd line.
	``mvn -version``
	Maven can be downloaded here: https://maven.apache.org/download.cgi
3. Navigate to the project directory
4. Run ``mvn clean package``
5. Run the project by typing 
	``java -jar target/bbc-segs-2024-blackjack-game-1.0-SNAPSHOT.jar``

Alternatively you can open the project in an IDE and run the Main.java class.

## Blackjack

Once the game is running you will be asked how many players are playing, followed by being asked for the name of each player. 
Following this the game is now ready to play. You can either jump right in a game by pressing `P` or have a look at changing some rules by pressing `R`. When you are finished press `Q` to quit.

## Surrender Rule

When the surrender rule is turned on players are given the option to surrender after they have been dealt their first 2 cards. By surrendering they will receive back half of their bet and be out for the remainder of the round.

## Double Down

When the double down rule is turned on players are given the option to double down after they have been dealt their first 2 cards. By doubling down the player will double their bet for that round. Only players who can afford to double down are given the option.

## Unit Testing

Unit testing was carried out to ensure the game works as it is expected to. The unit tests can be found in the `test` folder within the `src` folder.

## Input Testing

As the application uses the scanner to receive user input, all inputs have been thoroughly tested with normal, extreme and exceptional data to ensure the application can handle incorrect inputs.  
