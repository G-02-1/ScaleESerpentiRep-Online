package Board.Graphic;

import Board.Grid.GridBoard.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LadderAndSnakeSetterWindow extends JFrame {
    private final int numRows, numCols;
    private Board board;
    private ArrayList<String> playersNames;
    private boolean CARDS, SPECIALCARDS;
    private JTextField[] ladderStartFields, ladderEndFields, snakeStartFields, snakeEndFields;
    private JButton nextButton;

    public LadderAndSnakeSetterWindow(Board board, ArrayList<String> playersNames, boolean CARDS, boolean SPECIALCARDS) {
        this.numRows = board.getY();
        this.numCols = board.getX();
        this.board = board;
        this.playersNames = playersNames;
        this.CARDS = CARDS;
        this.SPECIALCARDS = SPECIALCARDS;

        int numLadders = this.board.getBOARDCOMPONENTNUMBER() / 2;
        int numSnakes = this.board.getBOARDCOMPONENTNUMBER() / 2;

        setTitle("Choose Ladder and Snake position");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(numRows * 50, numCols * 50);
        setLayout(new GridLayout(numRows, numCols));

        setLayout(new GridLayout(numLadders + numSnakes + 1, 4));


        // Campi per le scale
        JLabel ladderLabel = new JLabel("Ladders:");
        ladderStartFields = new JTextField[numLadders];
        ladderEndFields = new JTextField[numLadders];
        for (int i = 0; i < numLadders; i++) {
            ladderStartFields[i] = new JTextField();
            ladderEndFields[i] = new JTextField();
            add(new JLabel("Start:"));
            add(ladderStartFields[i]);
            add(new JLabel("End:"));
            add(ladderEndFields[i]);
        }

        // Campi per i serpenti
        JLabel snakeLabel = new JLabel("Snakes:");
        snakeStartFields = new JTextField[numSnakes];
        snakeEndFields = new JTextField[numSnakes];
        for (int i = 0; i < numSnakes; i++) {
            snakeStartFields[i] = new JTextField();
            snakeEndFields[i] = new JTextField();
            add(new JLabel("Start:"));
            add(snakeStartFields[i]);
            add(new JLabel("End:"));
            add(snakeEndFields[i]);
        }

        // Bottone "Next"
        nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonClickListener());

        // Aggiungi componenti alla finestra
        add(ladderLabel);
        add(new JLabel()); // Spazio vuoto
        add(snakeLabel);
        add(new JLabel()); // Spazio vuoto
        for (int i = 0; i < numLadders; i++) {
            add(new JLabel("Ladder " + (i + 1)));
            add(new JLabel()); // Spazio vuoto
            add(new JLabel("Snake " + (i + 1)));
            add(new JLabel()); // Spazio vuoto
        }
        add(nextButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class NextButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Ottieni i valori inseriti per scale e serpenti
            for (int i = 0; i < ladderStartFields.length; i++) {
                int ladderStart = Integer.parseInt(ladderStartFields[i].getText());
                int ladderEnd = Integer.parseInt(ladderEndFields[i].getText());
                System.out.println("Ladder " + (i + 1) + ": Start " + ladderStart + ", End " + ladderEnd);
            }
            for (int i = 0; i < snakeStartFields.length; i++) {
                int snakeStart = Integer.parseInt(snakeStartFields[i].getText());
                int snakeEnd = Integer.parseInt(snakeEndFields[i].getText());
                System.out.println("Snake " + (i + 1) + ": Start " + snakeStart + ", End " + snakeEnd);
            }
        }
    }
}
