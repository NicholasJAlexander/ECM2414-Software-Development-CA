import ContinuousAssessment.Card;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {

    // location of output file for object
    String saveLocation;
    // the deck's number
    int deckNumber;

    // queue is implemented to avoid the game stagnating
    protected Queue<Card> cards;

    // constructor for CardDeck, has only a deckNumber - no cards
    public CardDeck(int i) {
        this.cards = new LinkedList<>();
        this.deckNumber = i;
    }
    // used to create
    CardDeck () {
        this.cards = new LinkedList<>();
    }

    synchronized Card takeCardFromTop() {
        // take card from the top of the deck
        return this.cards.poll();
    }

    synchronized void placeCardOnBottom(Card c) {
        // places card to the bottom of the deck
        this.cards.add(c);
    }

    /*
    public int size() {
        return this.cards.size();
    }

     */

    public String toString(){
        // string contains this decks cards (not ordered)
        String cardsStr = "";
        for (Card c: this.cards) {
            cardsStr += c.getValue()+" ";
        }
        return cardsStr;
    }

    /*
    public Queue<Card> getCards() {
        // getter for cards
        return cards;
    }

     */

    protected void outputToFile() {
        String saveLocation = "Logs" + File.separator + "deck" + this.deckNumber + "_output.txt";
        String outputMsg = "deck" + this.deckNumber + " contents: " + this;
        try {
            // create file variable
            File logFile = new File(saveLocation);

            // creates directory and/or file iff the path and/or file doesn't exist
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(saveLocation));
            // outputs decks cards to output file
            bWriter.write(outputMsg);
            bWriter.close();

        } catch (IOException e) {
            System.out.printf("An output file for deck" + this.deckNumber + " has not been created.");
        }
    }
}
