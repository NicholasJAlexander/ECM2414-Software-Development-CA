import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import java.io.OutputStream;
import java.io.PrintStream;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CardTest.class,
        CardDeckTest.class,
        PackTest.class,
        PlayerTest.class
})

/**
 * Test class done using JUnit 4.13.1
 *
 * @author James Calnan & Nicholas Alexander
 * @version 1.0
 */
public class RunTests {
    public static void main(String[] args) {
        // Notify user that tests are being run
        System.out.println("Running tests");

        // Capture any outputs not relevant
        PrintStream originalStream = System.out;
        PrintStream dummyStream = new PrintStream(new OutputStream(){ public void write(int b) {} });
        System.setOut(dummyStream);

        // Run tests
        Result testResults = JUnitCore.runClasses(RunTests.class);

        // Reassign original print stream
        System.setOut(originalStream);

        System.out.println("\n\nTest Results: ");

        // Notify user of failed tests
        System.out.println(testResults.getFailures().size() + " failures");
        for (Failure f : testResults.getFailures()) {
            System.out.println(f.toString());
        }

        System.out.println("\nTests successful: " + testResults.wasSuccessful());
    }
}
