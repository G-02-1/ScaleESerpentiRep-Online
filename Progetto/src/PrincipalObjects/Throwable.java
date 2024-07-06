package PrincipalObjects;

public interface Throwable {

    /**
     * This method simulates the dice's throw
     * @return
     */
    int throwDice();

    /**
     * This method simulates the two dices's throwing
     * @return sum of the two throws
     */
    default int throwDices() {
        return throwDice() + throwDice();
    }
}
