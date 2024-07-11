package SupportingObjects.Dice;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {

    Random dice;

    public Dice() {
        this.dice = new Random();
    }

    public int throwDice() {
        int result = dice.nextInt(1,7); //excluding 7
        System.out.println(result);
        return result;
    }
}
