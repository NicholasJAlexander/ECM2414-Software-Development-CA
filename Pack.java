package ContinuousAssessment;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Pack {

    // has implementation to take check if plain text file is a valid input pack
    // reads the files values into a linked list
    int reqNumOfCards;

    LinkedList<Integer> cards;


    public Pack(int numberOfPlayers, BufferedReader bReader) throws IOException {

        // takes number of players, and the name of file that holds the packs numbers
        this.cards = new LinkedList<>();
        // required number of cards
        this.reqNumOfCards = 8*numberOfPlayers;
        boolean successful;
        String fileName;

        System.out.println("Enter the filename of the desired pack here: ");

        do {
            successful = true;
            this.cards.clear();
            fileName = bReader.readLine();

            // initialise file and reader
            File userInputFile = new File(fileName);
            if (!userInputFile.exists()) {
                System.out.println("Your pack file does not exist, please give a valid pack file");
                successful = false;
                continue;
            }
            BufferedReader packReader = new BufferedReader(new FileReader(userInputFile));

            String currentLine;
            boolean trailingLines = false;

            while ((currentLine = packReader.readLine()) != null) {

                // ignores empty lines at the end of a file
                if (currentLine == "") {
                    trailingLines = true;
                    continue;
                } else if (trailingLines) {
                    System.out.println("Your pack file is invalid, please give a valid pack file");
                    // causes a skip to next loop
                    successful = false;
                    break;
                }

                try {
                    // add the integer to the list
                    this.cards.add(Integer.parseInt(currentLine));
                } catch (NumberFormatException e) {
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

    // getter
    public LinkedList<Integer> getCards() {
        return cards;
    }

    // pops the card from the list
    public Card popCard() {
        return new Card(this.cards.pop());
    }

//    void readFile() throws IOException {
//        // reads .txt file into a deck
//    }

    @Override
    public String toString(){
        return "Pack values: " + Arrays.toString(cards.toArray());
    }


}
