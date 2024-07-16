package Graphic;

import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.SpecialCell;
import Board.Grid.GridCells.StandardCell;
import Patterns.ObserverComunication.Subscriber;
import PlayerObjects.Player;
import SimulationObject.Simulation;
import SupportingObjects.Position;
import SupportingObjects.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


public class GamePlayWindow extends JPanel implements Subscriber {

    private int turn;
    private JFrame frame;
    private Simulation simulation;
    private JButton[][] gridButtons;
    private int gridSizeX, gridSizeY;
    private Board board;
    private Cell[][] cells;
    private ArrayList<Player> players;
    private JMenuItem saveItem;
    private JButton playSimulation, nextButton;
    private HashMap<Player, JLabel> playersLabels;
    private boolean auto;


    public GamePlayWindow(Simulation simulation) {
        this.simulation = simulation;
        this.board = this.simulation.getBoard();
        int x = board.getX();
        int y = board.getY();
        this.turn = -1;
        this.players = simulation.getPlayers();
        playersLabels = new HashMap<>();

        for(Player p : players) {
            p.addSubscriber(this);
        }

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
                } else if (cells[i][j] instanceof StandardCell sCell){
                    if(sCell.containsBoardComponentActive()) {
                        gridButtons[i][j] = new JButton(""+sCell.getNumber()+": " + sCell.getBoardComponent().name() + "\n(to: " +
                                sCell.getPASSIVE().getNumber() + ")");
                    }
                    else if(sCell.containsBoardComponentPassive() && sCell.getBoardComponent().isSnake()) {
                        gridButtons[i][j] = new JButton(""+sCell.getNumber()+" \nSnake's tail");
                    } else if(sCell.containsBoardComponentPassive() && sCell.getBoardComponent().isLadder()) {
                        gridButtons[i][j] = new JButton("" + sCell.getNumber() + " \nLadder's top");
                    } else {
                        gridButtons[i][j] = new JButton(""+sCell.getNumber()+"");
                    }
                }
                //gridButtons[i][j].setEnabled(false); //All the cell aren't clickable

                gridButtons[i][j].addActionListener(new GamePlayWindow.CellClickListener(i, j));
                gridPanel.add(gridButtons[i][j]);
            }
        }
        System.out.println(board);
        for(Cell c : board.getAllCells()) {
            System.out.println(c.getPosition() + ": " + c.getNumber());
        }

        JPanel buttonPanel = new JPanel();
        this.nextButton = new JButton("Next");
        if(auto) {
            nextButton.setEnabled(false);
        }

        nextButton.addActionListener(e -> {
            auto = false;
            this.playSimulation.setEnabled(true);
            increaseTurn();
            try {
                movePlayer();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Next button clicked");
        });

        this.playSimulation = new JButton("Play");
        if(auto) {
            playSimulation.setEnabled(false);
        }

        playSimulation.addActionListener(e -> {
            playSimulation.setEnabled(false);
            nextButton.setEnabled(false);
            auto = true;
            while(auto) {
                increaseTurn();
                try {
                    movePlayer();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.out.println("Next button clicked");
        });


//        JButton throwButton = new JButton("throw");
//        JLabel resultLabel = new JLabel("Result: ");
//        JLabel diceImageLabel1 = new JLabel();
//        JLabel diceImagelabel2 = new JLabel();
//
//        throwButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Dice dice = new Dice();
//                int result = dice.throwDice();
//                resultLabel.setText("Result: " + result);
//
//                diceImageLabel1.setIcon(diceFaces[result - 1]);
//                if(simulation.getDicesNumber() == 2) {
//                    diceImagelabel2.setIcon(diceFaces[result - 1]);
//                }
//            }
//        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save clicked!");
                if(showConfirmationDialog("Save & exit?")) {
                    simulation.save();
                    showMessage("Simulation saved");
                    frame.dispose();
                } else {
                    showError("Simulation not saved!");
                }
            }
        });
        fileMenu.add(saveItem);

        JMenuItem optionsItem = new JMenuItem("Simulation's field");
        optionsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Grid's dimension: " + simulation.getBoard().getX() + "X" + simulation.getBoard().getY());
                sb.append("\nNumber of players: " + simulation.getNPlayer());
                sb.append("\nNumber of dices: " + simulation.getDicesNumber());
                if(simulation.isCUSTOM()) {
                    sb.append("\nIs custom");
                }
                if(simulation.hasCARD()) {
                    sb.append("\nHas cards");
                }
                if(simulation.hasSPECIALCARD()) {
                    sb.append("\nHas special card: 'parking term'");
                }
                showMessage(sb.toString());
            }
        });
        fileMenu.add(optionsItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

//        JPanel diceImagePanel1 = new JPanel(new BorderLayout());
//        diceImagePanel1.add(diceImageLabel1, BorderLayout.NORTH);
//        Dimension preferredSize = new Dimension(5, 5);
//        diceImagePanel1.setPreferredSize(preferredSize);
//
//        JPanel diceImagePanel2 = null;
//        if(this.simulation.getDicesNumber() == 2) {
//            diceImagePanel2 = new JPanel(new BorderLayout());
//            diceImagePanel2.add(diceImagelabel2, BorderLayout.NORTH);
//            diceImagePanel2.setPreferredSize(preferredSize);
//        }

        JPanel contentPanel = new JPanel(new BorderLayout());
//        contentPanel.add(diceImagePanel1, BorderLayout.EAST);
        contentPanel.add(gridPanel, BorderLayout.WEST);

        JPanel playersLabel = new JPanel();
        playersLabel.setLayout(new GridLayout(this.simulation.getNPlayer(), 1));

//        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA};

        for (int i = 0; i < this.simulation.getNPlayer(); i++) {
            JLabel label = new JLabel(players.get(i).getName());
//            label.setForeground(colors[i]);
            playersLabel.add(label);
            playersLabels.put(players.get(i), label);
        }

//        if(this.simulation.getDicesNumber() == 2) {
//            JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, diceImagePanel1, diceImagePanel2);
//            splitPane1.setResizeWeight(0.5);
//
//            JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1, playersLabel);
//            splitPane2.setResizeWeight(0.3);
//
//            JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gridPanel, splitPane2);
//            splitPane3.setResizeWeight(0.6); // Adjust as needed
//            frame.add(splitPane3, BorderLayout.CENTER);
//        } else {
//            JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, diceImagePanel1, playersLabel);
//            splitPane1.setResizeWeight(0.3);
//
//            JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gridPanel, splitPane1);
//            splitPane2.setResizeWeight(0.6); // Adjust as needed
//            frame.add(splitPane2, BorderLayout.CENTER);
//        }
//
//        buttonPanel.add(throwButton);
//        buttonPanel.add(resultLabel);
        buttonPanel.add(nextButton);
        buttonPanel.add(playSimulation);

        //__________________________________________________________________________________________
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gridPanel, playersLabel);
//        splitPane.setResizeWeight(0.7); // Adjust as needed
//        frame.add(splitPane, BorderLayout.CENTER);
        //__________________________________________________________________________________________

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(String message) {
        //I will use an if-statement because in a switch-statement I can't use case Token.CASE.name()
        String[] parts = message.split("-");
        for(int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        String msg;
        String eventType = parts[0];
        String playerName = parts[1];
        if(eventType.equals(Token.ANOTHER.name())) {
            msg = playerName + " threw a double six, so: " + playerName + " will throw again the dices!\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.ALMOST.name())) {
            msg = playerName + " has almost arrived to the finish line!\n" + parts[2] + " cells left\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.ARRIVE.name())) {
            msg = playerName + " has just arrived on Cell n°: " + parts[2] + "\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.CLIMB.name())) {
            msg = "How lucky!\n" + playerName + " encounters a ladder!\n" + playerName + " goes to " + parts[2] + "\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.GOTOSLEEP.name())) {
            msg = playerName + " is very sleepy... How lucky! Here " + playerName + " has found an Inn, he will be able to rest for two turns without throwing the ";
            if(this.simulation.getDicesNumber() == 2) {
                msg = msg + "dices!\n";
            } else {
                msg = msg + "dice!\n";
            }
            showMessage(msg);
        }
        else if(eventType.equals(Token.MOVEAGAIN.name())) {
            msg = "How lucky, a spring " + playerName + " jumps so high... About " + parts[2] + " cells!\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.PARKINGTERM.name())) {
            msg = playerName + " uses the 'Parking Term' card on " + parts[2] + "! Now " + playerName + " is free to go!\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.PICKACARD.name())) {
            msg = playerName + " picked a " + parts[3] + " card!\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.RETREAT.name())) {
            msg = "Too bad! " + playerName + " was almost there, too bad it will have to go back " + parts[2] + " cells...\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.SLICE.name())) {
            msg = "Oh crap!\n" + playerName + " encounters a snake!\n" + playerName + " goes to " + parts[2] + "\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.STANDSTILL.name())) {
            msg = playerName + " is very tired... How lucky, a bench! he will be able to rest for one turn without throwing the ";
            if(this.simulation.getDicesNumber() == 2) {
                msg = msg + "dices!\n";
            } else {
                msg = msg + "dice!\n";
            }
            showMessage(msg);
        }
        else if(eventType.equals(Token.THROWAGAIN.name())) {
            msg = playerName + " is very lucky, he found two dices, and he really wants to throw again...\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.THROW.name())) {
            msg = playerName + " threw: " + parts[2] + "\n";
            showMessage(msg);
        }
        else if(eventType.equals(Token.WIN.name())) {
            msg = playerName + " wins the game!\n";
            this.auto = false;
            this.playSimulation.setEnabled(false);
            this.nextButton.setEnabled(false);
            showMessage(msg);
            this.saveItem.doClick();
        }
    }

    private void increaseTurn() {
        this.turn = (turn + 1 + this.simulation.getNPlayer()) % this.simulation.getNPlayer();
    }
    private void movePlayer() throws InterruptedException {
        Player player = players.get(this.turn);
        if(player.getState().move()) {
            System.out.println(player.getName() + " può muoversi? " + player.getState().move());
            player.turn();
        } else {
            System.out.println(player.getName() + " può muoversi? " + player.getState().move());
            increaseTurn();
            movePlayer();
        }
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
            Cell cell = board.getCell(new Position(x, y));
            String message = null;
            if(cell instanceof SpecialCell) {
                message = "Cell n°" + cell.getNumber() + "\nType: " + cell.toString();
            } else if (cell instanceof StandardCell) {
                message = "Cell n°" + cell.getNumber();
                if(((StandardCell) cell).containsBoardComponentActive()) {
                    message = message + "\nContains: " + ((StandardCell) cell).getBoardComponent().name() + ", from: " +
                            ((StandardCell) cell).getNumber() + ", to" + ((StandardCell) cell).getPASSIVE().getNumber();
                }
                else if(((StandardCell) cell).containsBoardComponentPassive()) {
                    message = message + "\nIs the final position of a: " + ((StandardCell) cell).getBoardComponent().name();
                }
            }
            int nP = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("\nPlayers: ");
            for(Player player : players) {
                if(player.getCurrentNumber() == cell.getNumber()) {
                    sb.append("\n" + player.getName());
                    nP++;
                }
            }
            if(nP == 0) {
                sb.append("\nNo one player on this cell");
            }
            message = message + sb;
            showMessage(message);
        }
    }

    private boolean showConfirmationDialog(String message) {
        int result = JOptionPane.showConfirmDialog(frame, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
