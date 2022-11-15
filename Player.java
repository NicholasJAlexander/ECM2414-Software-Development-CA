package ContinuousAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends CardDeck implements Runnable{

    int playerNumber;
    CardDeck leftDeck;
    CardDeck rightDeck;
    String saveLocation;
    Boolean playerWon;
    int initialDeckSize;

    Player(CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        super(d);
//        this.d = d;
        this.initialDeckSize = d.size();
        this.playerNumber = this.deckNumber + 1;
        this.leftDeck = lDeck;
        this.rightDeck = rDeck;
        this.saveLocation = "Logs" + File.separator + "player" + this.playerNumber + "_output.txt";
        this.playerWon = allSameCards(this.cards);

        // initialise logs folder and file
        try {
            // create file variable
            File logFile = new File(this.saveLocation);
            logFile.getParentFile().mkdirs();
            if (!logFile.createNewFile()) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation));
                System.out.println("save location: " + this.saveLocation);
                // initialise file
                bWriter.write("Player " + this.playerNumber + " created.");
                bWriter.close();
            }
        } catch (IOException e) {
            System.out.printf("An output file for player" + this.playerNumber + " has not been created.");
        }
        // outputs initial message
        String initialMsg = String.format("player %d initial hand %s", this.playerNumber, this.getOrderedHand());
        this.logOutput(initialMsg);

    }

    static boolean allSameCards(Queue<Card> cs) {
        // loop hand queue and check if all the cards are the same number
        // the loop queue and remove all card of number
        int val = cs.peek().getValue();

        for (Card c: cs) {
            if (c.getValue() != val) {
                return false;
            }
        }
        return true;
    };

    void writeFile() {

    }

    void drawCardFromDeck() {
        // takes a card from the players respective deck
        // print drawn card info
        Card c = this.leftDeck.takeCardFromTop();
        if (c == null) {
            // theres no cards in the left deck
            return;
        }
        String drawsMsg = String.format("player %d draws a %d from deck %d", this.playerNumber, c.getValue(), this.rightDeck.deckNumber);
        logOutput(drawsMsg);
        this.cards.add(c);
    }

    void discardCardToDeck(Player p) {
        // cycles players cards to avoid dicarding a prefered card
        while (p.cards.peek().getValue() == p.playerNumber) {
            // removes card from top and places on bottom of deck, this only happens if
            // the card value is the preffered value
            p.cards.add(p.cards.poll());
        }

        // discards a non prefered card
        Card discard = p.cards.poll();
//        p.discardCard(discard);

        // print discard info
        this.rightDeck.placeCardOnBottom(discard);
        String discardMsg = String.format("player %d discards a %d from deck %d", this.playerNumber, discard.getValue(), this.leftDeck.deckNumber);
        logOutput(discardMsg);
    }

    private void logOutput(String msg) {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation, true));
            bWriter.newLine();
            System.out.println("output: " + msg);
            bWriter.write(msg);
            bWriter.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }


    public static synchronized void playGo(Player p) {

        // skip go if players left deck is empty
        if (p.leftDeck.cards.size() == 0) {return;}
        System.out.println("\n");
        // draws card from left deck and add to back of players deck
        p.drawCardFromDeck();
        
        //discards non preferred card, places on right deck
        p.discardCardToDeck(p);

        String currentHandMsg = String.format("player %d current hand is %s",p.playerNumber,p.getOrderedHand());
        p.logOutput(currentHandMsg);
        
        // check if player has won
        p.playerWon = allSameCards(p.cards);

        if (p.playerWon && p.initialDeckSize == p.cards.size()) {
            // player wins here
            String pN = String.format("player " + p.playerNumber);
            String msg = "Player " + p.playerNumber + " wins\n" + pN + " exits\n" + pN + String.format(" final hand is %s",p.getOrderedHand());
            p.logOutput(msg);
        }
    }

    ArrayList<Integer> getOrderedHand() {
        // orderes hand
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (Card c: this.cards) {
            arr.add(c.getValue());
        }
        Collections.sort(arr);
        return arr;
    }

    @Override
    public String toString() {
        return "\nPlayer number  : " + this.playerNumber +
                "\nPlayer deck    : " + this.getOrderedHand() +
                "\nDeck no " + this.leftDeck.deckNumber + " ldeck: " + this.leftDeck +
                "\nDeck no " + this.rightDeck.deckNumber + " rdeck: " + this.rightDeck +
                "\n";
    }


    @Override
    public void run() {
        // thread
        while (!this.playerWon) {
            playGo(this);
        }
    }
}
