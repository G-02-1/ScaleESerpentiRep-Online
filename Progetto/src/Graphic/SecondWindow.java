package Graphic;

import Board.Grid.GridBoard.Board;
import Patterns.StatePackage.State;
import SimulationObject.Simulation;
import SupportingObjects.Cards.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SecondWindow extends JFrame {
    private int dicesNumber, X, Y, NPlayer;
    private boolean CUSTOM, CARDS = false, SPECIALCARD = false, LadderAndSnakeSetter;
    private Board board;
    private ArrayList<String> playersNames = new ArrayList<>();

    private FirstWindow firstWindow;


    public SecondWindow(FirstWindow firstWindow) {

        this.firstWindow = firstWindow;

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

        JButton backButton = new JButton("Back");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(backButton, constraints);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                firstWindow.show();
            }
        });

        setLocationRelativeTo(null);
        add(panel);
    }

    private void showSetSimulationDialog(JFrame parentFrame, boolean custom) {

        JDialog dialog = new JDialog(parentFrame, "Choose the essential field", true);
        dialog.setSize(800, 200);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel widthLabel = new JLabel("Width (4-10):");
        SpinnerModel widhtSpinnerModel = new SpinnerNumberModel(4, 4, 10, 1);
        JSpinner widhtNumberSpinner = new JSpinner(widhtSpinnerModel);
        dialog.add(widthLabel);
        dialog.add(widhtNumberSpinner);

        JLabel heightLabel = new JLabel("Height (5-10):");
        SpinnerModel heightSpinnerModel = new SpinnerNumberModel(5, 5, 10, 1);
        JSpinner heightNumberSpinner = new JSpinner(heightSpinnerModel);
        dialog.add(heightLabel);
        dialog.add(heightNumberSpinner);

        JLabel playersNumberLabel = new JLabel("Players' Number (2-6):");
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
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(this);
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
                    board.putBoardComponent();
                    System.out.println(board);
                    openGameWindow();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);
        dialog.add(buttonPanel);
        dialog.setVisible(true);
    }

    private int heightBasedOnPlayers() {
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
        LadderAndSnakeSetterWindow ladderAndSnakeSetterWindow = new LadderAndSnakeSetterWindow(board, playersNames, dicesNumber, SPECIALCARD, CARDS);
        ladderAndSnakeSetterWindow.show();
    }

    private void showSetCustomSimulationDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Choose the optional field", true);
        dialog.setSize(700, 200);
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
                    board = new Board(X, Y, CUSTOM, CARDS);
                    board.putBoardComponent();
                    System.out.println(board);
                    openGameWindow();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);
        dialog.add(buttonPanel);
        dialog.setVisible(true);
    }

    public void openGameWindow() {
        dispose();
        System.out.println("Prima della finestra di gioco: " + board);
        GamePlayWindow gamePlayWindow = new GamePlayWindow(new Simulation(new Simulation.Builder(this.board, this.playersNames).dicesNumber(this.dicesNumber).SPECIALCARD(this.SPECIALCARD).CARD(this.CARDS)));
        gamePlayWindow.setVisible(true);
    }
}
