package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testScenario1() {
        /*
        Given I play a game of blackjack
        When I am dealt my opening hand
        Then I have two cards
         */

        Deck deck = new Deck(1);
        deck.shuffle();
        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            // removes a card from the top of the deck
            Card currentCard = deck.dealCard();
            // adds cards to player object and calculates their score
            playerCards.add(currentCard);
            player.setCards(playerCards);
            player.calculateHand();
        }

        assertEquals(2, player.getCards().size(), "Player should have 2 cards.");
    }

    @Test
    void testScenario2() {
        /*
        Given I have a valid hand of cards
`       When I choose to ‘hit’
        Then I receive another card
        And my score is updated
         */
        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card five = new Card("Hearts", "Five", 5);
        playerCards.add(king);
        playerCards.add(five);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        //Player Hits
        boolean playerHits = true;
        if (playerHits) {
            Card four = new Card("Spades", "Four", 4);
            playerCards.add(four);
            player.setCards(playerCards);
            player.calculateHand();
            if (player.getHand() > 21) {
                player.setBust(true);
            }

            assertEquals(19, player.getHand(), "Player should have a score of 19.");
            assertFalse(player.isBust(), "The player has a score of 19 and so should not be bust.");
            assertEquals(3, player.getCards().size(), "The player should now have 3 cards.");
        }

    }

    @Test
    void testScenario3() {
        /*
        Given I have a valid hand of cards
        When I choose to ‘stand’
        Then I receive no further cards
        And my score is evaluated
         */

        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card five = new Card("Hearts", "Five", 5);
        playerCards.add(king);
        playerCards.add(five);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        //Player Stands
        boolean playerHits = false;
        if (playerHits) {
            Card four = new Card("Spades", "Four", 4);
            playerCards.add(four);
            player.setCards(playerCards);
            player.calculateHand();
            if (player.getHand() > 21) {
                player.setBust(true);
            }

            assertEquals(19, player.getHand(), "Player should have a score of 19.");
            assertFalse(player.isBust(), "The player has a score of 19 and so should not be bust.");
            assertEquals(3, player.getCards().size(), "The player should now have 3 cards.");
        } else {
            assertEquals(15, player.getHand(), "Player should have a score of 15.");
            assertFalse(player.isBust(), "The player has a score of 15 and so should not be bust.");
            assertEquals(2, player.getCards().size(), "The player should still have only 2 cards.");
        }
    }

    @Test
    void testScenario4() {
        /*
        Given my score is updated or evaluated
        When it is 21 or less
        Then I have a valid hand
         */

        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card five = new Card("Hearts", "Five", 5);
        Card six = new Card("Clubs", "Six", 6);
        playerCards.add(king);
        playerCards.add(five);
        playerCards.add(six);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        assertEquals(21, player.getHand(), "Player should have a score of 21.");
        assertFalse(player.isBust(), "Player has a score of 21 and is therefore not bust.");

    }

    @Test
    void testScenario5() {
        /*
        Given my score is updated
        When it is 22 or more
        Then I am ‘bust’ and do not have a valid hand

         */

        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card five = new Card("Hearts", "Five", 5);
        Card eight = new Card("Clubs", "Eight", 8);
        playerCards.add(king);
        playerCards.add(five);
        playerCards.add(eight);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        assertEquals(23, player.getHand(), "Player should have a score of 23.");
        assertTrue(player.isBust(), "Player has a score of 23 and is therefore not bust.");

    }

    @Test
    void testScenario6() {
        /*
        Given I have a king and an ace
        When my score is evaluated
        Then my score is 21
         */
        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card ace = new Card("Diamonds", "Ace", 11);
        playerCards.add(king);
        playerCards.add(ace);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        assertEquals(21, player.getHand(), "Player should have a score of 21.");

    }

    @Test
    void testScenario7() {
        /*
        Given I have a king, a queen, and an ace
        When my score is evaluated
        Then my score is 21
        */

        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card queen = new Card("Diamonds", "Queen", 10);
        playerCards.add(king);
        playerCards.add(queen);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        Card newCard = new Card("Spades", "Ace", 11);
        if (newCard.getRank().equals("Ace") && (player.getHand() + 11) > 21) {
            // if the card is an ace and if adding the ace with a value of 11 will make the player go bust then set the value to 1
            newCard.setValue(1);
        }
        // if the card is not an ace then it is added to the players cards and their new score is now calculated
        playerCards.add(newCard);
        player.setCards(playerCards);
        player.calculateHand();
        if (player.getHand() > 21) {
            for (Card playerCard : playerCards) {
                if (playerCard.getValue() == 11) {
                    playerCard.setValue(1);
                    player.calculateHand();

                    if (player.getHand() <= 21) {
                        break;
                    }
                }
            }
        }

        assertEquals(21, player.getHand(), "Player score should be equal to 21.");
    }

    @Test
    void testScenario8 () {
        /*
        Given that I have a nine, an ace, and another ace
        When my score is evaluated
        Then my score is 21
         */

        Player player = new Player("Craig");

        ArrayList<Card> playerCards = new ArrayList<>();
        Card nine = new Card("Hearts", "Nine", 9);
        Card ace = new Card("Diamonds", "Ace", 11);
        playerCards.add(nine);
        playerCards.add(ace);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        }

        Card newCard = new Card("Spades", "Ace", 11);
        if (newCard.getRank().equals("Ace") && (player.getHand() + 11) > 21) {
            // if the card is an ace and if adding the ace with a value of 11 will make the player go bust then set the value to 1
            newCard.setValue(1);
        }
        // if the card is not an ace then it is added to the players cards and their new score is now calculated
        playerCards.add(newCard);
        player.setCards(playerCards);
        player.calculateHand();
        if (player.getHand() > 21) {
            for (Card playerCard : playerCards) {
                if (playerCard.getValue() == 11) {
                    playerCard.setValue(1);
                    player.calculateHand();

                    if (player.getHand() <= 21) {
                        break;
                    }
                }
            }
        }

        assertEquals(21, player.getHand(), "Player score should be equal to 21.");
    }

    // The following tests test the rest of the logic of the game

    @Test
    void testPlayerSetup() {
        ArrayList<Player> playerList = new ArrayList<>();
        int noOfPlayers = 3;
        for (int i = 0; i < noOfPlayers; i++) {
            Player newPlayer = new Player("Craig");
            playerList.add(newPlayer);
        }

        assertEquals(3, playerList.size(), "The playerList should contain 3 players.");
    }

    @Test
    void testRemovePlayerWhenBalanceEquals0 () {
        Player Craig = new Player("Craig");
        Player Dan = new Player("Dan");
        Player Steven = new Player("Steven");
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(Craig);
        playerList.add(Dan);
        playerList.add(Steven);

        playerList.get(0).setBalance(0);

        Iterator<Player> iterator = playerList.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getBalance() <= 0) {
                iterator.remove();
            }
        }

        assertEquals(2, playerList.size(), "The playerList should contain only 2 players.");
        assertEquals("Dan", playerList.get(0).getName(), "The first player in the list should now be Dan as Craig has been removed.");

    }

    @Test
    void testGameEndsWhen0Players () {
        Player Craig = new Player("Craig");
        Player Dan = new Player("Dan");
        Player Steven = new Player("Steven");
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(Craig);
        playerList.add(Dan);
        playerList.add(Steven);

        for (Player player : playerList) {
            player.setBalance(0);
        }

        Iterator<Player> iterator = playerList.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getBalance() <= 0) {
                iterator.remove();
            }
        }

        assertTrue(playerList.isEmpty(), "The player list should be empty");
    }

    @Test
    void testDealToAllPlayers () {
        Deck deck = new Deck(1);
        Player Craig = new Player("Craig");
        Player Dan = new Player("Dan");
        Player Steven = new Player("Steven");
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(Craig);
        playerList.add(Dan);
        playerList.add(Steven);

        for (Player player : playerList) {
            ArrayList<Card> playerCards = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                // removes a card from the top of the deck
                Card currentCard = deck.dealCard();
                // adds cards to player object and calculates their score
                playerCards.add(currentCard);
                player.setCards(playerCards);
                player.calculateHand();
            }
        }

        for (Player player : playerList) {
            assertEquals(2, player.getCards().size(), "Every player should have 2 cards.");
        }
    }

    @Test
    void testSetBet() {
        Player Craig = new Player("Craig");
        Craig.setBet(5.5);

        assertEquals(5.5, Craig.getBet(), "Bet should be equal to 5.5.");
    }

    @Test
    void testSurrender() {
        Player Craig = new Player("Craig");
        Craig.setBet(10);


        boolean surrender = true;
        if (surrender) {
            Craig.setBalance(Craig.getBalance() - (Craig.getBet() / 2));
            Craig.setBust(true);
        }

        assertEquals(95, Craig.getBalance(), "Balance should be 95. Craig received half his bet back.");
        assertTrue(Craig.isBust(), "Craig should be bust as he surrendered and is out of the round.");
    }

    @Test
    void testDoubleDown() {
        Player Craig = new Player("Craig");
        Player Dan = new Player("Dan");
        Craig.setBet(10);
        Dan.setBet(60);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(Craig);
        playerList.add(Dan);

        boolean doubleDown = true;
        if (doubleDown) {
            for (Player player : playerList) {
                if ((player.getBalance() - (player.getBet() * 2)) < 0) {
                    break;
                } else {
                    player.setBet(player.getBet() * 2);
                }
            }
        }

        assertEquals(20, playerList.get(0).getBet(), "Craig had enough funds to double his bet so it should be 20.");
        assertEquals(60, playerList.get(1).getBet(), "Dan didn't have enough funds to double his bet so it should be 60.");
    }

    @Test
    void testNaturalBlackjack() {
        Player player = new Player("Craig");
        player.setBet(10);

        ArrayList<Card> playerCards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card ace = new Card("Diamonds", "Ace", 11);
        playerCards.add(king);
        playerCards.add(ace);

        player.setCards(playerCards);
        player.calculateHand();

        if (player.getHand() > 21) {
            player.setBust(true);
        } else if (player.getHand() == 21) {
            player.setBalance(player.getBalance() + (player.getBet() * 1.5));
            player.setBust(true);
        }

        assertEquals(115, player.getBalance(), "The players balanced should be 115 as natural blackjack pays 3:2.");
        assertTrue(player.isBust(), "The player should be 'bust' as they have won and don't need to partake in the round anymore.");
    }

    @Test
    void testDealerCalculateHand() {
        Main main = new Main();
        ArrayList<Card> dealerCards = new ArrayList<>();
        int dealerHand;

        Card king = new Card("Spades", "King", 10);
        Card six = new Card("Clubs", "Six", 6);
        dealerCards.add(king);
        dealerCards.add(six);

        dealerHand = main.calculateValue(dealerCards);

        assertEquals(16, dealerHand, "The dealers score should be 16.");
    }

    @Test
    void testDealerHit() {
        Main main = new Main();
        ArrayList<Card> dealerCards = new ArrayList<>();
        int dealerHand;

        Card king = new Card("Spades", "King", 10);
        Card six = new Card("Clubs", "Six", 6);
        dealerCards.add(king);
        dealerCards.add(six);

        dealerHand = main.calculateValue(dealerCards);

        while (dealerHand <= 16) {
            Card three = new Card("Diamonds", "Three", 3);
            dealerCards.add(three);
            dealerHand = main.calculateValue(dealerCards);
        }

        assertEquals(19, dealerHand, "The dealers score should be equal to 19.");
    }

    @Test
    void testDealerChangeAceValue() {
        Main main = new Main();
        ArrayList<Card> dealerCards = new ArrayList<>();
        int dealerHand;

        Card king = new Card("Spades", "King", 10);
        Card queen = new Card("Spades", "King", 10);
        dealerCards.add(king);
        dealerCards.add(queen);

        dealerHand = main.calculateValue(dealerCards);

        if (dealerHand > 21) {
            dealerCards.get(0).setValue(1);
        }

        Card newCard = new Card("Diamonds", "Ace", 11);
        if (newCard.getRank().equals("Ace") && (dealerHand + 11) > 21) {
            newCard.setValue(1);
        }
        dealerCards.add(newCard);
        dealerHand = main.calculateValue(dealerCards);

        assertEquals(21, dealerHand, "The dealer hand should be equal to 21.");

        dealerCards.clear();

        Card nine = new Card("Hearts", "Nine", 9);
        Card ace = new Card("Diamonds", "Ace", 11);
        dealerCards.add(nine);
        dealerCards.add(ace);

        dealerHand = main.calculateValue(dealerCards);

        if (dealerHand > 21) {
            dealerCards.get(0).setValue(1);
        }

        Card newCard2 = new Card("Spades", "King", 10);
        if (newCard2.getRank().equals("Ace") && (dealerHand + 11) > 21) {
            newCard2.setValue(1);
        }
        dealerCards.add(newCard2);
        dealerHand = main.calculateValue(dealerCards);
        if (dealerHand > 21) {
            for (Card dealerCard : dealerCards) {
                if (dealerCard.getValue() == 11) {
                    dealerCard.setValue(1);
                    dealerHand = main.calculateValue(dealerCards);
                    if (dealerHand <= 21) {
                        break;
                    }
                }
            }
        }

        assertEquals(20, dealerHand, "The dealer hand should be equal to 20.");
    }

    @Test
    void testDeckResetsWhenCreatingNew () {
        Deck deck = new Deck(1);

        deck.createDeck(1);
        deck.createDeck(2);

        assertEquals(104, deck.getCards().size());
    }
}