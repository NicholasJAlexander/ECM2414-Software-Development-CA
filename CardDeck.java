package ContinuousAssessment;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {

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
    }

    Card takeCardFromTop() {
        // take card from the top of the deck
        return this.cards.poll();
    }

    void placeCardOnBottom(Card c) {
        // places card to the bottom of the deck
        this.cards.add(c);
    }

    public String toString(){
        return "Pack values: " + Arrays.toString(this.cards.toArray());
    }

    // not used currently
    static synchronized Card takeAndPlace(CardDeck takeDeck, CardDeck placeDeck, Card c) {
        // places card on place deck (players right)
        placeDeck.cards.add(c);
        // takes card from take deck (players left)
        return takeDeck.cards.poll();
    }

}
