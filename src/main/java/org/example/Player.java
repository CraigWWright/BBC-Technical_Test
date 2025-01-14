package org.example;

import java.util.ArrayList;

public class Player {
    // player name
    private String name;
    // player balance
    private double balance;
    // bet set by the player
    private double bet;
    // cards dealt to the player
    private ArrayList<Card> cards;
    // tracks score of the player based on their current cards
    private int hand;
    // tracks if the user is bust in the round
    private boolean bust;

    public Player(String name, double balance, double bet, ArrayList<Card> cards, int hand, boolean bust) {
        this.name = name;
        this.balance = balance;
        this.cards = cards;
        this.hand = hand;
        this.bust = bust;
    }

    // default constructor
    public Player(String name) {
        this.name = name;
        this.balance = 100;
        this.cards = null;
        this.hand = 0;
        this.bust = false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }


    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public void calculateHand() {
        // calculates the players score based on their cards
        // resets the value of their current hand
        hand = 0;
        // loops through the cards and adds their value
        for (Card card : cards) {
            hand = hand + card.getValue();
        }

    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", money=" + balance +
                ", bet=" + bet +
                ", cards=" + cards +
                ", hand=" + hand +
                ", bust=" + bust +
                '}';
    }
}
