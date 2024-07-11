package Board.Graphic;

import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Patterns.StatePackage.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class SecondWindow extends JFrame {
    private State state;
    private int dicesNumber, X, Y, NPlayer;
    private boolean CUSTOM, CARDS = false, SPECIALCARD = false, LadderAndSnakeSetter;
    private Board board;
    private ArrayList<String> playersNames = new ArrayList<>();


    public SecondWindow() {
        // Set window properties
        JFrame mainFrame = new JFrame();
        setSize(500, 200);
        setTitle("Are you a daredevil? Choose custom simulation!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton standardSimulationButton = new JButton("Standard Simulation");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(standardSimulationButton, constraints);

        standardSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSetSimulationDialog(mainFrame, false);
            }
        });

        JButton customSimulationButton = new JButton("Custom Simulation");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(customSimulationButton, constraints);

        customSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSetSimulationDialog(mainFrame, true);
            }
        });

        // Add other components and logic as needed

        setLocationRelativeTo(null);
        add(panel);
    }

    private void showSetSimulationDialog(JFrame parentFrame, boolean custom) {
        // Creazione della finestra di dialogo
        JDialog dialog = new JDialog(parentFrame, "Choose the essential field", true);
        dialog.setSize(800, 200);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel widthLabel = new JLabel("Width (4-15):");
        SpinnerModel widhtSpinnerModel = new SpinnerNumberModel(4, 4, 15, 1);
        JSpinner widhtNumberSpinner = new JSpinner(widhtSpinnerModel);
        dialog.add(widthLabel);
        dialog.add(widhtNumberSpinner);

        JLabel heightLabel = new JLabel("Height (5-15):");
        SpinnerModel heightSpinnerModel = new SpinnerNumberModel(5, 5, 15, 1);
        JSpinner heightNumberSpinner = new JSpinner(heightSpinnerModel);
        dialog.add(heightLabel);
        dialog.add(heightNumberSpinner);

        JLabel playersNumberLabel = new JLabel("Players' Number (1-6):");
        SpinnerModel playersSpinnerModel = new SpinnerNumberModel(2, 2, 6, 1);
        JSpinner playersNumberSpinner = new JSpinner(playersSpinnerModel);
        dialog.add(playersNumberLabel);
        dialog.add(playersNumberSpinner);

        JLabel diceNumberLabel = new JLabel("Dice's Number (1-2):");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2, 1);
        JSpinner diceNumberSpinner = new JSpinner(spinnerModel);
        dialog.add(diceNumberLabel);
        dialog.add(diceNumberSpinner);

        JCheckBox LSsetterCheckBox = new JCheckBox("Do you want to set the Ladders' and Snakes' position?");
        dialog.add(LSsetterCheckBox);

        // Creazione del pulsante "OK"
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int width = (int) widhtNumberSpinner.getValue();
                int height = (int) heightNumberSpinner.getValue();
                int playersNumber = (int) playersNumberSpinner.getValue();
                int diceNumber = (int) diceNumberSpinner.getValue();
                boolean wantToSet = LSsetterCheckBox.isSelected();

                X = width;                                    System.out.println("Width: " + X);
                Y = height;                                   System.out.println("Height: " + Y);
                NPlayer = playersNumber;                      System.out.println("Players' Number: " + NPlayer);
                dicesNumber = diceNumber;                     System.out.println("Dice's Number: " + dicesNumber);
                CUSTOM = custom;                              System.out.println("Custom? " + (custom?"yes":"no"));
                LadderAndSnakeSetter = wantToSet;             System.out.println("Want to set? " + (LadderAndSnakeSetter?"yes":"no"));

                dialog.dispose();
                showSetPlayersNamesDialog(parentFrame);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        dialog.add(buttonPanel);

        setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void showSetPlayersNamesDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Choose the name of the players", true);
        dialog.setSize(400, heightBasedOnPlayers());
        dialog.setLayout(new GridLayout(NPlayer + 1, 2));

        JTextField[] nameFields = new JTextField[NPlayer];
        for (int i = 0; i < NPlayer; i++) {
            JLabel label = new JLabel("Player " + (i + 1) + ":");
            nameFields[i] = new JTextField();
            dialog.add(label);
            dialog.add(nameFields[i]);
        }

        String[] playerNames = new String[NPlayer];
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < NPlayer; i++) {
                    playerNames[i] = nameFields[i].getText();
                    playersNames.add(playerNames[i]);   System.out.println("Player " + (i + 1) + ": " + playerNames[i]);
                }
                dialog.dispose();
                if(CUSTOM) {
                    showSetCustomSimulationDialog(parentFrame);
                } else if (LadderAndSnakeSetter) {
                    parentFrame.dispose();
                    showSetLadderAndSnakeDialog(parentFrame);
                }
                else {
                    parentFrame.dispose();
                    board = new Board(X, Y, CUSTOM, CARDS);
                    openGameWindow();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);
        dialog.add(buttonPanel);

        setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private int heightBasedOnPlayers() {
        //TODO
        switch (NPlayer) {
            case 2:
                return 150;
            case 3:
                return 170;
            case 4:
                return 200;
            case 5:
                return 250;
            default:
                return 300;
        }
    }

    private void showSetLadderAndSnakeDialog(JFrame parentFrame) {
        this.board = new Board(this.X, this.Y, this.CUSTOM, this.CARDS);


        int bcNumber = this.board.getBOARDCOMPONENTNUMBER();
        JDialog dialog = new JDialog(parentFrame, "Choose the number of ladder and snake", true);
        dialog.setSize(400, heightBasedOnBCNumber());
        dialog.setLayout(new GridLayout(bcNumber + 1, 2));

        JComboBox<String>[] positionFields = new JComboBox[bcNumber];
        ArrayList<String> ladderPositions = new ArrayList<>();
        ArrayList<String> snakePositions = new ArrayList<>();
        for (int i = 0; i < bcNumber / 2; i++) {
            JLabel label = new JLabel("Ladder " + (i + 1) + ":");
            positionFields[i] = new JComboBox<>(option());
            dialog.add(label);
            dialog.add(positionFields[i]);
        }
        for (int i = 0; i < bcNumber / 2; i++) {
            JLabel label = new JLabel("Snake " + (i + 1) + ":");
            positionFields[i] = new JComboBox<>(option());
            dialog.add(label);
            dialog.add(positionFields[i]);
        }

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            for (int i = 0; i < bcNumber; i++) {
                String selectedItem = (String) positionFields[i].getSelectedItem();
                if (i < bcNumber / 4) {
                    ladderPositions.add(selectedItem);
                } else {
                    snakePositions.add(selectedItem);
                }
            }

            // Print the results
            System.out.println("Ladder positions: " + ladderPositions);
            System.out.println("Snake positions: " + snakePositions);

            dialog.dispose();
            // Add your logic for the next steps here
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);
        dialog.add(buttonPanel);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    private Vector<String> option() {
        Vector<String> toReturn = new Vector<>();
        ArrayList<Cell> assignable = this.board.assignableCells();
        for(Cell c : assignable) {
            toReturn.add(c.toString());
        }
        return toReturn;
    }


    private int heightBasedOnBCNumber() {
        return 500;
//        switch () {
//            case 2:
//                return 150;
//            case 3:
//                return 170;
//            case 4:
//                return 200;
//            case 5:
//                return 250;
//            default:
//                return 300;
//        }
    }

    private void showSetCustomSimulationDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Choose the optional field", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 1));

        JCheckBox cardsCheckBox = new JCheckBox("Do you want to include cards?");
        JCheckBox specialCardsCheckBox = new JCheckBox("Do you want to include the 'parking term' card? (If you select this, you must select also cards inclusion)");
        dialog.add(cardsCheckBox);
        dialog.add(specialCardsCheckBox);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean hasCards = cardsCheckBox.isSelected();
                boolean hasSpecialCards = specialCardsCheckBox.isSelected();

                CARDS = hasCards;                                System.out.println("Cards: " + hasCards);
                SPECIALCARD = hasSpecialCards;                   System.out.println("Special Cards: " + hasSpecialCards);

                dialog.dispose();
                if(LadderAndSnakeSetter) {
                    showSetLadderAndSnakeDialog(parentFrame);
                }
                else {
                    parentFrame.dispose();
                    openGameWindow();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);
        dialog.add(buttonPanel);

        setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void openGameWindow() {
        // Create and show the SecondWindow

        GameWindow gameWindow = new GameWindow(board, playersNames, SPECIALCARD);
        gameWindow.setVisible(true);
    }
    public void openLadderAndSnakeSetterWindow() {

        // Create and show the SecondWindow
        LadderAndSnakeSetterWindow ladderAndSnakeSetterWindow = new LadderAndSnakeSetterWindow(board, playersNames, CARDS, SPECIALCARD);
        ladderAndSnakeSetterWindow.setVisible(true);
    }
}
