package SupportingObjects.Cards;

import Exceptions.CardInitializzationException;

public class Card extends Consumable {

    public Card(String token) throws CardInitializzationException {
        super(token);
    }

    public Card(Card target) throws CardInitializzationException {
        super(target);
        if(target != null) {
            this.type = target.getType();
        }
    }

    //Prototype
    @Override
    public Card clone() {
        return new Card((Card) super.clone());
    }

    @Override
    public int triggerEffect() {
        if(this.isPRIZE()) {
            if(this.isDICES()) {
                return 2;
            }
            if(this.isSPRING()) {
                return 3;
            }
        }
        if(this.isSTOPOVER()) {
            if(this.isBENCH()) {
                return 4;
            }
            if(this.isINN()) {
                return 5;
            }
        }
        if(this.isPARKINGTERM()) {
            return 7;
        }
        return -1;
    }
}
