package src.Card;

public class Card {
    protected int value;
    protected Type type;

    public Card(int value, Type type) {
        this.value = value;
        this.type = type;
    }

    public String toString() {
        return "(" + type + ", " + value + ")";
    }

    //get a deck full of the necessary cards
    public static Card[] getNewDeck(){
        Card[] deck = new Card[44];

        for(int i = 2; i < 11; i++){
            deck[(i-2)*4 + 0] = new Card(i, Type.HEALING);
            deck[(i-2)*4 + 1] = new Card(i, Type.WEAPON);
            deck[(i-2)*4 + 2] = new Card(i, Type.ENEMY);
            deck[(i-2)*4 + 3] = new Card(i, Type.ENEMY);
        }

        for(int i = 11; i < 15; i++){
            deck[36 + (i - 11)*2 + 0] = new Card(i, Type.ENEMY);
            deck[36 + (i - 11)*2 + 1] = new Card(i, Type.ENEMY);
        }

        return deck;
    }
}
