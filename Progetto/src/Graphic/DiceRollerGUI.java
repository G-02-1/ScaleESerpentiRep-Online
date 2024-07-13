package Graphic;

import SupportingObjects.Dice.Dice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceRollerGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulatore di lancio del dado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        ImageIcon[] diceFaces = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            diceFaces[i] = new ImageIcon("src/Graphic/Dices immage/dice" + (i + 1) + ".png");
        }

        JButton rollButton = new JButton("Lancia il dado");
        JLabel resultLabel = new JLabel("Risultato: ");
        JLabel diceImageLabel = new JLabel();

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simula il lancio del dado (genera un numero casuale tra 1 e 6)
                Dice dice = new Dice();
                int result = dice.throwDice();
                resultLabel.setText("Risultato: " + result);

                // Mostra l'immagine corrispondente alla faccia del dado
                diceImageLabel.setIcon(diceFaces[result - 1]);
            }
        });

        JPanel panel = new JPanel();
        panel.add(rollButton);
        panel.add(resultLabel);
        panel.add(diceImageLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
}