package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testGetValue() {
        Card card = new Card("Hearts", "King", 10);

        assertEquals(10, card.getValue(), "King value should be equal to 10.");
    }

    @Test
    void testSetValue() {
        Card card = new Card("Hearts", "Ace", 11);
        card.setValue(1);

        assertEquals(1, card.getValue(), "Ace value should be equal to 1.");
    }

    @Test
    void testToString() {
        Card card = new Card("Hearts", "Seven", 10);

        assertEquals("Seven of Hearts", card.toString(), "toString method should equal 'Seven of Hearts',");
    }



}