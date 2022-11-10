package ContinuousAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends CardDeck{

    int playerNumber;
    CardDeck leftDeck;
    CardDeck rightDeck;
    String saveLocation;

    Player(CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        super(d);
//        this.d = d;
        this.playerNumber = this.deckNumber;
        this.leftDeck = lDeck;
        this.rightDeck = rDeck;
        this.saveLocation = "Logs" + File.separator + "player" + this.playerNumber + "_output.txt";

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

    }

    static boolean allSameCards(Queue<Card> cs) {
        // loop hand queue and check if all the cards are the same number
        // the loop queue and remove all card of number 
        Card prevCard = cs.peek();
        boolean same = true;
        for (Card c: cs) {
            same &= (c == prevCard);
            prevCard = c;
        }
        return same;
    };

    void writeFile() {

    }

    Card drawCard() {
        // takes a card from the players respective deck
        // print drawn card info
        Card c = this.leftDeck.takeCardFromTop();
        String drawsmsg = String.format("player %d draws a %d from deck %d", this.playerNumber, c.getValue(), this.rightDeck.deckNumber);
        logOutput(drawsmsg);
        return c;
    }

    void discardCard (Card c) {
        // discards unwanted card
        // print discard info
        this.rightDeck.placeCardOnBottom(c);
        String discardmsg = String.format("player %d discards a %d from deck %d", this.playerNumber, c.getValue(), this.leftDeck.deckNumber);
        logOutput(discardmsg);
    }

    private void logOutput(String msg) {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation, true));
            bWriter.newLine();
            bWriter.write(msg);
            bWriter.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }

    void playGo() {

        //the order of drawing and discarding cards doesn't matter

        // cycles players cards to avoid dicarding a prefered card
        while (this.deck.peek().getValue() == this.playerNumber) {
            // removes card from top and places on bottom of deck, this only happens if
            // the card value is the preffered value
            this.deck.add(this.deck.poll());
        }
        // discards a non prefered card
        Card discard = this.deck.poll();
        this.discardCard(discard);

        // draws card and add to players deck
        Card drawn = this.drawCard();
        this.deck.add(drawn);
        

        if (allSameCards(this.deck)) {
            // player wins here
            String p = String.format("player %d " + this.playerNumber);
            String msg = p + "wins\n" + p + "exits\n" + p + String.format("current hand is %s",this.getOrderedHand());
            logOutput(msg);
        }
    }

    int[] getOrderedHand() {
        // orderes hand
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (Card c: this.deck) {
            arr.add(c.getValue());
        }
        Collections.sort(arr);
        return arr.stream().mapToInt(Integer::intValue).toArray();
    }
}
