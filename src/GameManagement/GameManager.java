package src.GameManagement;

import src.Card.*;

import java.util.Collections;
import java.util.LinkedList;

public class GameManager {

    private int health;
    private LinkedList<Card> dungeon;

    public GameManager() {
        this.health = 20;
        dungeon = generateDungeon();
        this.printDungeon();
    }

    //generates a new Deck
    private LinkedList<Card> generateDungeon() {
        LinkedList<Card> dungeon = new LinkedList<>();
        Card[] newGame = Card.getNewDeck();

        //get the dungeon from the array
        Collections.addAll(dungeon, newGame);

        //shuffle the array
        Collections.shuffle(dungeon);

        return dungeon;
    }

    //A way of printing the whole Dungeon
    private void printDungeon() {
        for (Card card : dungeon) {
            System.out.println(card.toString());
        }
    }

}
