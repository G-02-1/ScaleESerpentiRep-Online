package Graphic;

import Patterns.Memento.Memento;
import Patterns.Memento.Originator;
import SimulationObject.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FirstWindow extends JFrame {

    protected final String folderPath;

    public FirstWindow(String folderPath) {

        this.folderPath = folderPath;

        setSize(500, 200);
        setTitle("Welcome on Snakes and Ladder!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton newSimulationButton = new JButton("New Simulation");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(newSimulationButton, constraints);

        newSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSecondWindow();
                ((JFrame) SwingUtilities.getWindowAncestor(newSimulationButton)).dispose();
            }
        });

        JButton uploadSimulationButton = new JButton("Upload Simulation");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(uploadSimulationButton, constraints);

        uploadSimulationButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(folderPath);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                    if (isValidExtension(extension)) {
                        System.out.println("File selected: " + selectedFile.getAbsolutePath());

                        //Restore the selected simulation
                        Simulation simulation = restore(selectedFile.getAbsolutePath());

                        ((JFrame) SwingUtilities.getWindowAncestor(uploadSimulationButton)).dispose();
                        // Open the "GamePlay" window (replace with your actual code) //TODO
                        openGamePlayWindow(simulation);

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid file extension. Please choose a valid file.");
                    }
                }
            }
        });

        setLocationRelativeTo(null);
        add(panel);
    }

    private boolean isValidExtension(String extension) {
        return extension.equalsIgnoreCase("dat");
    }

    private void openGamePlayWindow(Simulation simulation) {
        // Implement your "GamePlay" window here
        // ...
    }

    public void openSecondWindow() {
        SecondWindow secondWindow = new SecondWindow(this);
        secondWindow.setVisible(true);
    }

    public Simulation restore(String filePath) {
        try {
            FileInputStream door = new FileInputStream(filePath);
            ObjectInputStream reader = new ObjectInputStream(door);
            Simulation.MementoSimulation x = (Simulation.MementoSimulation) reader.readObject();
            return Simulation.backup(x);
        } catch (IOException | ClassNotFoundException e) {
            showError("The selected file has unidentified problem");
        }
        return null;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
