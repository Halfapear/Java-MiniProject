package code;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {
    public NavigationPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("Home");
        JButton endButton = new JButton("End Session");

        homeButton.addActionListener(e -> MainApp.switchPanel(new HomePanel()));
        endButton.addActionListener(e -> MainApp.switchPanel(new EndPanel()));

        add(homeButton);
        add(endButton);
    }
}
