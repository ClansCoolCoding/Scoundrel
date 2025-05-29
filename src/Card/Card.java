package src.Card;

public class Card {
    public int value;
    public CardType cardType;

    public Card(int value, CardType cardType) {
        this.value = value;
        this.cardType = cardType;
    }

    public String toString() {
        return "(" + cardType + ", " + value + ")";
    }

    //get a deck full of the necessary cards
    public static Card[] getNewDeck(){
        Card[] deck = new Card[44];

        for(int i = 2; i < 11; i++){
            deck[(i-2)*4 + 0] = new Card(i, CardType.HEALING);
            deck[(i-2)*4 + 1] = new Card(i, CardType.WEAPON);
            deck[(i-2)*4 + 2] = new Card(i, CardType.ENEMY);
            deck[(i-2)*4 + 3] = new Card(i, CardType.ENEMY);
        }

        for(int i = 11; i < 15; i++){
            deck[36 + (i - 11)*2 + 0] = new Card(i, CardType.ENEMY);
            deck[36 + (i - 11)*2 + 1] = new Card(i, CardType.ENEMY);
        }

        return deck;
    }
}
