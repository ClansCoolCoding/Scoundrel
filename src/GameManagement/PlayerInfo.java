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
                        if (this.getWeapon() == null) {
                            return this.handleCard(card, CombatType.BARE_HANDED);
                        } else if(getLastBeaten() != null && getLastBeaten().value <= card.value){
                            return CardHandler.PROBLEM_WEAPON_TO_WEAK;
                        } else {
                            int damage = Math.max(0, card.value - getWeapon().value);
                            setHealth(Math.max(0, getHealth() - damage));
                            setLastBeaten(card);
                            if(getHealth() == 0){
                                return CardHandler.PROBLEM_NO_HEALTH;
                            }
                        }
                    }

                    default -> {
                        if (this.getWeapon() == null) {
                            return this.handleCard(card, CombatType.BARE_HANDED);
                        } else {
                            return CardHandler.PROBLEM_NO_COMBAT_TYPE;
                        }
                    }
                }
            }
        }

        return CardHandler.SUCCESS;
    }

    @Override
    public String toString() {
        if (this.getWeapon() == null) {
            return "Health: " + health +", Weapon: you dont have a weapon -> you might pick one up";
        }else if (this.getLastBeaten() == null) {
            return "Health: " + health +", Weapon: [ " + this.getWeapon().value + " ], Last Beaten: you havent used this weapon yet";
        }
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
        return weapon;
    }

    private void setWeapon(Card weapon) {
        this.weapon = weapon;
    }

    private Card getLastBeaten() {
        return lastBeaten;
    }
    private void setLastBeaten(Card lastBeaten) {
        this.lastBeaten = lastBeaten;
    }
}
