package ContinuousAssessment;

import java.util.LinkedList;

public class Pack{

    // has implementation to take check if plain text file is a valid input pack
    // reads the files values into a linked list 

    // number of players
    int nOPs;

    LinkedList<Integer> cards;

    Pack(int n, String filename) {
        // takes number of players, and also the name of file that holds the packs numbers
        this.nOPs = n;
    }

    void readFile() {
        // reads .txt file into a deck
    }

}
