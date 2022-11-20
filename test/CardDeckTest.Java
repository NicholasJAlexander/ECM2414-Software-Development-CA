import ContinuousAssessment.Card;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class done using JUnit 5
 *
 * @author James Calnan & Nicholas Alexander
 * @version 1.0
 */
class CardDeckTest {


    /**
     * @return new deck with cards in it
     */
    CardDeck initDeck() {
        // Initialise new deck object with a deckId of 1
        CardDeck d = new CardDeck(1);

        // Populate the deck with cards using the placeCardOnBottom method
        for (int i = 1; i < 5; i++) {d.placeCardOnBottom(new Card(i));}

        // Return the deck
        return d;
    }

    @Test
    @Description("Test to check the ToString method output the correct data")
    void testToString() {
        // Initialise a new deck for testing
        CardDeck testDeck = initDeck();

        // Assertion test to check the value of the deck
        assertEquals(String.valueOf(testDeck), "1 2 3 4 ");
    }

    @Test
    @Description("Test to check cards are being taken from the top of the correct deck")
    void testTakeCardFromTop() {
        // Initialise a new deck for testing
        CardDeck testDeck = initDeck();

        // Record value of the deck on initialisation
        String valueBefore = String.valueOf(testDeck);

        // Take a card from the top of the deck
        testDeck.takeCardFromTop();

        // Define the expected output and the actual output
        String expectedOutput = "2 3 4 ";
        String actualOutput = String.valueOf(testDeck);

        // Assertion tests
        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }

    @Test
    @Description("Test to check cards are being placed on the bottom of the correct deck")
    void testPlaceCardOnBottom() {
        // Initialise a new deck for testing
        CardDeck testDeck = initDeck();

        // Record value of the deck on initialisation
        String valueBefore = String.valueOf(testDeck);

        // Place a new card of the bottom of the deck
        testDeck.placeCardOnBottom(new Card(10));

        // Define the expected output and the actual output
        String expectedOutput = "1 2 3 4 10 ";
        String actualOutput = String.valueOf(testDeck);

        // Assertion tests
        assertEquals(expectedOutput, actualOutput);
        assertNotEquals(valueBefore, actualOutput);
    }


}