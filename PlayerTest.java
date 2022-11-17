package ContinuousAssessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class PlayerTest {

    @org.junit.jupiter.api.Test
    void writeFile() {
        assertEquals("","");
    }

    @DisplayName("Check sorted deck value")
    @org.junit.jupiter.api.Test
    void getOrderedHand() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        assertEquals(p.getOrderedHand(), "1 2 3 4 ");
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(1));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String expectedOutput = "\nPlayer number  : 1\nPlayer deck    : 1 1 1 1 \nDeck no 2 ldeck: [1, 1, 1, 1]\nDeck no 3 rdeck: [1, 1, 1, 1]\n";
        String actualOutput = String.valueOf(p);

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    void testAllSameCards() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 0; i < 4; i++) {tempSet.add(new Card(1));}

        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        assertEquals(true, Player.allSameCards(p.cards));
    }

    @Test
    void testDrawCardFromDeck() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.drawCardFromDeck();

        String expectedOutput = "\n" +
                "Player number  : 1\n" +
                "Player deck    : 1 2 3 4 \n" +
                "Deck no 2 ldeck: [3, 2, 1, 4]\n" +
                "Deck no 3 rdeck: [3, 2, 1, 4]\n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void testDiscardCardToDeck() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.discardCardToDeck();

        String expectedOutput = "\n" +
                "Player number  : 1\n" +
                "Player deck    : 1 2 3 4 \n" +
                "Deck no 2 ldeck: [3, 2, 1, 4]\n" +
                "Deck no 3 rdeck: [3, 2, 1, 4]\n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void logOutput() throws IOException {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, -1), new CardDeck(tempSet, -1), new CardDeck(tempSet, -1));

        p.drawCardFromDeck();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player-1_output.txt"));
        String value = reader.readLine();
        value = reader.readLine();

        assertEquals("player -1 draws a 4 from deck -1", value);
    }

    @Test
    void testPlayGo() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, 1), new CardDeck(tempSet, 2), new CardDeck(tempSet, 3));

        String valueBefore = String.valueOf(p);

        p.playGo();

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 4 \n" +
                "Deck no 2 ldeck: [2, 1, 4, 3]\n" +
                "Deck no 3 rdeck: [2, 1, 4, 3]\n";
        String actualOutput = String.valueOf(p);

        assertNotEquals(valueBefore, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void loseOutput() throws IOException {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, -1), new CardDeck(tempSet, -1), new CardDeck(tempSet, -1));

        p.loseOutput();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player-1_output.txt"));
        String value = reader.readLine();

        assertEquals("player -1 initial hand 1 2 3 4 ", value);

    }

    @Test
    void winOutput() throws IOException {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, -1), new CardDeck(tempSet, -1), new CardDeck(tempSet, -1));

        p.winOutput();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player-1_output.txt"));
        String value = reader.readLine();

        assertEquals("player -1 initial hand 1 2 3 4 ", value);
    }

    @Test
    void currentHandOutput() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}


        Player p = new Player(new CardDeck(tempSet, -1), new CardDeck(tempSet, -1), new CardDeck(tempSet, -1));


        assertEquals("1 2 3 4 ", p.getOrderedHand());
    }

    @Test
    void setPlayerWon() {
        Queue<Card> tempSet = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(i));}

        Queue<Card> tempAdjacentDeck1 = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(1));}
        Queue<Card> tempAdjacentDeck2 = new LinkedList<>();
        for (int i = 4; i > 0; i--) {tempSet.add(new Card(1));}

        Player p = new Player(new CardDeck(tempSet, -1), new CardDeck(tempAdjacentDeck1, 0), new CardDeck(tempAdjacentDeck2, 1));
        for (int i = 0; i < 3; i++) {
            p.discardCardToDeck();
            p.drawCardFromDeck();
        }

        boolean checkWin = p.getPlayerWon();

        p.setPlayerWon();

        assertNotEquals(checkWin, p.getPlayerWon());

    }
}