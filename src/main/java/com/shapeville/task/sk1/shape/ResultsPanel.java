package com.shapeville.task.sk1.shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Results Panel - Displays the score and results after completing a task
 */
class ResultsPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel messageLabel;
    private JButton homeButton;
    
    public ResultsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // Light blue background
        
        // Add title
        JLabel titleLabel = new JLabel("Task Completed!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(70, 130, 180)); // Steel blue
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(80));
        add(titleLabel);
        
        // Add score label
        scoreLabel = new JLabel("Your Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(30));
        add(scoreLabel);
        
        // Add message label
        messageLabel = new JLabel("Well done!");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(messageLabel);
        
        // Add home button
        homeButton = new JButton("Return to Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 18));
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.setMaximumSize(new Dimension(200, 50));
        homeButton.setBackground(new Color(70, 130, 180)); // Steel blue
        homeButton.setForeground(Color.WHITE);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setFocusPainted(false);
        // Use correct rounded border implementation
        homeButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        add(Box.createVerticalStrut(50));
        add(homeButton);
        
        // Add bottom information
        JLabel infoLabel = new JLabel("Thank you for using the Shape Recognition App");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(infoLabel);
        add(Box.createVerticalStrut(20));
    }
    
    /**
     * Set the results information
     */
    public void setResults(int score, boolean is3D) {
        scoreLabel.setText("Your Score: " + score);
        
        // Display different messages based on the score
        String message;
        if (score >= 10) {
            message = "Excellent! You have a great understanding of " + (is3D ? "3D" : "2D") + " shapes!";
        } else if (score >= 5) {
            message = "Well done! Keep practicing and you'll get even better!";
        } else {
            message = "Keep trying! With more practice, you'll master these shapes!";
        }
        
        messageLabel.setText(message);
    }
    
    /**
     * Add a listener for the home button
     */
    public void addHomeListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }
}    