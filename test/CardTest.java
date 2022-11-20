import ContinuousAssessment.Card;
import jdk.jfr.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class done using JUnit 5
 */
public class CardTest {

    @org.junit.Test
    @Description("Test to check the value of the card being stored in the object")
    public void testGetValue() {
        // Initialise a new card with a value 1
        Card c = new Card(1);

        // Test the value stored in the card object
        assertEquals(1, c.getValue());
    }

    @org.junit.Test
    @Description("Test to check the value of the ToString method")
    public void testToString() {
        // Initialise a new card with a value 1
        Card c = new Card(1);

        // Test the value of the ToString method
        assertEquals("1", String.valueOf(c));
    }
}