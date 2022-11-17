package ContinuousAssessment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CardDeck {

    String saveLocation;
    int deckNumber;


    // a queue avoids the game stagnating for the player
    protected Queue<Card> cards = new LinkedList<>();

    CardDeck (Queue<Card> d, int dNumber) {
        this.cards = d;
        this.deckNumber = dNumber;
    }

    // empty constructor to initialise new list
    CardDeck(int i){
        this.cards = new LinkedList<>();
        this.deckNumber = i;
    }

    CardDeck (CardDeck d) {
        this.cards = d.cards;
        this.deckNumber = d.deckNumber;
        this.saveLocation = d.saveLocation;
    }

    synchronized Card takeCardFromTop() {
        // take card from the top of the deck
        return this.cards.poll();
    }

    synchronized void placeCardOnBottom(Card c) {
        // places card to the bottom of the deck
        this.cards.add(c);
    }

    public int size() {
        return this.cards.size();
    }

    public String toString(){
        return "" + Arrays.toString(this.cards.toArray());
    }

    public Queue<Card> getCards() {
        return cards;
    }

    protected void outputToFile() {
        String saveLocation = "Logs" + File.separator + "deck" + this.deckNumber + "_output.txt";

        try {
            // create file variable
            File logFile = new File(saveLocation);
            logFile.getParentFile().mkdirs();

            if (!logFile.createNewFile()) {
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(saveLocation));
                //System.out.println("save location: " + saveLocation);
                // initialise file, not needed
                bWriter.write("deck" + this.deckNumber + " contents: " + this.getOrderedHand());
                bWriter.close();
            }
        } catch (IOException e) {
            System.out.printf("An output file for deck" + this.deckNumber + " has not been created.");
        }
    }

    String getOrderedHand() {
        // orders hand
        ArrayList<Integer> arr = new ArrayList<>();
        for (Card c: this.cards) {
            arr.add(c.getValue());
        }
        Collections.sort(arr);
        String hand = "";
        for (Integer i: arr) {
            hand += i+" ";
        }

        return hand;
    }

    void logOutput(String msg) {
        try {
            System.out.println(this.saveLocation);
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation, true));
            bWriter.newLine();
            System.out.println("output: " + msg);
            bWriter.write(msg);
            bWriter.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }

}
