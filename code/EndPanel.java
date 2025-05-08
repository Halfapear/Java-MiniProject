package code;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    public EndPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Thank you for playing Shapeville!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}
