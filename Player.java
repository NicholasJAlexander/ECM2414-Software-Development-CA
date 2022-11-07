package ContinuousAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public class Player extends CardDeck{

    int playerNumber;
    CardDeck leftDeck;
    CardDeck rightDeck;

    Player(int pNumber, CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        super(d);
        this.playerNumber = pNumber;
        this.leftDeck = lDeck;
        this.rightDeck = rDeck;

        
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

    Card drawCard() {
        // takes a card from the players respective deck
        // print drawn card info
        return this.leftDeck.takeCardFromTop();
    }

    void discardCard (Card c) {
        // discards unwanted card
        // print discard info
        this.rightDeck.placeCardOnBottom(c);
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
        this.discardCard(this.deck.poll());

        // draws card and add to players deck
        Card drawn = this.drawCard();
        this.deck.add(drawn);
        
        if (allSameCards(this.deck)) {
            // player wins here
        } else {
            // players go is over
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
