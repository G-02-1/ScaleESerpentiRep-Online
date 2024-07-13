package Graphic;

import Board.Components.BoardComponent;
import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.SpecialCell;
import Board.Grid.GridCells.StandardCell;
import SupportingObjects.Position;
import SupportingObjects.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LadderAndSnakeSetterWindow {
    private JFrame frame;
    private JButton[][] gridButtons;
    private boolean placingLadder, placingSnakes;
    private int gridSizeX, gridSizeY, selectedX1, selectedY1, selectedX2, selectedY2, cellCounter, snakeCounter, ladderCounter;
    private Board board;
    private Cell[][] cells;

    private JButton snakeButton, ladderButton;

    public LadderAndSnakeSetterWindow(Board board) {
        this.board = board;
        this.ladderCounter = 0;
        this.snakeCounter = 0;
        int x = board.getX();
        int y = board.getY();
        this.cells = new Cell[x][y];
        gridSizeX = x;
        gridSizeY = y;
        gridButtons = new JButton[x][y];
        placingLadder = false;
        placingSnakes = false;
        cellCounter = 0;

        System.out.println(this.board.getBOARDCOMPONENTNUMBER());

        frame = new JFrame("Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(x, y));
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //Populate the matrix to match the cells on the board
                cells[i][j] = this.board.getCell(new Position(i, j));

                if(cells[i][j] instanceof SpecialCell) {
                    gridButtons[i][j] = new JButton(""+cells[i][j].getNumber()+": SPEICAL ");
                    gridButtons[i][j].setEnabled(false); //the special cells aren't placeable
                } else {
                    gridButtons[i][j] = new JButton(""+cells[i][j].getNumber()+"");
                }

                gridButtons[i][j].addActionListener(new CellClickListener(i, j));
                gridPanel.add(gridButtons[i][j]);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        ladderButton = new JButton("Ladder");
        snakeButton = new JButton("Snake");

        ladderButton.addActionListener(e -> {
            placingLadder = true;
            placingSnakes = false;
        });

        snakeButton.addActionListener(e -> {
            placingSnakes = true;
            placingLadder = false;
        });

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            if(ladderCounter < board.getBOARDCOMPONENTNUMBER() / 2) {
                showError("Cannot instantiate a board with ladders' number minor than " + this.board.getBOARDCOMPONENTNUMBER() / 2);
            }
            if(snakeCounter < board.getBOARDCOMPONENTNUMBER() / 2) {
               showError("Cannot instantiate a board with snakes' number minor than " + this.board.getBOARDCOMPONENTNUMBER() / 2);
           } else {

            }
            System.out.println("Next button clicked");
        });

        buttonPanel.add(ladderButton);
        buttonPanel.add(snakeButton);
        buttonPanel.add(nextButton);

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
            if (placingLadder || placingSnakes) {
                if (ladderCounter == board.getBOARDCOMPONENTNUMBER() / 2 && snakeCounter == board.getBOARDCOMPONENTNUMBER() / 2) {
                    ladderButton.setEnabled(false);
                    snakeButton.setEnabled(false);
                    showSuccess("Click next to start the simulation");
                }
                else if (cellCounter == 0) {
                    selectedX1 = x;
                    selectedY1 = y;
                    cellCounter++;
                } else if (cellCounter == 1) {
                    selectedX2 = x;
                    selectedY2 = y;
                    cellCounter++;

                    if (placingLadder) {
                        if (ladderCounter == board.getBOARDCOMPONENTNUMBER() / 2) {
                            ladderButton.setEnabled(false);
                            showMessage("Placed the maximum ladders' number");
                            cellCounter = 0;
                        }
                        else if (cells[selectedX1][selectedY1].compareTo(cells[selectedX2][selectedY2]) == 0) {
                            showError("Cannot place a ladder on the same cell!");
                            System.out.println("Ladder celle uguali");
                            cellCounter = 0;
                        }
                        else if(cells[selectedX1][selectedY1].compareTo(cells[selectedX2][selectedY2]) == 1) {
                            showError("Cannot place a ladder to a from a major cell to a minor one");
                            cellCounter = 0;
                        }
                        else {
                            StandardCell active = (StandardCell) cells[selectedX1][selectedY1];
                            StandardCell passive = (StandardCell) cells[selectedX2][selectedY2];
                            showMessage("Ladder: (" + selectedX1 + ", " + selectedY1 + ") - (" + selectedX2 + ", " + selectedY2 + ")" +
                                    "\nAt " + active.toString() + ", " + passive.toString());

                            BoardComponent ladder = new BoardComponent(Token.LADDER.name(), active.getPosition(), passive.getPosition());
                            active.setBoardComponent(ladder);

                            ladderCounter++;
                            cellCounter = 0;

                            //color change
                            gridButtons[selectedX1][selectedY1].setBackground(Color.YELLOW);
                            gridButtons[selectedX2][selectedY2].setBackground(Color.YELLOW);

                            //set unclickable
                            gridButtons[selectedX1][selectedY1].setEnabled(false);
                            gridButtons[selectedX2][selectedY2].setEnabled(false);
                        }
                    } else {
                        if (snakeCounter == board.getBOARDCOMPONENTNUMBER() / 2) {
                            snakeButton.setEnabled(false);
                            showMessage("Placed the maximum snakes' number");
                            cellCounter = 0;
                        }
                        else if (cells[selectedX1][selectedY1].compareTo(cells[selectedX2][selectedY2]) == 0) {
                            showError("Cannot place a snake on the same cell!");
                            cellCounter = 0;
                        }
                        else if(cells[selectedX1][selectedY1].compareTo(cells[selectedX2][selectedY2]) == -1) {
                            showError("Cannot place a snake to a from a minor cell to a major one");
                            cellCounter = 0;
                        }
                        else {
                            StandardCell active = (StandardCell) cells[selectedX1][selectedY1];
                            StandardCell passive = (StandardCell) cells[selectedX2][selectedY2];
                            showMessage("Snake: (" + selectedX1 + ", " + selectedY1 + ") - (" + selectedX2 + ", " + selectedY2 + ")" +
                                    "\nAt " + active.toString() + ", " + passive.toString());

                            BoardComponent snake = new BoardComponent(Token.SNAKE.name(), active.getPosition(), passive.getPosition());
                            active.setBoardComponent(snake);

                            snakeCounter++;
                            cellCounter = 0;

                            //color change
                            gridButtons[selectedX1][selectedY1].setBackground(Color.GREEN);
                            gridButtons[selectedX2][selectedY2].setBackground(Color.GREEN);

                            //set unclickable
                            gridButtons[selectedX1][selectedY1].setEnabled(false);
                            gridButtons[selectedX2][selectedY2].setEnabled(false);
                        }
                    }
                }
            }
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
}
