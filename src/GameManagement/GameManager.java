package src.GameManagement;

import src.Card.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class GameManager {
    private LinkedList<Card> dungeon;
    private Card[] room;
    private PlayerInfo playerInfo;

    public GameManager() {}

    public void startNewGame(int initHealth, int initRoomSize) {
        this.playerInfo = new PlayerInfo(initHealth);
        dungeon = generateDungeon();
        room = new Card[initRoomSize];

        for (int i = 0; i < initRoomSize; i++) {
            room[i] = dungeon.removeFirst();
        }
        //this.printDungeon();
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

    public void playDungeon() {
        this.printCurrentGameState();

        /* TODO -- Make the original game loop (card handling has all ready been implemented in the PlayerInfo class) */
    }

    //A way of printing the whole Dungeon
    private void printDungeon() {
        for (Card card : dungeon) {
            System.out.println(card.toString());
        }
    }

    //The print function for the room
    private void printCurrentGameState(){
        System.out.print("[ " + this.dungeon.size() + " ]:   ");
        this.printCurrentRoom();
        System.out.println(playerInfo.toString());
        System.out.println();
    }

    private void printCurrentRoom(){
        for(int i = 0; i < 4; i++){
            if(room[i] == null){
                System.out.print("[ (_, _) ] ");
            }else{
                System.out.print("[ " + room[i].toString() + " ] ");
            }
        }
        System.out.println();
    }
}
