package src.Card;

public enum CardType {
    HEALING("H"),
    WEAPON("W"),
    ENEMY("E");

    public final String label;

    private CardType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
