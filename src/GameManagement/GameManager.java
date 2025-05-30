package src.GameManagement;

import src.Card.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class GameManager {

    private LinkedList<Card> dungeon;
    private Card[] room;

    private PlayerInfo playerInfo;
    private Scanner scanner;
    private boolean gameIsGoing;
    private boolean dungeonWon;

    public GameManager() {
        this.scanner = new Scanner(System.in);
    }

    public void startNewGame(int initHealth, int initRoomSize) {
        this.playerInfo = new PlayerInfo(initHealth);
        dungeon = generateDungeon();
        room = new Card[initRoomSize];

        for (int i = 0; i < initRoomSize; i++) {
            room[i] = dungeon.removeFirst();
        }

        this.gameIsGoing = true;
        this.dungeonWon = false;
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

        this.gameIsGoing = true;

        return dungeon;
    }

    public void playDungeon() {
        this.printCurrentGameState();

        CardHandler state = CardHandler.SUCCESS;
        String input = "";

        while (gameIsGoing) {
            //Get the input of the player
            System.out.print("What are you doing?: ");
            input = scanner.nextLine();
            input = input.toLowerCase();

            //Check if conditions are met (at least 2 long, having a number in front)
            if (input.equalsIgnoreCase("help")) {
                this.printHelp();

            }else if (input.isEmpty()){
                System.out.println("You have to enter an input");

            }else if (parseToInt(input.charAt(0)) == -1) {
                System.out.println("You have to enter a number to specify a card [1 - 4]");

            }else if (room[parseToInt(input.charAt(0))] == null) {
                System.out.println("You allready have played this card");

            }else {
                //make the move
                Card selectedCard = room[parseToInt(input.charAt(0))];
                if(input.length() == 1) {
                    state = playerInfo.handleCard(selectedCard, CombatType.NOT_TO_BE_SPECIFIED);
                }else {
                    CombatType combatType = parseCombatType(input.charAt(1));
                    state = playerInfo.handleCard(selectedCard, combatType);
                }

                //handle the returned value
                switch (state) {
                    case PROBLEM_NO_COMBAT_TYPE: System.out.println("You have to choose a combat type [w, h]"); break;
                    case PROBLEM_WEAPON_TO_WEAK: System.out.println("Your weapon is too weak for this enemy!"); break;
                    case PROBLEM_NO_HEALTH: {
                        System.out.println("You lost because you have no health left!");
                        this.gameIsGoing = false;
                        break;
                    }
                    default: {
                        System.out.println("Your action was successful");
                        room[parseToInt(input.charAt(0))] = null;
                        this.checkToRefill();
                        this.checkWon();

                        if(dungeonWon) {
                            System.out.println("You won!");
                            this.gameIsGoing = false;
                            return;
                        }

                        this.printCurrentGameState();
                        break;
                    }
                }
            }
        }
    }

    //A way of printing the whole Dungeon
    private void printDungeon() {
        for (Card card : dungeon) {
            System.out.println(card.toString());
        }
    }

    //The print function for the room
    private void printCurrentGameState(){
        System.out.println("------------------------------------------------------------------------");
        System.out.print("[ " + this.dungeon.size() + " ]:   ");
        this.printCurrentRoom();
        System.out.println(playerInfo.toString());
        System.out.println("------------------------------------------------------------------------");
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

    /* TODO */
    private void printHelp() {
        System.out.println("You are now asked to enter your next move.");
        /*System.out.println("To specify your next move you have to provide it in the following format: ");
        System.out.println("First the card you want to pick, specified as a number from 1 to 4");
        System.out.println("The cards are labled in the following format: [1] [2] [3] [4]");
        System.out.println("So if you type in 0");
        System.out.println("You are now asked to enter your next move.");*/
        this.printCurrentGameState();
    }

    private int parseToInt(char c) {
        return switch (c) {
            case '1' -> 0;
            case '2' -> 1;
            case '3' -> 2;
            case '4' -> 3;
            default -> -1;
        };
    }

    private CombatType parseCombatType(char c) {
        return switch (c) {
            case 'w' -> CombatType.WITH_WEAPON;
            case 'h' -> CombatType.BARE_HANDED;
            default -> CombatType.NOT_TO_BE_SPECIFIED;
        };
    }

    private void checkToRefill() {
        int cardsLeft = 0;

        for(int i = 0; i < 4; i++){
            if(room[i] != null){
                cardsLeft++;
            }
        }

        if (cardsLeft <= 1) {
            refillCards();
        }
    }

    private void refillCards() {
        for (int i = 0; i < 4; i++) {
            if(room[i] == null){
                if(dungeon.isEmpty()){
                    return;
                }
                room[i] = dungeon.removeFirst();
            }
        }
        System.out.println("Your room was refilled!");
    }

    private void checkWon() {
        if(dungeon.isEmpty()) {
            boolean emptyRoom = true;

            for (int i = 0; i < 4; i++) {
                if (room[i] != null) {
                    emptyRoom = false;
                    break;
                }
            }

            if (emptyRoom) {
                this.dungeonWon = true;
            }
        }
    }
}
