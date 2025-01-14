package org.example;

import java.util.*;
import java.util.List;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private int gameCounter = 0;
    private int decks = 1;
    private boolean doubleDown = false;
    private boolean surrender = false;
    private static List<Card> cards;
    private final Deck deck = new Deck(decks);
    private final ArrayList<Player> playerList = new ArrayList<>();


    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {

        setUpPlayers();
        displayMessage("You have been given £100 to start. Good luck!");

        // this segment tells the player how much money they have after a round. It also removes players who have ran out of money
        while (true) {
            Iterator<Player> iterator = playerList.iterator();
            // only runs if a game has already been played
            // it is not necessary for players to be told how much they have before any games have been played as they have already been told that
            if (gameCounter > 0) {
                // iterator is used to safely remove a player from the list without causing any issues
                while (iterator.hasNext()) {
                    Player player = iterator.next();
                    if (player.getBalance() <= 0) {
                        // removes player if they no or negative money
                        displayMessage("Unlucky " + ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have ran out of money and have been removed from the game.");
                        iterator.remove();
                    } else {
                        // tells remaining players how much money they have
                        displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have £" + player.getBalance());
                    }
                }
            }

            if (playerList.isEmpty()) {
                // if all players have run out of money then the game ends
                return;
            } else {
                // asks player for their input - input is then validated
                displayMessage("To play a game of Blackjack enter 'P', or to change the rules press 'R', or to quit enter 'Q'.");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("P")) {
                    gameCounter++;
                    playBlackjack();
                } else if (input.equalsIgnoreCase("R")) {
                    displayRules();
                } else if (input.equalsIgnoreCase("Q")) {
                    quitMessages();
                    scanner.close();
                    return;
                } else {
                    displayMessage(ConsoleColors.RED + "Input not recognised. " + ConsoleColors.RESET + "Please ensure you entered either 'P', 'R' or 'Q'.");
                }
            }

        }
    }


    public void setUpPlayers() {

        // uses the integer validation method to receive a valid integer input
        int noOfPlayers = getValidIntegerInput("How many people are playing? Enter a number between 1 and 4.", 1, 4);
        // loops through all the players and receives their name
        for (int i = 0; i < noOfPlayers; i++) {
            displayMessage("Enter the name of player " + (i + 1) + ".");
            String playerName = scanner.nextLine();
            // creates a new player using the default player constructor
            Player newPlayer = new Player(playerName);
            // adds player to list of players
            playerList.add(newPlayer);
        }

    }

    public void playBlackjack() {


        // this segment receives all players bets
        for (Player player : playerList) {
            // if a player went bust in the previous round this resets it
            player.setBust(false);

            // gets a valid bet
            double bet = getValidDoubleInput(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " please enter your bet", 0, player.getBalance());
            player.setBet(bet);
        }

        // a new deck is created at the start of each game this ensures the number of decks being used is correct to the player specification
        deck.createDeck(decks);
        deck.shuffle();

        ArrayList<Card> dealerCards = new ArrayList<>();
        int dealerHand;

        // deals out 2 cards to each player
        for (Player player : playerList) {
            // creates a local list of the cards dealt to the player
            ArrayList<Card> playerCards = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                // removes a card from the top of the deck
                Card currentCard = deck.dealCard();
                // adds cards to player object and calculates their score
                playerCards.add(currentCard);
                player.setCards(playerCards);
                player.calculateHand();
            }


            // if in the unlikely case the player has 2 aces then changes the value of one of them to 1
            if (player.getHand() > 21) {
                player.getCard(0).setValue(1);
            }


            // checks if player has natural blackjack
            if (player.getHand() == 21) {
                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you were dealt the " + player.getCard(0) + " and the " + player.getCard(1));
                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have a natural blackjack! Congrats. Your bet will be paid back at 3:2. You will receive £" + (player.getBet() * 1.5));
                // player wins 1.5x their bet
                player.setBalance(player.getBalance() + (player.getBet() * 1.5));
                //Although the player isn't bust they have finished their involvement this round so setting Bust to true excludes them from future participation in this round
                player.setBust(true);
            }
        }

        // dealer deals themselves 2 cards
        for (int i = 0; i < 2; i++) {
            Card currentCard = deck.dealCard();
            dealerCards.add(currentCard);
        }
        // calculates dealer hand
        dealerHand = calculateValue(dealerCards);

        // if in the unlikely case the dealer has 2 aces then changes the value of one of them to 1
        if (dealerHand > 21) {
            dealerCards.get(0).setValue(1);
        }

        // displays all players hands and their scores
        for (Player player : playerList) {
            // if statement excludes players who are out of the round from seeing their hand again
            if (!player.isBust()) {
                displayMessage((ConsoleColors.CYAN + player.getName() + "'s hand: " + ConsoleColors.RESET + player.getCard(0)) + " and " + player.getCard(1) + " and you have a score of " + player.getHand());
            }
        }
        // displays only 1 card of the dealers hand. One remains unseen to the players
        displayMessage(ConsoleColors.PURPLE + "Dealer hand: " + ConsoleColors.RESET + dealerCards.get(0).toString() + " and they have one more unseen card");

        // only runs if the surrender rule is turned on
        if (surrender) {
            displayMessage("Surrender rule is turned on. You can surrender this round and receive back half your bet.");
            // loops through every player and gives them the option to surrender
            for (Player player : playerList) {
                // only asks players who are still in the round
                if (!player.isBust()) {
                    boolean validInput = false;
                    // input validation is used to ensure only Y or N is accepted
                    while (!validInput) {
                        displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " would you like to surrender? Enter Y or N.");
                        String surrenderResponse = scanner.nextLine();
                        if (surrenderResponse.equalsIgnoreCase("Y")) {
                            displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have surrender this round you will receive back £" + (player.getBet() / 2));
                            // gives player back half their bet
                            player.setBalance(player.getBalance() - (player.getBet() / 2));
                            // sets bust value to true to exclude them from the rest of the round
                            player.setBust(true);
                            validInput = true;
                        } else if (surrenderResponse.equalsIgnoreCase("N")) {
                            validInput = true;
                        } else {
                            displayMessage(ConsoleColors.RED + "Input not recognised. " + ConsoleColors.RESET + "Please ensure you enter 'Y' or 'N'.");
                        }
                    }
                }
            }
        }

        // only runs if the double down rules is turned on
        if (doubleDown) {
            displayMessage("Double down is turned on. You can double down this round and double your bet.");
            // loops through every player and gives them the option to double down
            for (Player player : playerList) {
                if (!player.isBust()) {
                    // only asks players who are still in the round
                    boolean validInput = false;
                    while (!validInput) {
                        if ((player.getBalance() - (player.getBet() * 2)) < 0) {
                            // if the player doesn't have funds to double down then they are excluded from being asked
                            displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you don't have enough money to double down.");
                            break;
                        } else {
                            displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " would you like to double down? Enter Y or N.");
                            String doubleDownResponse = scanner.nextLine();
                            if (doubleDownResponse.equalsIgnoreCase("Y")) {
                                // doubles players bet
                                player.setBet(player.getBet() * 2);
                                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " your bet is now £" + player.getBet());
                                validInput = true;
                            } else if (doubleDownResponse.equalsIgnoreCase("N")) {
                                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have chosen not to double down. Your bet remains at £" + player.getBet());
                                validInput = true;
                            } else {
                                displayMessage(ConsoleColors.RED + "Input not recognised. " + ConsoleColors.RESET + "Please ensure you enter 'Y' or 'N'.");
                            }
                        }

                    }
                }
            }
        }

        // this segment allows players to hit or stand
        for (Player player : playerList) {
            if (!player.isBust()) {
                // only asks players who are still in the round
                displayMessage((ConsoleColors.CYAN + player.getName() + "'s hand: " + ConsoleColors.RESET + player.getCard(0)) + " and " + player.getCard(1) + " and you have a score of " + player.getHand());
                while (true) {
                    // while loop allows for input validation on user input
                    displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you can now either Stand (enter S) or Hit (enter H)");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("S")) {
                        // if they stand it simply breaks out the loop as no more action is being taken
                        break;
                    } else if (input.equalsIgnoreCase("H")) {
                        // creates a temporary list to store the players current cards and the card they are about to be given
                        ArrayList<Card> playerCards;
                        playerCards = player.getCards();
                        // removes card from top of deck
                        Card currentCard = deck.dealCard();
                        if (currentCard.getRank().equals("Ace") && (player.getHand() + 11) > 21) {
                            // if the card is an ace and if adding the ace with a value of 11 will make the player go bust then set the value to 1
                            currentCard.setValue(1);
                        }
                        // if the card is not an ace then it is added to the players cards and their new score is now calculated
                        playerCards.add(currentCard);
                        player.setCards(playerCards);
                        player.calculateHand();
                        // this if statement handles changing ace values to stop player going bust
                        if (player.getHand() > 21) {
                            // loops through the players cards looking for an ace
                            for (Card playerCard : playerCards) {
                                // if there is an ace which value is still at 11
                                if (playerCard.getValue() == 11) {
                                    // then the value is set to 1
                                    playerCard.setValue(1);
                                    // recalculates hand
                                    player.calculateHand();

                                    // will continue looking for more aces to change unless the players hand is now 21 or under
                                    if (player.getHand() <= 21) {
                                        break;
                                    }
                                }
                            }
                        }
                        displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have been dealt the " + currentCard + " and you now have a score of " + player.getHand());

                        // before giving the player the choice to hit or stand again it is checked if they are bust
                        if (player.getHand() > 21) {
                            displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you are bust with a score of " + player.getHand());
                            // removes bet from player balance
                            player.setBalance(player.getBalance() - player.getBet());
                            // sets bust value to true to exclude them from the rest of the round
                            player.setBust(true);
                            break;
                        }
                        // checks if player has 21
                        // if they do then the game automatically stands for them
                        if (player.getHand() == 21) {
                            displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you have got blackjack. The dealer will also attempt to get blackjack.");
                            break;
                        }
                    }
                }
            }
        }

        // the dealer will now choose to hit or stand
        // checks first if all player are bust as no need to continue with round if true
        boolean allBust = true;
        for (Player player : playerList) {
            if (!player.isBust()) {
                // loops through all players bust values if at least one is false then that means that the round will continue
                allBust = false;
                break;
            }
        }
        if (allBust) {
            if (playerList.size() > 1) {
                // only shows this message if there is more than one player
                // one player will already know that the round is over
                displayMessage("Everyone is bust this round.");
                return;
            } else {
                return;
            }
        }

        // in casinos dealers will continue to hit while they are on 16 or under and stand when on 17 and over
        // so while the dealer has a score of 16 or under they will continue to take a card
        while (dealerHand <= 16) {
            // removes card from top of deck
            Card currentCard = deck.dealCard();
            if (currentCard.getRank().equals("Ace") && (dealerHand + 11) > 21) {
                // if the card is an ace and if adding the ace with a value of 11 will make the dealer go bust then set the value to 1
                currentCard.setValue(1);
            }
            dealerCards.add(currentCard);
            dealerHand = calculateValue(dealerCards);
            displayMessage("The dealer hits. It is the " + currentCard);
            // this if statement handles changing ace values to stop dealer going bust
            if (dealerHand > 21) {
                // loops through the dealers cards looking for an ace
                for (Card dealerCard : dealerCards) {
                    // if there is an ace which value is at 11
                    if (dealerCard.getValue() == 11) {
                        // then the value is set to 1
                        dealerCard.setValue(1);
                        // recalculates hand
                        dealerHand = calculateValue(dealerCards);
                        // will continue looking for more aces to change unless the dealers hand is now 21 or under
                        if (dealerHand <= 21) {
                            break;
                        }
                    }
                }
            }
            if (dealerHand > 21) {
                // if the dealer goes bust after hitting then pay out the remaining players
                displayMessage("The dealer is bust with a score of " + dealerHand);
                for (Player player : playerList) {
                    if (!player.isBust()) {
                        // only pays out to users who are still in the round
                        displayMessage("Congrats " + ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you win £" + player.getBet());
                        player.setBalance(player.getBalance() + player.getBet());
                    }
                }
                return;
            }
        }

        displayMessage("The dealer stands. They will now reveal their hidden card.");
        displayMessage("The dealers hidden card is the " + dealerCards.get(1).toString() + ". They have a score of " + dealerHand);

        // the dealer has stood
        // the next part calculates which of the remaining players won, lost and drew

        // calculates how close to 21 the dealer was
        int dealerTo21 = 21 - dealerHand;

        // loops through every player
        for (Player player : playerList) {
            // excludes those who are already out
            if (!player.isBust()) {
                // calculates how close to 21 the player was
                int playerTo21 = 21 - player.getHand();
                if (playerTo21 < dealerTo21) {
                    // if player was closer to 21 then they win
                    displayMessage("Congratulations " + ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " with a score of " + player.getHand() + " compared to the dealers score of " + dealerHand + ".");
                    player.setBalance(player.getBalance() + player.getBet());
                } else if (dealerTo21 < playerTo21) {
                    // if the dealer was closer to 21 then the dealer wins
                    displayMessage("Unlucky this time " + ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + ". You had a score of " + player.getHand() + " but the dealer had " + dealerHand + ".");
                    player.setBalance(player.getBalance() - player.getBet());
                } else {
                    // if the player and dealer had the same score then it's a draw and the player has their bet returned to them
                    displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + " you drew with the dealer this time. Your £" + player.getBet() + " will be returned back to you.");
                }
            }

        }
    }

    public int getValidIntegerInput(String prompt, int min, int max) {
        // this method is reused throughout the code to validate user integer inputs
        while (true) {
            // displays the prompt to the user
            displayMessage(prompt);
            // if statements check if the input from the user is an integer
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    // if the input is within the boundaries the input is then returned
                    return input;
                } else {
                    // if the input is outwith the boundaries then the user is told the error they have made and will get asked to enter again
                    displayMessage(ConsoleColors.RED + "Invalid number." + ConsoleColors.RESET + " Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                // if the input is not a string then the user is told the error they have made and will get asked to enter again
                displayMessage(ConsoleColors.RED + "Invalid input." + ConsoleColors.RESET + " Please enter a number between " + min + " and " + max + ".");
                scanner.next();
            }
        }
    }

    public double getValidDoubleInput(String prompt, double min, double max) {
        // this method is reused throughout the code to validate user double inputs
        while (true) {
            // displays the prompt to the user
            displayMessage(prompt);
            // if statements check if the input from the user is an integer
            if (scanner.hasNextDouble()) {
                double input = scanner.nextDouble();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    // if the input is within the boundaries the input is then returned
                    return input;
                } else {
                    // if the input is outwith the boundaries then the user is told the error they have made and will get asked to enter again
                    displayMessage(ConsoleColors.RED + "Invalid number." + ConsoleColors.RESET + " Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                // if the input is not a string then the user is told the error they have made and will get asked to enter again
                displayMessage(ConsoleColors.RED + "Invalid input." + ConsoleColors.RESET + " Please enter a number between " + min + " and " + max + ".");
                scanner.next();
            }
        }
    }

    public void displayRules() {
        while (true) {
            // uses input validation method to check user input
            int ruleInput = getValidIntegerInput("Here you can change the set-up of and rules of the game. \n " +
                    "1. Change number of decks of cards. \n " +
                    // ternary operator shows if double down and surrender is turned on or off
                    "2. Double Down. Currently: " + (doubleDown ? ConsoleColors.GREEN + "On" + ConsoleColors.RESET : ConsoleColors.RED + "Off" + ConsoleColors.RESET) + " \n " +
                    "3. Surrender. Currently: " + (surrender ? ConsoleColors.GREEN + "On" + ConsoleColors.RESET : ConsoleColors.RED + "Off" + ConsoleColors.RESET) + " \n " +
                    "4. Return to Main Menu.", 1, 4);
            if (ruleInput == 1) {
                // uses input validation method to check user input and changes number of decks to valid input
                decks = getValidIntegerInput("Blackjack can be played with 1-8 decks of cards. This game is currently set at " + decks + " decks but you can change it by entering a number between 1 and 8.", 1, 8);
                displayMessage("Number of decks changed to " + decks);
            } else if (ruleInput == 2) {
                // inverts the double down value
                // on/off switch
                doubleDown = !doubleDown;
            } else if (ruleInput == 3) {
                // inverts the surrender value
                // on/off switch
                surrender = !surrender;
            } else if (ruleInput == 4) {
                // returns player to main menu
                break;
            } else {
                displayMessage(ConsoleColors.RED + "Input not recognised. " + ConsoleColors.RESET + "Please enter a number between 1 and 4.");
            }
        }
    }

    public void quitMessages() {
        // loops through the players and provides a message based on their final balance
        for (Player player : playerList) {
            if (player.getBalance() > 100) {
                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + ". Congrats you finished with £" + player.getBalance() + ". Which means you made " + ConsoleColors.GREEN + "£" + (player.getBalance() - 100) + "." + ConsoleColors.RESET);
            } else if (player.getBalance() < 100) {
                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + ". You finished with £" + player.getBalance() + ". Which means you lost " + ConsoleColors.RED + "£" + (100 - player.getBalance()) + "." + ConsoleColors.RESET);
            } else {
                displayMessage(ConsoleColors.CYAN + player.getName() + ConsoleColors.RESET + ". You finished with £100 meaning you broke even.");
            }
        }

    }

    public int calculateValue(ArrayList<Card> cards) {
        // takes a list of cards and calculates the score
        int total = 0;
        for (Card card : cards) {
            total = total + card.getValue();
        }

        return total;
    }

    public void displayMessage(String message) {
        // displays a message in the terminal and adds a 1s wait before anything else happens
        // makes it easier for players to read and understand what's happening
        System.out.println(message);
        wait(1000);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}