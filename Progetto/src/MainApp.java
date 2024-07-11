import Board.Graphic.FirstWindow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApp {
    protected static final String folderPath = "C:\\LaddersAndSnakeFolder";
    public static void main(String[] args) {

        //Create simulation's folder (if he doesn't exist)
        try {
            Path folder = Paths.get(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
                System.out.println("Folder created: " + folder);
            } else {
                System.out.println("Folder already exists: " + folder);
            }
        } catch (IOException e) {
            System.err.println("Error creating folder: " + e.getMessage());
        }

        FirstWindow firstWindow = new FirstWindow(folderPath);
        firstWindow.setVisible(true);
    }
}
