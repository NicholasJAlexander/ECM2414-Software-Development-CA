package ContinuousAssessment;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

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


    /**
     * Method to initialise a new text file with 32 numbers in it
     * and then initialises a new Pack object with 4 players
     * @throws IOException
     */
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

        testPack = new Pack(4, bReader, saveLocation);
        bReader.close();
    }

    @Test
    @Description("Test the value of the ToString method")
    void testToString() throws IOException {
        // Initialise the pack file
        initFile();

        String cardsStr = "";
        for (int c: temp) {
            cardsStr += c+" ";
        }

        // Check the value
        assertEquals(String.valueOf(testPack), "Pack card values: " + cardsStr);
    }

}