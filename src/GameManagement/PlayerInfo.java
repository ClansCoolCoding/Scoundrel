package src.GameManagement;

import src.Card.Card;
import src.Card.CardType;

public class PlayerInfo {

    private int health;
    private int initHealth;
    private Card weapon;
    private Card lastBeaten;

    public PlayerInfo(int initHealth) {
        this.health = initHealth;
        this.initHealth = initHealth;
        this.weapon = null;
        this.lastBeaten = null;
    }

    //this is the function actually handling the card
    public CardHandler handleCard(Card card, CombatType combatType) {
        switch (card.cardType) {
            case HEALING -> {
                setHealth(Math.min(initHealth, getHealth() + card.value));
            }

            case WEAPON -> {
                setWeapon(card);
                setLastBeaten(null);
            }

            case ENEMY -> {
                switch (combatType) {
                    case BARE_HANDED -> {
                        setHealth(Math.max(0, getHealth() - card.value));
                        if(getHealth() == 0){
                            return CardHandler.PROBLEM_NO_HEALTH;
                        }
                    }

                    case WITH_WEAPON -> {
                        if(getLastBeaten().value <= card.value){
                            return CardHandler.PROBLEM_WEAPON_TO_WEAK;
                        }else{
                            int damage = card.value - getWeapon().value;
                            setHealth(Math.max(0, getHealth() - damage));
                            if(getHealth() == 0){
                                return CardHandler.PROBLEM_NO_HEALTH;
                            }
                        }
                    }
                }
            }
        }

        return CardHandler.SUCCESS;
    }

    @Override
    public String toString() {
        return "Health: " + health +", Weapon: [ " + this.getWeapon().value + " ], Last Beaten: [ " + this.getLastBeaten().value + " ]";
    }

    //GETTERS AND SETTERS FOR
    private int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    private Card getWeapon() {

        if(this.weapon == null){
            return new Card(0, CardType.WEAPON);
        }

        return weapon;
    }

    private void setWeapon(Card weapon) {
        this.weapon = weapon;
    }

    private Card getLastBeaten() {

        if(this.lastBeaten == null){
            return new Card(0, CardType.ENEMY);
        }

        return lastBeaten;
    }
    private void setLastBeaten(Card lastBeaten) {
        this.lastBeaten = lastBeaten;
    }
}
