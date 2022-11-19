package ContinuousAssessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CardGame {

    // list of player in the game
    private ArrayList<Player> players;

    CardGame(int n, Pack pack) throws IOException {

        // deals the cards in pack p, creates the n players and n decks
        dealCards(n, pack);

        /*
        for (Player individualPlayer : this.players) {
            System.out.println(individualPlayer);
        }
        for (CardDeck individualDeck : this.decks) {
            System.out.println(individualDeck);
        }
        */

        // this needs to go before starting the threads, otherwise players will start playing,
        // even if one of the player initially wins
        for (Player p: this.players) {
            // checks if the player has won
            if (p.getPlayerWon()) {
                //System.out.println("Player: " + ip.getPlayerNumber() + " has won");
                // outputs that the player has won
                p.winOutput();
                break;
            }
        }

        // creates a thread for each player
        for (Player p: this.players) {
            Thread thread = new Thread(p);
            thread.start();
        }
    }


    void dealCards(int n, Pack pack) {
        // deals out the cards in pack, pack should have 8n cards in it

        // ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<CardDeck> individualDecks = new ArrayList<>();

        // initialise list of players
        this.players = new ArrayList<>();

        // init the 2n decks, first n for players, final n for decks
        // player decks numbered 1..n, decks also numbered 1..n
        for (int i = 0; i < 2*n; i++) {
            individualDecks.add(new CardDeck( 1 + i%n));
        }


        // populates 2n decks by placing the card on the bottom and popping the card from the pack,
        // first n decks are for the players (dealt to first), last n decks are for the decks between players
        for (int index=0; index<2*n; index = index+n) {
            // deals to players, then to decks
            for (int i = 0; i < n * 4; i++) {
                individualDecks.get(index +(i % n)).placeCardOnBottom(pack.takeCardFromTop());
            }
        }

        // creates a player, using the players respective deck of cards, and the decks to the
        // left and right of the player
        for (int i = 0; i<n; i++) {
            CardDeck playersDeck = individualDecks.get(i);
            CardDeck leftDeck = individualDecks.get(n + i);
            CardDeck rightDeck = individualDecks.get(n + (i+1)%n);
            this.players.add(
                // playersDeck already has a deckNumber - this becomes the player's playerNumber
                new Player(
                    playersDeck,
                    leftDeck,
                    rightDeck
                )
            );
        }
    }

    public static void main(String[] args) {
        try {
            // Create buffer reader for taking input from console
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

            // Take input of number of players
            System.out.println("Enter the number of desired players here: ");
            int n = Integer.parseInt(bReader.readLine());

            // Take input of pack file
            Pack p = new Pack(n,bReader, "");
            bReader.close();
            // Output to user
            //System.out.println("The number of players you have selected are: " + n);
            //System.out.println("The name of the file you have selected is: " + fileName);

            // Start new card game
            new CardGame(n, p);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
