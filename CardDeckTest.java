package ContinuousAssessment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class CardDeckTest {

    CardDeck initDeck() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 1; i < 5; i++) {tempSet.add(new Card(i));}

        return new CardDeck(tempSet, 1);
    }

    @Test
    void size() {
        CardDeck testDeck = initDeck();
        assertEquals(4, testDeck.size());
    }

    @Test
    void testToString() {
        CardDeck testDeck = initDeck();

        assertEquals(String.valueOf(testDeck), "[1, 2, 3, 4]");
    }

    @Test
    void testTakeCardFromTop() {
        CardDeck testDeck = initDeck();

        String valueBefore = String.valueOf(testDeck);

        testDeck.takeCardFromTop();

        String expectedOutput = "[2, 3, 4]";
        String actualOutput = String.valueOf(testDeck);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void testPlaceCardOnBottom() {
        CardDeck testDeck = initDeck();

        String valueBefore = String.valueOf(testDeck);

        testDeck.placeCardOnBottom(new Card(10));

        String expectedOutput = "[1, 2, 3, 4, 10]";
        String actualOutput = String.valueOf(testDeck);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void testSize() {
    }

    @Test
    void getCards() {
    }

    @Test
    void outputToFile() {
    }

    @Test
    void getOrderedHand() {
    }

    @Test
    void logOutput() {
    }

//    @Test
//    void takeAndPlace() {
//        CardDeck testDeck1 = initDeck();
//        CardDeck testDeck2 = initDeck();
//
//        Card c = CardDeck.takeAndPlace(testDeck1, testDeck2, new Card(1));
//
//        assertEquals(1, c.getValue());
//        assertNotEquals(testDeck1.getCards(), testDeck2.getCards());
//    }
}