package ContinuousAssessment;

import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {

    int deckNumber;

    // a queue avoids the game stagnating for the player
    protected Queue<Card> deck = new LinkedList<>();

    CardDeck (Queue<Card> d, int dNumber) {
        this.deck = d;
        this.deckNumber = dNumber;
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

}
