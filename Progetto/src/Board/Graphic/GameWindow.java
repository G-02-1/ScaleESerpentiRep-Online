package Board.Graphic;

import Board.Grid.GridBoard.Board;
import SupportingObjects.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private final Board board;
    private final ArrayList<String> playerNames;
    private final boolean SPECIALCARDS;

    public GameWindow(Board board, ArrayList<String> playerNames, boolean SPECIALCARDS) {
        this.board = board;
        this.playerNames = playerNames;
        this.SPECIALCARDS = SPECIALCARDS;

        // Create a JPanel to hold the game board
        JPanel gamePanel = new JPanel(new GridLayout(board.getY(), board.getX()));

        // Populate the game board with player names
        for (int y = 0; y < board.getY(); y++) {
            for (int x = 0; x < board.getX(); x++) {
                //String playerName = playerNames.get(y * board.getX() + x);
                JLabel cellLabel = new JLabel("" + this.board.getCell(new Position(y, x)).getNumber() + "", SwingConstants.CENTER);
                gamePanel.add(cellLabel);
            }
        }

        // Add the game panel to the JFrame
        add(gamePanel);

        // Set JFrame properties (e.g., size, visibility, etc.)
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Example usage:
        Board sampleBoard = new Board(10, 10, true, true); // Create a 7x6 board
        ArrayList<String> samplePlayerNames = new ArrayList<>();
        for (int i = 0; i < 42; i++) {
            samplePlayerNames.add("Player" + (i + 1));
        }
        new GameWindow(sampleBoard, samplePlayerNames, true);
    }
}
