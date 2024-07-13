package Graphic;

import Board.Components.BoardComponent;
import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.SpecialCell;
import Board.Grid.GridCells.StandardCell;
import SupportingObjects.Dice.Dice;
import SupportingObjects.Position;
import SupportingObjects.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TestGameW extends JPanel {
    private JFrame frame;
    private JButton[][] gridButtons;
    private int gridSizeX, gridSizeY;
    private Board board;
    private Cell[][] cells;


    public TestGameW(Board board) {
        this.board = board;
        int x = board.getX();
        int y = board.getY();
        this.cells = new Cell[x][y];
        gridSizeX = x;
        gridSizeY = y;
        gridButtons = new JButton[x][y];

        System.out.println(this.board.getBOARDCOMPONENTNUMBER());

        frame = new JFrame("Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon[] diceFaces = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            diceFaces[i] = new ImageIcon("src/Graphic/Dices immage/dice" + (i + 1) + ".png");
        }

        JPanel gridPanel = new JPanel(new GridLayout(x, y));
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //Populate the matrix to match the cells on the board
                cells[i][j] = this.board.getCell(new Position(i, j));

                if(cells[i][j] instanceof SpecialCell) {
                    gridButtons[i][j] = new JButton("" + cells[i][j].getNumber() + ": " + cells[i][j].toString());
                } else {
                    gridButtons[i][j] = new JButton(""+cells[i][j].getNumber()+"");
                }
                //gridButtons[i][j].setEnabled(false); //All the cell aren't clickable

                gridButtons[i][j].addActionListener(new TestGameW.CellClickListener(i, j));
                gridPanel.add(gridButtons[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {

            //TODO
            System.out.println("Next button clicked");
        });

        JButton throwButton = new JButton("Throw");
        JLabel resultLabel = new JLabel("Result: ");
        JLabel diceImageLabel = new JLabel();

        throwButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dice dice = new Dice();
                int result = dice.throwDice();
                resultLabel.setText("Result: " + result);

                diceImageLabel.setIcon(diceFaces[result - 1]);
            }
        });

        JPanel diceImagePanel = new JPanel(new BorderLayout());
        diceImagePanel.add(diceImageLabel, BorderLayout.CENTER);

        // Create the main content panel (top-left + right)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(gridPanel, BorderLayout.WEST);
        contentPanel.add(diceImagePanel, BorderLayout.EAST);

        buttonPanel.add(nextButton);
        buttonPanel.add(throwButton);
        buttonPanel.add(resultLabel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gridPanel, diceImagePanel);
        splitPane.setResizeWeight(1); // Adjust as needed

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private class CellClickListener implements ActionListener {
        private int x;
        private int y;

        public CellClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
             //TODO
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.OK_OPTION);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//
//        System.out.println("Sto posizionando a caso una scala e un serpente");
//        StandardCell A = (StandardCell) board.getCell(board.getBoardComponentCells().get(2).getPosition());
//        StandardCell B = (StandardCell) board.getCell(board.getBoardComponentCells().get(10).getPosition());
//        BoardComponent Ladder = new BoardComponent(Token.LADDER.name(), A.getPosition(), board.getBoardComponentCells().get(6).getPosition());
//        BoardComponent Snake = new BoardComponent(Token.SNAKE.name(), B.getPosition(), board.getBoardComponentCells().get(3).getPosition());
//        A.setBoardComponent(Ladder);
//        B.setBoardComponent(Snake);
//        System.out.println("Vivo");
//
//
//        super.paintComponent(g);
//
//        int cellSize = 50;
//        int startX = A.getPosX();
//        int startY = A.getPosY();
//        int endX = B.getPosX();
//        int endY = B.getPosY();
//
//        int x1 = startX * cellSize + cellSize / 2;
//        int y1 = startY * cellSize + cellSize / 2;
//        int x2 = endX * cellSize + cellSize / 2;
//        int y2 = endY * cellSize + cellSize / 2;
//
//        // Disegna la linea tra i punti
//        g.setColor(Color.RED);
//        g.drawLine(x1, y1, x2, y2);
//    }

    public static void main(String[] args) {
        Board board = new Board(10, 10, true, true);
        TestGameW testGameW = new TestGameW(board);

//        StandardCell A = (StandardCell) board.getCell(board.getBoardComponentCells().get(2).getPosition());
//        StandardCell B = (StandardCell) board.getCell(board.getBoardComponentCells().get(10).getPosition());
//        BoardComponent Ladder = new BoardComponent(Token.LADDER.name(), A.getPosition(), board.getBoardComponentCells().get(6).getPosition());
//        BoardComponent Snake = new BoardComponent(Token.SNAKE.name(), B.getPosition(), board.getBoardComponentCells().get(3).getPosition());

        testGameW.show();
    }
}
