package ContinuousAssessment;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Pack{

    // has implementation to take check if plain text file is a valid input pack
    // reads the files values into a linked list 

    // number of players
    int nOPs;

    LinkedList<Integer> cards;


    public Pack(int n, String file) throws IOException {
        try {

            // takes number of players, and also the name of file that holds the packs numbers
            this.cards = new LinkedList<>();
            this.nOPs = n;

            // the required amount of cards
            int requiredAmount = this.nOPs * 8;

            System.out.println(file);

            // initialise file and reader
            File userInputFile = new File(file);
            BufferedReader bReader = new BufferedReader(new FileReader(userInputFile));

            // create current line variable
            String currentLine;
            int i = 0;

            // check if the end of the file has been reached
            while ((currentLine = bReader.readLine()) != null) {
                // check if there are too many cards in the pack
                if (i == requiredAmount) {
                    System.out.println("The file exceeded the amount of cards needed for the game");
                } else {
                    // add the integer to the list
                    this.cards.add(Integer.parseInt(currentLine));
                }
                i++;
            }

            if (i < requiredAmount) {
                System.out.println("The file didn't contain enough cards");
            }

            bReader.close();
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e);
        }
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
