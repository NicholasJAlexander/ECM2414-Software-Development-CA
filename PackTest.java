package ContinuousAssessment;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class done using JUnit 5
 */
class PackTest {

    Pack testPack;
    ArrayList<Integer> temp = new ArrayList<>();

    void initFile() throws IOException {
        Random r = new Random();
        r.setSeed(54367853);

        String saveLocation = "testPack.txt";

        BufferedWriter bWriter = new BufferedWriter(new FileWriter(saveLocation));
        for (int i = 1; i <= 32; i++) {
            int val = r.nextInt(32);
            bWriter.write(val + "\n");
            temp.add(val);
        }

        bWriter.close();

        testPack = new Pack(4, "testPack.txt");
    }

    @Test
    void getCards() throws IOException {
        initFile();

        assertEquals(testPack.getCards(), temp);

    }

    @Test
    void popCard() throws IOException {
        initFile();

        LinkedList<Integer> beforePop = testPack.getCards();

        Card val = testPack.popCard();

        LinkedList<Integer> afterPop = testPack.getCards();

        assertEquals(val.getValue(), new Card(temp.get(0)).getValue());
        assertEquals(beforePop, afterPop);

    }

    @Test
    void testToString() throws IOException {
        initFile();

        assertEquals(String.valueOf(testPack), "Pack values: " + temp);
    }
}