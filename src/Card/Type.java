package src.Card;

public enum Type {
    HEALING("H"),
    WEAPON("W"),
    ENEMY("E");

    public final String label;

    private Type(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
