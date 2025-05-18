package com.shapeville.ui;
//package src.main.java.com.shapeville.ui;

import com.shapeville.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * The top navigation bar displaying Home/End buttons, score, and progress.
 */
public class NavigationBar extends JPanel {

    private MainFrame mainFrameRef;
    private JButton homeButton;
    private JButton endSessionButton;
    private JLabel scoreLabel;
    private JProgressBar progressBar;

    public NavigationBar(MainFrame mainFrame) {
        this.mainFrameRef = mainFrame;
        setLayout(new BorderLayout(10, 5)); // Use BorderLayout
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Left side: Score and Progress
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        // TODO: Define total steps for progress bar based on TaskManager's sequence
        int totalTasks = 10; // Placeholder - get this dynamically later
        progressBar = new JProgressBar(0, totalTasks);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(150, 20));

        statusPanel.add(new JLabel("Progress:"));
        statusPanel.add(progressBar);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(scoreLabel);

        // Right side: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        homeButton = new JButton("Home");
        endSessionButton = new JButton("End Session");

        buttonPanel.add(homeButton);
        buttonPanel.add(endSessionButton);

        // Add panels to the main NavigationBar panel
        add(statusPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);

        // Action Listeners
        homeButton.addActionListener(e -> mainFrameRef.navigateToHome());
        endSessionButton.addActionListener(e -> mainFrameRef.endSession());
    }

    public void updateScore(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    public void updateProgress(int tasksCompleted, int totalTasks) {
        progressBar.setMaximum(totalTasks); // Ensure max value is correct
        progressBar.setValue(tasksCompleted);
    }
}