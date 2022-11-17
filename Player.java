package ContinuousAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends CardDeck implements Runnable{

    static AtomicInteger winner = new AtomicInteger();

    int playerNumber;
    CardDeck leftDeck;
    CardDeck rightDeck;
    String saveLocation;

    public Boolean getPlayerWon() { return playerWon; }

    Boolean playerWon;
    int initialDeckSize;

    Player(CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        super(d);
        this.initialDeckSize = d.size();
        this.playerNumber = this.deckNumber;
        this.leftDeck = lDeck;
        this.rightDeck = rDeck;
        this.saveLocation = "Logs" + File.separator + "player" + this.playerNumber + "_output.txt";
        this.playerWon = allSameCards(this.cards);

        String initialMsg = String.format("player %d initial hand %s", this.playerNumber, this.getOrderedHand());
        // initialise logs folder and file
        try {
            // create file variable
            File logFile = new File(this.saveLocation);
            logFile.getParentFile().mkdirs();
            if (!logFile.createNewFile()) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation));
                //System.out.println("save location: " + this.saveLocation);
                // initialise file
                bWriter.write(initialMsg);
                bWriter.close();
            }
        } catch (IOException e) {
            System.out.printf("An output file for player" + this.playerNumber + " has not been created.");
        }
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
    }

    void drawCardFromDeck() {
        // takes a card from the players respective deck
        // print drawn card info
        Card c = this.leftDeck.takeCardFromTop();
        if (c == null) {
            // there is no cards in the left deck
            return;
        }
        String drawsMsg = String.format("player %d draws a %d from deck %d", this.playerNumber, c.getValue(), this.leftDeck.deckNumber);
        logOutput(drawsMsg);
        this.cards.add(c);
    }

    void discardCardToDeck() {
        // cycles players cards to avoid discarding a preferred card
        while (this.cards.peek().getValue() == this.playerNumber) {
            // removes card from top and places on bottom of deck, this only happens if
            // the card value is the preferred value
            this.cards.add(this.cards.poll());
        }

        // discards a non preferred card
        Card discard = this.cards.poll();
//        p.discardCard(discard);

        // print discard info
        this.rightDeck.placeCardOnBottom(discard);
        String discardMsg = String.format("player %d discards a %d to deck %d", this.playerNumber, discard.getValue(), this.rightDeck.deckNumber);
        logOutput(discardMsg);
    }

    void logOutput(String msg) {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation, true));
            bWriter.newLine();
            //System.out.println("output: " + msg);
            bWriter.write(msg);
            bWriter.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }

    public boolean playGo() {

        // checks if there is a winner
        if (winner.intValue()!=0) {
            return false;
        }

        // skip go if players left deck is empty
        if (this.leftDeck.cards.size() == 0) {return true;}

        //System.out.println("\n");
        // draws card from left deck and add to back of players deck
        this.drawCardFromDeck();
        
        //discards non preferred card, places on right deck
        this.discardCardToDeck();

        // outputs current had to file
        this.currentHandOutput();

        // sets playerWon attribute according to whether the player as won
        this.setPlayerWon();

        if (this.playerWon) {
            // if this player first to win, not the case if winner != 0
            if (winner.intValue() == 0) {
                // output win to file
                this.winOutput();
                // set winner
                winner.set(this.playerNumber);
            }
        }
        // this return value is used to stop the thread if player wins (makes a players go atomic)
        return !this.playerWon;
    }


    @Override
    public String toString() {
        return "\nPlayer number  : " + this.playerNumber +
                "\nPlayer deck    : " + this.getOrderedHand() +
                "\nDeck no " + this.leftDeck.deckNumber + " ldeck: " + this.leftDeck +
                "\nDeck no " + this.rightDeck.deckNumber + " rdeck: " + this.rightDeck +
                "\n";
    }

    void loseOutput() {
        int winnerNumber = winner.intValue();
        String pN = String.format("player " + this.playerNumber);
        String msg = "player " + winnerNumber + " has informed " + pN +
                     " that player " + winnerNumber + " has won\n" +
                     pN + " exits\n" +
                     pN + String.format(" hand: %s",this.getOrderedHand());
        this.logOutput(msg);
    }

    void winOutput() {
        String pN = String.format("player " + this.playerNumber);
        String msg = pN + " wins\n" + pN + " exits\n" + pN + String.format(" final hand is %s", this.getOrderedHand());
        this.logOutput(msg);
    }

    void currentHandOutput() {
        String msg = String.format("player %d current hand is %s",this.playerNumber,this.getOrderedHand());
        this.logOutput(msg);
    }


    void setPlayerWon() {
        this.playerWon = allSameCards(this.cards);
    }

    @Override
    public void run() {
        // playGo returns false if player had won
        while (this.playGo());

        // outputs deck contents to file
        this.leftDeck.outputToFile();

        if (winner.intValue() == this.playerNumber) {
            System.out.println(String.format("player %d wins",this.playerNumber));
        } else {
            this.loseOutput();
        }
    }
}
