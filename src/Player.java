import ContinuousAssessment.Card;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends CardDeck implements Runnable{

    // this is used to allow players to check if another player has won
    // it is the player number of the winning player
    static AtomicInteger winner = new AtomicInteger();
    // the player's number
    private int playerNumber;
    // deck to the left of player, player draws from this deck
    private CardDeck leftDeck;
    // deck to the right of player, player discards to this deck
    private CardDeck rightDeck;
    // true if payer has "won", false otherwise
    private boolean playerWon;
    // queue is implemented to avoid the game stagnating
    private Queue<Card> cards;


    Player(CardDeck d, CardDeck lDeck, CardDeck rDeck) {
        this.cards = d.cards;
        this.playerNumber = d.deckNumber;
        this.leftDeck = lDeck;
        this.rightDeck = rDeck;
        // this the location/file of the player's output file
        this.saveLocation = "Logs" + File.separator + "player" + this.playerNumber + "_output.txt";
        // checks if the player has already won, and sets playerWon accordingly
        setPlayerWon();
        if (this.playerWon && Player.winner.intValue() == 0) {
            Player.winner.set(this.playerNumber);
        }

        // initialise logs folder and file
        try {
            // create file variable
            File logFile = new File(this.saveLocation);
            // creates directory and file iff the path and/or file doesn't exist
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation));
            // Initial message for the player
            String initialMsg = String.format("player %d initial hand %s",
                    this.playerNumber,
                    this.getOrderedCards()
            );
            // writes initial message to file
            bWriter.write(initialMsg);
            bWriter.close();

        } catch (IOException e) {
            System.out.print("An output file for player" + this.playerNumber + " has not been created.");
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    boolean drawCardFromDeck() {
        // takes a card from the players respective deck
        Card c = this.leftDeck.takeCardFromTop();
        //checks if there was a card in the left deck
        if (c != null) {
            // there is no cards in the left deck
            String drawsMsg = String.format("player %d draws a %d from deck %d", this.playerNumber, c.getValue(), this.leftDeck.deckNumber);
            logOutput(drawsMsg);
            this.cards.add(c);
        }
        // returns true if c is a Card, i.e. the left deck wasn't empty
        return c != null;
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

        this.rightDeck.placeCardOnBottom(discard);

        // print discard info
        String discardMsg = String.format("player %d discards a %d to deck %d", this.playerNumber, discard.getValue(), this.rightDeck.deckNumber);
        logOutput(discardMsg);
    }

    void logOutput(String msg) {
        try {
            // creates buffer reader
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.saveLocation, true));
            //System.out.println("output: " + msg);
            // writes msg to a new line in the player's output file
            bWriter.newLine();
            bWriter.write(msg);
            bWriter.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
    }
    // implements a players go
    public boolean playGo() {

        // checks if there is a winner, returns false if there is
        if (winner.intValue()!=0) {
            return false;
        }

        /*  draws card from left deck and add to back of players deck
            draw method returns false if players left deck is empty
            if the left deck is empty then this if clause will return true,
            thus causing the player to play again (see run() method)
            */
        if (!this.drawCardFromDeck()) {return true;}
        
        //discards non preferred card, places on right deck
        this.discardCardToDeck();

        // outputs current had to file
        this.currentHandOutput();

        // sets playerWon attribute according to whether the player as won
        this.setPlayerWon();

        // checks if player has won
        if (this.playerWon) {
            // checks if the player is first to win, winner = 0 if no one has won yet
            // sets winner to playerNumber if winner = 0
            winner.compareAndSet(0,this.playerNumber);
            if (winner.intValue() == this.playerNumber) {
                // output win to file
                this.winOutput();
            }
        }
        // this return value is used to stop the thread if player wins
        return !this.playerWon;
    }

    @Override
    public String toString() {
        // string contains this player's number, ordered cards, left and right deck cards
        return "\nPlayer number  : " + this.playerNumber + // players number
                "\nPlayer deck    : " + this.getOrderedCards() + // players ordered cards
                "\nDeck no " + this.leftDeck.deckNumber + " ldeck: " + this.leftDeck + // left deck cards
                "\nDeck no " + this.rightDeck.deckNumber + " rdeck: " + this.rightDeck + // right deck cards
                "\n";
    }

    void loseOutput() {
        // outputs lose message to this player's file
        int winnerNumber = winner.intValue();
        String pN = String.format("player " + this.playerNumber);
        String msg = "player " + winnerNumber + " has informed " + pN +
                " that player " + winnerNumber + " has won\n" +
                pN + " exits\n" +
                pN + String.format(" hand: %s",this.getOrderedCards());
        this.logOutput(msg);
    }

    public void winOutput() {
        // outputs win message to this player's file
        String pN = String.format("player " + this.playerNumber);
        String msg = pN + " wins\n" +
                pN + " exits\n" +
                pN + String.format(" final hand is %s", this.getOrderedCards());
        this.logOutput(msg);
        // prints to terminal that the player has won
        System.out.printf("player %d wins",this.playerNumber);
    }

    void currentHandOutput() {
        // outputs players current hand to player's file
        String msg = String.format("player %d current hand is %s",this.playerNumber,this.getOrderedCards());
        this.logOutput(msg);
    }

    void setPlayerWon() {
        // checks if player has won, and sets this.playerWon accordingly
        int val = this.cards.peek().getValue();

        for (Card c: this.cards) {
            // function returns if cards are not the same
            if (c.getValue() != val) {
                return;
            }
        }
        // if the above loop finishes, then the player has a winning hand
        this.playerWon = true;
    }

    public boolean getPlayerWon() {
        // getter for playerWon
        return this.playerWon;
    }

    String getOrderedCards() {
        // orders hand
        ArrayList<Integer> arr = new ArrayList<>();
        for (Card c: this.cards) {
            arr.add(c.getValue());
        }
        Collections.sort(arr);
        String cardsStr = "";
        for (Integer i: arr) {
            cardsStr += i+" ";
        }
        return cardsStr;
    }

    // overrides run() method in Runnable
    @Override
    public void run() {
        // playGo returns false if player had won, or if another player has one
        while (this.playGo());

        // outputs deck contents to deck's output file
        this.leftDeck.outputToFile();

        // outputs the losing information to the player's file, if they have lost
        if (winner.intValue() != this.playerNumber) {
            // if the player is not the winner, outputs lose message to player's file
            this.loseOutput();
        }
    }
}
