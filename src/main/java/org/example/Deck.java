package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(int decks) {
        createDeck(decks);
    }

    public void createDeck (int decks) {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        // aces have a default value of 11 but this may be changed during the game

        // loops through the suits array and creates a card for every possible combination
        // decks variable affects how many decks are created
        // if decks is set to 1 it will run once and create 52 unique cards
        for (int i = 0; i < decks; i++) {
            for (String suit : suits) {
                for (int j = 0; j < ranks.length; j++) {
                    cards.add(new Card(suit, ranks[j], values[j]));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

}
