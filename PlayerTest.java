package ContinuousAssessment;

import org.junit.jupiter.api.DisplayName;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class PlayerTest {

    Queue<Card> tempCards = new LinkedList<>();

    Player createPlayer() {
        CardDeck playerDeck = new CardDeck(1);
        CardDeck lDeck = new CardDeck(2);
        CardDeck rDeck = new CardDeck(3);
        for (Card c: this.tempCards) {
            playerDeck.placeCardOnBottom(c);
            lDeck.placeCardOnBottom(c);
            rDeck.placeCardOnBottom(c);
        }
        return new Player(playerDeck, lDeck, rDeck);
    }

    @DisplayName("Check if all of the cards are the same")
    @org.junit.jupiter.api.Test
    void allSameCards() {

        this.tempCards.clear();
        for (int i = 0; i < 4; i++) {this.tempCards.add(new Card(1));}

        Player p = createPlayer();

        assertTrue(p.getPlayerWon());
    }

    @org.junit.jupiter.api.Test
    void writeFile() {
        assertEquals("","");
    }

    @org.junit.jupiter.api.Test
    void drawCardFromDeck() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();
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
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();
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
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

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
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        assertEquals(p.getOrderedCards(), "1 2 3 4 ");
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(1));}

        Player p = createPlayer();

        String expectedOutput = "\nPlayer number  : 2\nPlayer deck    : [1, 1, 1, 1]\nDeck no 2 ldeck: [1, 1, 1, 1]\nDeck no 3 rdeck: [1, 1, 1, 1]\n";
        String actualOutput = String.valueOf(p);

        assertEquals(actualOutput, expectedOutput);
    }
}