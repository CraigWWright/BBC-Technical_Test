package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckHas52UniqueCards() {
        Deck deck = new Deck(1);
        List<Card> cards = deck.getCards();

        // Check for unique cards
        HashSet<Card> uniqueCards = new HashSet<>(cards);
        assertEquals(52, uniqueCards.size(), "Deck should contain 52 unique cards.");
    }

    @Test
    void testDeckHas52Cards() {
        Deck deck = new Deck(1);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(52, cards.size(), "Deck should contain 52 cards.");
    }

    @Test
    void test2DeckHas104Cards() {
        Deck deck = new Deck(2);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(104, cards.size(), "Deck should contain 104 cards.");
    }

    @Test
    void test3DeckHas156Cards() {
        Deck deck = new Deck(3);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(156, cards.size(), "Deck should contain 156 cards.");
    }

    @Test
    void test4DeckHas208Cards() {
        Deck deck = new Deck(4);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(208, cards.size(), "Deck should contain 208 cards.");
    }

    @Test
    void test5DeckHas260Cards() {
        Deck deck = new Deck(5);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(260, cards.size(), "Deck should contain 260 cards.");
    }

    @Test
    void test6DeckHas312Cards() {
        Deck deck = new Deck(6);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(312, cards.size(), "Deck should contain 312 cards.");
    }

    @Test
    void test7DeckHas364Cards() {
        Deck deck = new Deck(7);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(364, cards.size(), "Deck should contain 364 cards.");
    }

    @Test
    void test8DeckHas416Cards() {
        Deck deck = new Deck(8);
        List<Card> cards = deck.getCards();

        // Check deck size
        assertEquals(416, cards.size(), "Deck should contain 416 cards.");
    }

    @Test
    void testDealCard() {
        Deck deck = new Deck(1);
        Card card = deck.getCards().get(0);
        Card dealtCard = deck.dealCard();

        assertEquals(card, dealtCard, "Dealt card should be the card that was at top of the deck.");
    }

    @Test
    void testShuffle() {
        Deck deck = new Deck(1);
        List<Card> originalOrder = new ArrayList<>(deck.getCards());
        deck.shuffle();
        List<Card> shuffledOrder = deck.getCards();

        assertNotEquals(originalOrder, shuffledOrder, "Shuffled deck should be different from original order");
    }


}