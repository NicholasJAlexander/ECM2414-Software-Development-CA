package ContinuousAssessment;

public class Card {

    private int value;

    public int getValue() {return this.value;};

    public Card(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
