import ContinuousAssessment.Card;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pack extends CardDeck {

    // has implementation to take check if plain text file is a valid input pack
    // reads the files values into a linked list
    private int reqNumOfCards;

    public Pack(int numberOfPlayers, BufferedReader bReader, String testFile) throws IOException {
        // takes number of players, and the name of file that holds the packs numbers

        // required number of cards
        this.reqNumOfCards = 8*numberOfPlayers;
        // this is used to check if parsing of pack file has been successful, if value is true when checked,
        // the parsing has been successful (up to the point of checking)
        boolean successful;
        // name of the pack file
        String fileName;

        System.out.println("Enter the filename of the desired pack here: ");

        do {
            // assume the parsing is successful
            successful = true;
            // clear the cards list - might contain cards from previous loop
            this.cards.clear();
            // read file name from the bReader
            fileName = testFile == "" ? bReader.readLine() : testFile;
            // initialise file and reader
            File userInputFile = new File(fileName);
            if (!userInputFile.exists()) {
                System.out.println("Your pack file does not exist, please give a valid pack file");
                // parsing has failed - skip to next loop
                successful = false;
                continue;
            }
            // init BufferReader (file definitely exists at this point)
            BufferedReader packReader = new BufferedReader(new FileReader(userInputFile));

            String currentLine;
            // this is used to avoid invalidating a valid pack file that has empty lines at the end of the file
            boolean trailingEmptyLines = false;

            while ((currentLine = packReader.readLine()) != null) {

                if (currentLine == "") {
                    trailingEmptyLines = true;
                    // go to next loop
                    continue;
                } else if (trailingEmptyLines) {
                    // if currentline != "", and trailingEmptyLines is true, then the pack file
                    // has an empty line in the middle of it, and is invalid
                    System.out.println("Your pack file is invalid, please give a valid pack file");
                    // successful is checked after this while loop, to check if the pack is invalid
                    successful = false;
                    // breaks out of current while loop because the pack is invalid
                    break;
                }

                try {
                    // add the integer to the list
                    // if currentLine can't be an int, a NumberFormatException is thrown
                    this.cards.add(new Card(Integer.parseInt(currentLine)));
                } catch (NumberFormatException e) {
                    // NumberFormatException is caught the pack is invalid
                    System.out.println("Your pack file is invalid, please give a valid pack file");
                    // causes a skip to next loop
                    successful = false;
                    break;
                }
            }

            if (!successful) {
                // try again
                continue;
            }

            if (this.cards.size() != this.reqNumOfCards) {
                String msg = String.format(
                        "Your pack file is invalid, it has %d cards, but should have %d cards. " +
                                "Please give a valid pack file.",
                        this.cards.size(),
                        this.reqNumOfCards);
                System.out.println(msg);
                successful = false;
            }

        } while (!successful);
    }

    @Override
    public String toString(){
        String cardsStr = "";
        for (Card c: this.cards) {
            cardsStr += c.getValue()+" ";
        }
        return "Pack card values: " + cardsStr;
    }
}
