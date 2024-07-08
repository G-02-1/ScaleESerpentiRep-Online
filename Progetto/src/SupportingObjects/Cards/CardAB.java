package SupportingObjects.Cards;

import Exceptions.CardInitializzationException;
import java.util.Objects;

public abstract class CardAB implements Consumable {

    private enum Type {
        BENCH, INN, DICES, SPRING, PARKINGTERM;
    }

    protected Type type;

    public CardAB(String token) {
        switch (token) {
            case "BENCH":
                this.type = Type.BENCH;
                break;
            case "INN":
                this.type = Type.INN;
                break;
            case "DICES":
                this.type = Type.DICES;
                break;
            case "SPRING":
                this.type = Type.SPRING;
                break;
            case "PARKINGTERM":
                this.type = Type.PARKINGTERM;
                break;
            default:
                throw new CardInitializzationException("Invalid token: " + token);
        }
    }

    public CardAB(Card target) {
        if(target != null) {
            this.type = target.getType();
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public CardAB clone() {
        try {
            return (CardAB) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isSTOPOVER() {
        return this.type.equals(Type.BENCH) || this.type.equals(Type.INN);
    }

    protected boolean isBENCH() {
        return this.type.equals(Type.BENCH);
    }

    protected boolean isINN() {
        return this.type.equals(Type.INN);
    }

    protected boolean isPRIZE() {
        return this.type.equals(Type.DICES) || this.type.equals(Type.SPRING);
    }


    protected boolean isDICES() {
        return this.type.equals(Type.DICES);
    }

    protected boolean isSPRING() {
        return this.type.equals(Type.SPRING);
    }

    protected boolean isPARKINGTERM() {
        return this.type.equals(Type.PARKINGTERM);
    }

    @Override
    public String toString() {
        return "Card: " + this.getType().toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof CardAB)) return false;
        CardAB card = (CardAB) o;
        return this.getType() == card.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
