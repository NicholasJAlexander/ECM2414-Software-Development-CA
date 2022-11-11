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
    Boolean playerWon;

    Player(CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        super(d);
//        this.d = d;
        this.playerNumber = this.deckNumber;
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
        String initialMsg = String.format("player %d initial hand %s", this.getOrderedHand());
        this.logOutput(initialMsg);

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

    void drawCardFromDeck() {
        // takes a card from the players respective deck
        // print drawn card info
        Card c = this.leftDeck.takeCardFromTop();
        String drawsMsg = String.format("player %d draws a %d from deck %d", this.playerNumber, c.getValue(), this.rightDeck.deckNumber);
        logOutput(drawsMsg);
        this.cards.add(c);
    }

    void discardCardToDeck() {
        // cycles players cards to avoid dicarding a prefered card
        while (p.cards.peek().getValue() == p.playerNumber) {
            // removes card from top and places on bottom of deck, this only happens if
            // the card value is the preffered value
            p.cards.add(p.cards.poll());
        }

        // discards a non prefered card
        Card discard = p.cards.poll();
        p.discardCard(discard);

        // print discard info
        this.rightDeck.placeCardOnBottom(discard);
        String discardMsg = String.format("player %d discards a %d from deck %d", this.playerNumber, discard.getValue(), this.leftDeck.deckNumber);
        logOutput(discardMsg);
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

    static synchronized void playGo(Player p) {

        // skip go if players left deck is empyt
        if (p.cards.size() == 0) {return;}

        // draws card from left deck and add to back of players deck
        p.drawCardFromDeck();
        
        //discards non preferred card, places on right deck
        p.discardCardToDeck();

        String currentHandMsg = String.format("player %d current hand is %s",p.playerNumber,p.getOrderedHand());
        p.logOutput(currentHandMsg);
        
        // check if player has won
        p.playerWon = allSameCards(p.cards);

        if (p.playerWon) {
            // player wins here
            String pN = String.format("player %d " + p.playerNumber);
            String msg = p + "wins\n" + pN + "exits\n" + pN + String.format("final hand is %s",p.getOrderedHand());
            p.logOutput(msg);
        }
    }

    int[] getOrderedHand() {
        // orderes hand
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (Card c: this.cards) {
            arr.add(c.getValue());
        }
        Collections.sort(arr);
        return arr.stream().mapToInt(Integer::intValue).toArray();
    }
}
