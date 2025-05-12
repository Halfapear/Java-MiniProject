package src.main.java.com.shapeville.main;

import javax.swing.*;

/**
 * Main application entry point. Initializes and displays the main frame.
 */
public class MainApp {

    public static void main(String[] args) {
        // Ensure UI operations are on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
