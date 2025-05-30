package src;

import src.GameManagement.*;

import java.util.Scanner;

public class Main {

    private static Scanner scn = new Scanner(System.in);

    public static void main(String[] agrs){
        GameManager gameManager = new GameManager();
        boolean goOn = true;

        while (goOn) {
            gameManager.startNewGame(20, 4);

            gameManager.playDungeon();

            goOn = askForContinuation();
        }
    }

    //ask the player for the input [y/n] in order to continue to the next game
    private static boolean askForContinuation() {
        String input = "test";

        while(input.toLowerCase().charAt(0) != 'y' && input.toLowerCase().charAt(0) != 'n'){
            System.out.println("Do you want to continue by playing a new dungeon? [y / n]: ");
            input = scn.nextLine();
            if(input.isEmpty()){
                input = "test";
            }
        }

        return input.toLowerCase().charAt(0) == 'y';
    }

}
