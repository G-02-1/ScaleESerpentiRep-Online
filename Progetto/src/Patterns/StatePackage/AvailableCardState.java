package Patterns.StatePackage;

import SupportingObjects.Cards.Card;

public class AvailableCardState extends State {
    public AvailableCardState(Card card) {
        super(card);
    }

    @Override
    public void setActiveState() {
        //Not a Player but a Card  -> Do nothing
    }

    @Override
    public void setBlockedState(int turns) {
        //Not a Player but a Card  -> Do nothing
    }

    @Override
    public boolean move() {
        //Not a Player but a Card -> return false
        return false;
    }

    @Override
    public void setAvailableState() {
        if(this.o instanceof Card) {
            Card card = (Card) o;
            card.changeState(new AvailableCardState(card));
        }
    }

    @Override
    public void setNotAvailableState() {
        //Do nothing
    }
}
