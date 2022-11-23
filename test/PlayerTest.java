import ContinuousAssessment.Card;
import jdk.jfr.Description;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 4.13.1
 *
 * @author James Calnan & Nicholas Alexander
 * @version 1.0
 */
public class PlayerTest {


    Queue<Card> tempCards = new LinkedList<>();

    /**
     * Method to initialise a new player
     * @return a new player object with 3 decks
     */
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

    @org.junit.Test
    @Description("Test to check the drawCardFromDeck method works")
    public void testDrawCardFromDeck() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Record the value of the player on initialisation
        String valueBefore = String.valueOf(p);

        // Draw a card from the deck
        p.drawCardFromDeck();

        // Define expected output
        String expectedOutput = """

                Player number  : 1
                Player deck    : 1 2 3 4 4\s
                Deck no 2 ldeck: 3 2 1\s
                Deck no 3 rdeck: 4 3 2 1\s
                """;

        // Define actual output
        String actualOutput = String.valueOf(p);

        // Assertion tests
        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @org.junit.Test
    @Description("Test to check the cards are being discarded correctly")
    public void testDiscardCardToDeck() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Record the value of the player on initialisation
        String valueBefore = String.valueOf(p);

        // Discard a card from the players deck
        p.discardCardToDeck();

        // Define expected output
        String expectedOutput = """

                Player number  : 1
                Player deck    : 1 2 3\s
                Deck no 2 ldeck: 4 3 2 1\s
                Deck no 3 rdeck: 4 3 2 1 4\s
                """;

        // Define actual output
        String actualOutput = String.valueOf(p);

        // Actual output
        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);

    }

    @org.junit.Test
    @Description("Test to check the thread safety of the program with multiple concurrent deck accesses")
    public void testThreadSafety() throws InterruptedException {

        // Create new decks for each player
        CardDeck playerDeck1 = new CardDeck(1);
        CardDeck playerDeck2 = new CardDeck(2);
        CardDeck playerDeck3 = new CardDeck(3);
        CardDeck playerDeck4 = new CardDeck(4);
        CardDeck lDeck = new CardDeck(2);
        CardDeck rDeck = new CardDeck(3);

        // Add new cards to the decks
        for (int i = 4; i > 0; i--) {
            playerDeck1.placeCardOnBottom(new Card(i));
            playerDeck2.placeCardOnBottom(new Card(i));
            playerDeck3.placeCardOnBottom(new Card(i));
            playerDeck4.placeCardOnBottom(new Card(i));
            lDeck.placeCardOnBottom(new Card(i));
            rDeck.placeCardOnBottom(new Card(i));
        }

        // Create new player objects
        Player p1 = new Player(playerDeck1, lDeck, rDeck);
        Player p2 = new Player(playerDeck2, rDeck, lDeck);
        Player p3 = new Player(playerDeck3, lDeck, rDeck);
        Player p4 = new Player(playerDeck4, rDeck, lDeck);

        // Create the processes so that deck accesses can happen at the same time
        Thread processesThread1 = new Thread(() -> {
            for (int i = 0; i < 4; i++){
                p1.drawCardFromDeck();
                p1.discardCardToDeck();
            }
        });
        Thread processesThread2 = new Thread(() -> {
            for (int i = 0; i < 4; i++){
                p2.drawCardFromDeck();
                p2.discardCardToDeck();
            }
        });
        Thread processesThread3 = new Thread(() -> {
            for (int i = 0; i < 4; i++){
                p3.drawCardFromDeck();
                p3.discardCardToDeck();
            }
        });
        Thread processesThread4 = new Thread(() -> {
            for (int i = 0; i < 4; i++){
                p4.drawCardFromDeck();
                p4.discardCardToDeck();
            }
        });

        // Start the processes
        processesThread1.start();
        processesThread2.start();
        processesThread3.start();
        processesThread4.start();

        // Wait for the processes to finish
        processesThread1.interrupt();
        processesThread2.interrupt();
        processesThread3.interrupt();
        processesThread4.interrupt();

        // Check the decks all have equal size
        assertEquals(p1.deckSize(), 4);
        assertEquals(p2.deckSize(), 4);
        assertEquals(p3.deckSize(), 4);
        assertEquals(p4.deckSize(), 4);

    }

    @org.junit.Test
    @Description("Test to check that the preferred card is being discarded last")
    public void testForceDiscard() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Record the value of the player on initialisation
        ArrayList<String> valuesAfterDiscards = new ArrayList<>();

        // Discard a card from the players deck
        p.discardCardToDeck();
        valuesAfterDiscards.add(p.getOrderedCards());

        p.discardCardToDeck();
        valuesAfterDiscards.add(p.getOrderedCards());

        p.discardCardToDeck();
        valuesAfterDiscards.add(p.getOrderedCards());

        // Start a new thread for the final discard as if the discardCardToDeck method works
        // as intended the program will hang with the single card in the players hand until
        // more cards are drawn from the left
        Thread processesThread = new Thread(() -> {
            p.discardCardToDeck();
            valuesAfterDiscards.add(p.getOrderedCards());
        });
        processesThread.start();

        String actualOutput = String.valueOf(valuesAfterDiscards);

        // Define actual output

        // Actual output
        assertEquals("[1 2 3 , 1 2 , 1 ]", actualOutput);

        // Interrupt the process
        processesThread.interrupt();

        // Check the players deck now only contains the single, preferred card
        assertEquals(p.getOrderedCards(), "1 ");

        // Define the expected output
        String expected = "\nPlayer number  : 1\n" +
                "Player deck    : 1 \n" +
                "Deck no 2 ldeck: 4 3 2 1 \n" +
                "Deck no 3 rdeck: 4 3 2 1 4 3 2 \n";

        // Check
        assertEquals(expected, String.valueOf(p));
    }

    @org.junit.Test
    @Description("Test to simulate the playGo method")
    public void testPlayGo() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Simulate a round
        p.playGo();

        // Define the expected output
        String expectedOutput = """

                Player number  : 1
                Player deck    : 1 2 3 4\s
                Deck no 2 ldeck: 4 3 2 1\s
                Deck no 3 rdeck: 4 3 2 1\s
                """;

        // Define the actual output
        String actualOutput = String.valueOf(p);

        // Assertion test
        assertEquals(expectedOutput, actualOutput);
    }

    @org.junit.Test
    @Description("Test to check the ToString method is outputting the correct message")
    public void testToString() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(1));}

        // Initialise a new player object
        Player p = createPlayer();

        // Define the expected output
        String expectedOutput = "\nPlayer number  : 1\nPlayer deck    : 1 1 1 1 \nDeck no 2 ldeck: 1 1 1 1 \nDeck no 3 rdeck: 1 1 1 1 \n";

        // Define the actual output
        String actualOutput = String.valueOf(p);

        // Assertion test
        assertEquals(actualOutput, expectedOutput);
    }

    @org.junit.Test
    @Description("Test to check the getPlayerWon method is working correctly")
    public void testAllSameCards() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(1));}

        // Initialise a new player object
        Player p = createPlayer();

        // Check the player has won
        assertTrue(p.getPlayerWon());
    }


    @org.junit.Test
    @Description("Test to check the correct messages are being outputted to the text file")
    public void testLogOutput() throws IOException {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Draw a card from the deck
        p.drawCardFromDeck();

        // Define the file reader
        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player1_output.txt"));

        // Record the first and second line of the text file
        reader.readLine();
        String value2 = reader.readLine();

        assertEquals("player 1 draws a 4 from deck 2", value2);
    }


    @org.junit.Test
    @Description("Test to check that the correct lose output message is being saved to the text file")
    public void testLoseOutput() throws IOException, InterruptedException {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Wait for any other tests to complete their actions with the text file
        Thread.sleep(50);

        // Initialise a new player object
        Player p = createPlayer();

        // Output the lost message to the text file
        p.loseOutput();


        // Define the file reader
        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player1_output.txt"));
        // Record the first and second line of the text file
        reader.readLine();
        String value2 = reader.readLine();

        // Assertion test
        assertEquals("player 1 has informed player 1 that player 1 has won", value2);
    }

    @org.junit.Test
    @Description("Test to check the correct win output message is being saved in the text file")
    public void testWinOutput() throws IOException {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        PrintStream originalStream = System.out;
        PrintStream dummyStream = new PrintStream(new OutputStream(){ public void write(int b) {} });
        System.setOut(dummyStream);

        // Output the win message to the text file
        p.winOutput();

        System.setOut(originalStream);

        // Define the file reader
        BufferedReader reader = new BufferedReader(new FileReader("Logs" + File.separator + "player1_output.txt"));
        // Record the first and second line of the text file
        reader.readLine();
        String value2 = reader.readLine();

        // Assertion test
        assertEquals("player 1 wins", value2);
    }

    @org.junit.Test
    @Description("Test to check the current hand output is correct")
    public void testCurrentHandOutput() {
        // Clear tempCards ArrayList
        this.tempCards.clear();

        // Add new cards to the ArrayList
        for (int i = 4; i > 0; i--) {this.tempCards.add(new Card(i));}

        // Initialise a new player object
        Player p = createPlayer();

        // Assertion test
        assertEquals("1 2 3 4 ", p.getOrderedCards());
    }

    @org.junit.Test
    @Description("Test to check that the playerWon method works when the deck satisfies the requirements")
    public void testSetPlayerWon() {
        // Initialise three temporary decks
        CardDeck temp1 = new CardDeck(1);
        CardDeck temp2 = new CardDeck(2);
        CardDeck temp3 = new CardDeck(3);

        // Add an extra card to the players deck so that they don't win on initialisation
        temp1.placeCardOnBottom(new Card(2));

        // Populate the decks with cards
        for (int i = 0; i < 4; i++) {
            temp1.placeCardOnBottom(new Card(1));
            temp2.placeCardOnBottom(new Card(1));
            temp3.placeCardOnBottom(new Card(1));
        }

        // Initialise a new player object
        Player p = new Player(temp1, temp2, temp3);

        // Draw and discard a card
        p.discardCardToDeck();
        p.drawCardFromDeck();

        // Check if the player has won
        boolean checkWin = p.getPlayerWon();

        // Set if the player has won
        p.setPlayerWon();

        // Assertion test
        assertNotEquals(checkWin, p.getPlayerWon());

    }
}