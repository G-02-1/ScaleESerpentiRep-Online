package Board.Grid.GridCells;

import javax.swing.*;
import java.awt.*;

public class LineBetweenCellsExample extends JPanel {

    private final int cellSize = 50; // Dimensione delle celle
    private final int startX = 2; // Coordinata X della prima cella
    private final int startY = 3; // Coordinata Y della prima cella
    private final int endX = 5; // Coordinata X della seconda cella
    private final int endY = 7; // Coordinata Y della seconda cella

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcola le coordinate dei punti di inizio e fine
        int x1 = startX * cellSize + cellSize / 2;
        int y1 = startY * cellSize + cellSize / 2;
        int x2 = endX * cellSize + cellSize / 2;
        int y2 = endY * cellSize + cellSize / 2;

        // Disegna la linea tra i punti
        g.setColor(Color.RED);
        g.drawLine(x1, y1, x2, y2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Line Between Cells Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new LineBetweenCellsExample());
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
