package src.GameManagement;

public class GameHelper {
    public static void printHelp() {
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
    }
}
