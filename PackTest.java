package ContinuousAssessment;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
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
        BufferedReader bReader = new BufferedReader(new FileReader(saveLocation));

        testPack = new Pack(4, bReader);
        bReader.close();
    }

    @Test
    void testToString() throws IOException {
        initFile();

        assertEquals(String.valueOf(testPack), "Pack values: " + temp);
    }
}