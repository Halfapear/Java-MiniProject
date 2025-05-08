package code;

import javax.swing.*;

public class MainApp {
    public static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Shapeville");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new HomePanel());
            frame.setVisible(true);
        });
    }

    public static void switchPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
    }
}
