package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testInitBalanceSetTo100() {
        Player player = new Player("Craig");
        assertEquals(100, player.getBalance(), "Initial balance should be set to 100.");
    }

    @Test
    void testCalculateHand() {
        Player player = new Player("Craig");
        ArrayList<Card> cards = new ArrayList<>();
        Card king = new Card("Hearts", "King", 10);
        Card queen = new Card("Spades", "Queen", 10);
        cards.add(king);
        cards.add(queen);

        player.setCards(cards);
        player.calculateHand();

        assertEquals(20, player.getHand(), "Players hand should be equal to 20.");
    }

    @Test
    void testSetBustIsInitiallyFalse() {
        Player player = new Player("Craig");

        assertFalse(player.isBust(), "SetBust value should initially be set to true");
    }

    @Test
    void testGetCards() {
        Deck deck = new Deck(1);
        Player player = new Player("Craig");
        ArrayList<Card> playerCards = new ArrayList<>();
        for (int i=0; i<2; i++) {
            playerCards.add(deck.dealCard());
        }
        player.setCards(playerCards);

        assertEquals(playerCards, player.getCards(), "Player cards should equal the cards they were given.");
    }

}