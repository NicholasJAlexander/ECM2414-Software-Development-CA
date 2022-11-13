package ContinuousAssessment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class CardTest {

    @Test
    void getValue() {
        Card c = new Card(1);
        assertEquals(1, c.getValue());
    }

    @Test
    void testToString() {
        Card c = new Card(1);
        assertEquals("1", String.valueOf(c));
    }
}