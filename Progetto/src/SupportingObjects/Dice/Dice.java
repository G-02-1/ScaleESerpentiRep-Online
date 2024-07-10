package SupportingObjects.Dice;

import java.util.Random;

public class Dice {

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
