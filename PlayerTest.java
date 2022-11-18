package ContinuousAssessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.*;

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

    @org.junit.jupiter.api.Test
    void drawCardFromDeck() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        String valueBefore = String.valueOf(p);

        p.drawCardFromDeck();

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 4 4 \n" +
                "Deck no 2 ldeck: 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 \n";

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

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 \n" +
                "Deck no 2 ldeck: 4 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 4 \n";

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

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 4 \n" +
                "Deck no 2 ldeck: 4 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 \n";
        String actualOutput = String.valueOf(p);


//        assertNotEquals(valueBefore, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(1));}

        Player p = createPlayer();

        String expectedOutput = "\nPlayer number  : 1\nPlayer deck    : 1 1 1 1 \nDeck no 2 ldeck: 1 1 1 1 \nDeck no 3 rdeck: 1 1 1 1 \n";
        String actualOutput = String.valueOf(p);

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    void testAllSameCards() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(1));}

        Player p = createPlayer();

        assertEquals(true, p.getPlayerWon());
    }

    @Test
    void testDrawCardFromDeck() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        String valueBefore = String.valueOf(p);

        p.drawCardFromDeck();

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 4 4 \n" +
                "Deck no 2 ldeck: 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 \n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void testDiscardCardToDeck() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        String valueBefore = String.valueOf(p);

        p.discardCardToDeck();

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 \n" +
                "Deck no 2 ldeck: 4 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 4 \n";

        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    void logOutput() throws IOException {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        p.drawCardFromDeck();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player1_output.txt"));
        String value = reader.readLine();
        value = reader.readLine();

        assertEquals("player 1 draws a 4 from deck 2", value);
    }

    @Test
    void testPlayGo() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        String valueBefore = String.valueOf(p);

        p.playGo();

        String expectedOutput = "\nPlayer number  : 1\n" +
                "Player deck    : 1 2 3 4 \n" +
                "Deck no 2 ldeck: 4 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 \n";
        String actualOutput = String.valueOf(p);

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void loseOutput() throws IOException {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        p.loseOutput();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player-1_output.txt"));
        String value = reader.readLine();

        assertEquals("player -1 initial hand 1 2 3 4 ", value);

    }

    @Test
    void winOutput() throws IOException {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();

        p.winOutput();

        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player-1_output.txt"));
        String value = reader.readLine();

        assertEquals("player -1 initial hand 1 2 3 4 ", value);
    }

    @Test
    void currentHandOutput() {
        this.tempCards.clear();
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}


        Player p = createPlayer();


        assertEquals("1 2 3 4 ", p.getOrderedCards());
    }

    @Test
    void setPlayerWon() {
        CardDeck temp1 = new CardDeck(1);
        CardDeck temp2 = new CardDeck(2);
        CardDeck temp3 = new CardDeck(3);

        for (int i = 0; i < 4; i++) {
            temp1.placeCardOnBottom(new Card(1));
            temp2.placeCardOnBottom(new Card(1));
            temp3.placeCardOnBottom(new Card(1));
        }
        temp1.placeCardOnBottom(new Card(2));

        Player p = new Player(temp1, temp2, temp3);

        p.discardCardToDeck();
        p.drawCardFromDeck();

        boolean checkWin = p.getPlayerWon();

        p.setPlayerWon();

        assertNotEquals(checkWin, p.getPlayerWon());

    }
}