package ContinuousAssessment;

import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class PlayerTest {

    @DisplayName("Check if all of the cards are the same")
    @org.junit.jupiter.api.Test
    void allSameCards() {

        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 0; i < 4; i++) {tempSet.add(new Card(1));}

        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        assertEquals(true, Player.allSameCards(p.cards));
    }

    @org.junit.jupiter.api.Test
    void writeFile() {
        assertEquals("","");
    }

    @org.junit.jupiter.api.Test
    void drawCardFromDeck() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.drawCardFromDeck();

        String expectedOutput = "\n" +
                "Player number  : 2\n" +
                "Player deck    : [1, 2, 3, 4]\n" +
                "Deck no 2 ldeck: [3, 2, 1, 4]\n" +
                "Deck no 3 rdeck: [3, 2, 1, 4]\n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @org.junit.jupiter.api.Test
    void discardCardToDeck() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.discardCardToDeck();

        String expectedOutput = "\n" +
                "Player number  : 2\n" +
                "Player deck    : [1, 2, 3, 4]\n" +
                "Deck no 2 ldeck: [3, 2, 1, 4]\n" +
                "Deck no 3 rdeck: [3, 2, 1, 4]\n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);

    }

    @org.junit.jupiter.api.Test
    void playGo() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.playGo();

        String expectedOutput = "\nPlayer number  : 2\n" +
                "Player deck    : [1, 2, 3, 4]\n" +
                "Deck no 2 ldeck: [2, 1, 4, 3]\n" +
                "Deck no 3 rdeck: [2, 1, 4, 3]\n";
        String actualOutput = String.valueOf(p);

        assertNotEquals(valueBefore, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }

    @DisplayName("Check sorted deck value")
    @org.junit.jupiter.api.Test
    void getOrderedHand() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        assertEquals(p.getOrderedHand(), Arrays.asList(1,2,3,4));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(1));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String expectedOutput = "\nPlayer number  : 2\nPlayer deck    : [1, 1, 1, 1]\nDeck no 2 ldeck: [1, 1, 1, 1]\nDeck no 3 rdeck: [1, 1, 1, 1]\n";
        String actualOutput = String.valueOf(p);

        assertEquals(actualOutput, expectedOutput);
    }
}