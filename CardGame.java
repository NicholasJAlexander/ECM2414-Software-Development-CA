package ContinuousAssessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CardGame {

    Pack p;
    ArrayList<Players> players;
    ArratList<CardDecks> decks;

    CardGame(int n, String file) throws IOException {
        p = new Pack(n, file);

        dealCards(n, p);
        // players
        // decks 
    }

    void dealCards(int n, Pack p) {
        // deals out the cards in pack, pack should have 8n cards in it

        // ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<CardDeck> individualDecks = new ArrayList<>();

        // init the 2n decks, first n for players, final n for decks
        // player decks numbered 0..n, decks also numbered 0..n
        for (int i = 0; i < 2*n; i++) {
            individualDecks.add(new CardDeck(i%n));
        }

        // populates 2n decks by placing the card on the bottom and popping the card from the pack,
        // first n decks are for the players (dealt to first), last n decks are for the decks between players
        for (int index=0; index<2*n; index = index+n) {
            // deals to players, then to decks
            for (int i = 0; i < n * 4; i++) {
                individualDecks.get(index +(i % n)).placeCardOnBottom(p.popCard());
            }
        }

        for (int i = 0; i<n; i++) {
            CardDeck playersDeck = individualDecks.get(i);
            CardDeck leftDeck = individualDecks.get(n + i);
            CardDeck rightDeck = individualDecks.get(n + (i+1)%n);
            this.players.add(
                // playersDeck already has a deckNuber - this is the players number
                new Player(
                    d=playersDeck,
                    lDeck = leftDeck,
                    rDeck = rightDeck
                )
            );
            // adds deck to games deck list
            this.decks.add(leftDeck);
        }


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
