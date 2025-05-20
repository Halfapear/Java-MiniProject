import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Welcome Panel - Displayed when the application starts
 */
class WelcomePanel extends JPanel {
    private JButton start2DButton;
    private JButton start3DButton;
    
    public WelcomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // Light blue background
        
        // Add title
        JLabel titleLabel = new JLabel("Shape Recognition Educational App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(70, 130, 180)); // Steel blue
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(80));
        add(titleLabel);
        
        // Add description
        JLabel descLabel = new JLabel("Test your shape recognition skills!");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(descLabel);
        
        // Add 2D shapes button
        start2DButton = new JButton("Start 2D Shape Recognition");
        start2DButton.setFont(new Font("Arial", Font.PLAIN, 18));
        start2DButton.setPreferredSize(new Dimension(250, 50));
        start2DButton.setMaximumSize(new Dimension(250, 50));
        start2DButton.setBackground(new Color(106, 168, 79)); // Green
        start2DButton.setForeground(Color.WHITE);
        start2DButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        start2DButton.setFocusPainted(false);
        // Use correct rounded border implementation
        start2DButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(106, 168, 79), 2, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        add(Box.createVerticalStrut(50));
        add(start2DButton);
        
        // Add 3D shapes button
        start3DButton = new JButton("Start 3D Shape Recognition");
        start3DButton.setFont(new Font("Arial", Font.PLAIN, 18));
        start3DButton.setPreferredSize(new Dimension(250, 50));
        start3DButton.setMaximumSize(new Dimension(250, 50));
        start3DButton.setBackground(new Color(66, 103, 178)); // Facebook blue
        start3DButton.setForeground(Color.WHITE);
        start3DButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        start3DButton.setFocusPainted(false);
        // Use correct rounded border implementation
        start3DButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(66, 103, 178), 2, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        add(Box.createVerticalStrut(20));
        add(start3DButton);
        
        // Add bottom information
        JLabel infoLabel = new JLabel("KS1 Educational Application");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(infoLabel);
        add(Box.createVerticalStrut(20));
    }
    
    /**
     * Add listener for the 2D task start button
     */
    public void addStart2DListener(ActionListener listener) {
        start2DButton.addActionListener(listener);
    }
    
    /**
     * Add listener for the 3D task start button
     */
    public void addStart3DListener(ActionListener listener) {
        start3DButton.addActionListener(listener);
    }
}    