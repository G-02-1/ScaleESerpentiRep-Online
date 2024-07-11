package SupportingObjects.Cards;

import Exceptions.CardInitializzationException;

import java.io.Serializable;
import java.util.Objects;

public abstract class Consumable implements Cloneable, Serializable {

    private enum Type {
        BENCH, INN, DICES, SPRING, PARKINGTERM;
    }

    protected Type type;

    public Consumable(String token) {
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

    public Consumable(Card target) {
        if(target != null) {
            this.type = target.getType();
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public Consumable clone() {
        try {
            return (Consumable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return a code that identifies the action
     */
    public abstract int triggerEffect();

    public boolean isSTOPOVER() {
        return this.type.equals(Type.BENCH) || this.type.equals(Type.INN);
    }

    public boolean isBENCH() {
        return this.type.equals(Type.BENCH);
    }

    public boolean isINN() {
        return this.type.equals(Type.INN);
    }

    public boolean isPRIZE() {
        return this.type.equals(Type.DICES) || this.type.equals(Type.SPRING);
    }


    public boolean isDICES() {
        return this.type.equals(Type.DICES);
    }

    public boolean isSPRING() {
        return this.type.equals(Type.SPRING);
    }

    public boolean isPARKINGTERM() {
        return this.type.equals(Type.PARKINGTERM);
    }

    @Override
    public String toString() {
        return "Card - " + this.getType().toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Consumable)) return false;
        Consumable card = (Consumable) o;
        return this.getType() == card.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
