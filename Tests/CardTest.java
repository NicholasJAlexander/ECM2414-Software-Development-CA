package ContinuousAssessment;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class CardTest {

    @Test
    @Description("Test to check the value of the card being stored in the object")
    void testGetValue() {
        // Initialise a new card with a value 1
        Card c = new Card(1);

        // Test the value stored in the card object
        assertEquals(1, c.getValue());
    }

    @Test
    @Description("Test to check the value of the ToString method")
    void testToString() {
        // Initialise a new card with a value 1
        Card c = new Card(1);

        // Test the value of the ToString method
        assertEquals("1", String.valueOf(c));
    }
}