package Board.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationDialog extends JDialog {
    private int X;
    private int Y;
    private int NPlayer;
    private int dicesNumber;
    private boolean CUSTOM;
    private boolean LadderAndSnakeSetter;

    public SimulationDialog(JFrame parentFrame, boolean custom) {
        super(parentFrame, "Choose the essential field", true);
        setSize(800, 200);
        setLayout(new GridLayout(5, 2));

        JLabel widthLabel = new JLabel("Width (4-15):");
        SpinnerModel widthSpinnerModel = new SpinnerNumberModel(4, 4, 15, 1);
        JSpinner widthNumberSpinner = new JSpinner(widthSpinnerModel);
        add(widthLabel);
        add(widthNumberSpinner);

        JLabel heightLabel = new JLabel("Height (5-15):");
        SpinnerModel heightSpinnerModel = new SpinnerNumberModel(5, 5, 15, 1);
        JSpinner heightNumberSpinner = new JSpinner(heightSpinnerModel);
        add(heightLabel);
        add(heightNumberSpinner);

        JLabel playersNumberLabel = new JLabel("Players' Number (1-6):");
        SpinnerModel playersSpinnerModel = new SpinnerNumberModel(2, 2, 6, 1);
        JSpinner playersNumberSpinner = new JSpinner(playersSpinnerModel);
        add(playersNumberLabel);
        add(playersNumberSpinner);

        JLabel diceNumberLabel = new JLabel("Dice's Number (1-2):");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2, 1);
        JSpinner diceNumberSpinner = new JSpinner(spinnerModel);
        add(diceNumberLabel);
        add(diceNumberSpinner);

        JCheckBox LSsetterCheckBox = new JCheckBox("Do you want to set the Ladders' and Snakes' position?");
        add(LSsetterCheckBox);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int width = (int) widthNumberSpinner.getValue();
                int height = (int) heightNumberSpinner.getValue();
                int playersNumber = (int) playersNumberSpinner.getValue();
                int diceNumber = (int) diceNumberSpinner.getValue();
                boolean wantToSet = LSsetterCheckBox.isSelected();

                X = width;
                Y = height;
                NPlayer = playersNumber;
                dicesNumber = diceNumber;
                CUSTOM = custom;
                LadderAndSnakeSetter = wantToSet;

                dispose();
                showSetPlayersNamesDialog(parentFrame);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        add(buttonPanel);
        setVisible(true);
    }

    private void showSetPlayersNamesDialog(JFrame parentFrame) {
        // Implement your logic for setting player names here
    }

    // Other methods related to your simulation can go here
}
