package ContinuousAssessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CardGame {

    Pack p;

    CardGame(int n, String file) throws IOException {
        p = new Pack(n, file);

        dealCards(n, p);
        // players
        // decks 
    }

    void dealCards(int n, Pack p) {
        // deals out the cars in pack

        // ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<CardDeck> individualDecks = new ArrayList<>();


        // init the n players decks
        for (int i = 0; i < n; i++) {
            individualDecks.add(new CardDeck());
        }

        // populate the n decks by placing the card on the bottom and popping the card from the pack
        for (int i = 0; i < n * 4; i++) {
            individualDecks.get(i % n).placeCardOnBottom(p.popCard());
        }

        // give players their cards
//        for (int i = 0; i < n; i++) {
//            playerList.add(new Player())
//        }

        for (CardDeck individualDeck : individualDecks) {
            System.out.println(individualDeck);
        }
        /*
            Pack values: [1, 1, 2, 2]
            Pack values: [1, 1, 2, 2]
            Pack values: [1, 1, 2, 2]
            Pack values: [1, 1, 2, 2]
         */


        // deal cards each player

    }




    public static void main(String args[]) {
        try {
            // Create buffer reader for taking input from console
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

            // Take input of number of players
            System.out.println("Enter the number of desired players here: ");
            int n = 4; //Integer.parseInt(bReader.readLine());

            // Take input of pack file
            System.out.println("Enter the filename of the desired pack here: ");
            String fileName = "pack.txt"; // bReader.readLine();

            // Output to user
            System.out.println("The number of players you have selected are: " + n);
            System.out.println("The name of the file you have selected is: " + fileName);

            // Start new card game
            new CardGame(n, fileName);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    
}
