package SupportingObjects.Cards;

import Exceptions.CardInitializzationException;
import Patterns.StatePackage.State;

public class Card extends CardAB {
    private State state;

    public Card(String token) throws CardInitializzationException {
        super(token);
    }

    public Card(Card target) throws CardInitializzationException {
        super(target);
        if(target != null) {
            this.type = target.getType();
        }
    }

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


    public void changeState(State state) {
        this.state = state;
    }
}
