package ContinuousAssessment;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {

    private int deckNumber;

    // a queue avoids the game stagnating for the player
    protected Queue<Card> deck = new LinkedList<>();

    CardDeck (Queue<Card> d, int dNumber) {
        this.deck = d;
        this.deckNumber = dNumber;
    }

    // empty constructor to initialise new list
    CardDeck(int i){
        this.deck = new LinkedList<>();
        this.deckNumber = i;
    }

    CardDeck (CardDeck d) {
        this.deck = d.deck;
        this.deckNumber = d.deckNumber;
    }

    Card takeCardFromTop() {
        // take card from the top of the deck
        return deck.poll();
    }

    void placeCardOnBottom(Card c) {
        // places card to the bottom of the deck
        deck.add(c);
    }

    public String toString(){
        return "Pack values: " + Arrays.toString(deck.toArray());
    }

}
