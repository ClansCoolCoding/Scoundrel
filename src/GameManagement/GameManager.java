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
    private boolean fledLastRoom;

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
        this.fledLastRoom = false;
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
            if (input.isEmpty()){
                System.out.println("You have to enter an input");

            }else if (input.equalsIgnoreCase("help")) {
                this.printHelp();

            }else if (input.equalsIgnoreCase("flee")) {
                if (this.fledLastRoom) {
                    System.out.println("You have already fled the last room, so you cant flee this room!");
                }else {
                    this.fledLastRoom = true;

                    for(int i = 0; i < 4; i++){
                        dungeon.add(room[i]);
                        room[i] = dungeon.removeFirst();
                    }

                    System.out.println("You have successfully fled this room!");
                    this.printCurrentGameState();
                }

            }else if (input.equalsIgnoreCase("state")) {
                this.printCurrentGameState();

            }else if (input.equalsIgnoreCase("exit")) {
                return;

            }else if (parseToInt(input.charAt(0)) == -1) {
                System.out.println("You have to enter a number to specify a card [1 - 4], or one of the commands [help, flee, exit]");

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

    private void printHelp() {
        System.out.println("""
                
                You are now asked to enter your next move.
                To specify which card you want to attack or use specify the nubmer of the card.
                The cards are labeled from 1 to 4 from left to right
                The cards are all labled the same
                
                W is a weapon,        seleting this card will make you pick it up
                H is a health potion, selecting it will heal you upt to the amount specified by the card or to your max health
                E is an enemy,        the value specified is his strength
                
                When you select an enemy, you have to specify if you want to fight it with your bare hands [h] or with you weapon [w].
                Fighting an enemy with your bare hands means his strength will get subtracted directly from your health points.
                You may choose to fight an enemy with your weapon only if your weapon is strong enough.
                Your weapon is strong enough if either you just picked it up or the last enemy fought with this weapon has a higher strength value than the enemy you are trying to fight.
                If your weapon is not strong enough (the last beaten enemy was stronger than the current one) the game will tell you.
                When you select an enemy, with no weapon equipped, you will fight it with your bare hands.
                The cards left in the dungeon are specified to the left of the current cards.
                
                You may flee a room with the command flee.
                This will bring you to a new room, while the old room is brought back deep into the dungeon.
                This only works if you havent fled the last room you were in
                
                You loose the dungeon if you have no health points.
                You win the dungeon if you defeat all cards in the dungeon.
                
                So a valid input looks something like: (with out the [])
                [1] -> interact with card in slot 1
                [2w] -> fight the enemy in slot 2 with your weapon
                [3h] -> fight the enemy in slot 3 with your bare hand
                [flee] -> flee this room
                [exit] -> exit the dungeon
                [help] -> get back to this menu
                
                """);
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
        this.fledLastRoom = false;
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
