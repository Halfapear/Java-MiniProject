package com.shapeville.task.sk1.shape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Task Panel - Displays shapes and handles user input
 */
class TaskPanel extends JPanel {
    private JLabel shapeImageLabel;
    private JTextField answerField;
    private JLabel instructionsLabel;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JButton submitButton;
    private JButton homeButton;
    private JButton finishButton;

    private int currentScore = 0;
    private boolean is3DTask = false;

    public TaskPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); // Light blue background

        // Top panel - Displays title and score
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(70, 130, 180)); // Steel blue
        topPanel.setPreferredSize(new Dimension(0, 60));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        topPanel.add(scoreLabel);

        add(topPanel, BorderLayout.NORTH);

        // Center panel - Displays shape image and instructions
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240, 248, 255));

        // Shape image area - 使用GridBagLayout确保图像居中
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        imagePanel.setPreferredSize(new Dimension(300, 300));
        imagePanel.setMaximumSize(new Dimension(300, 300));

        shapeImageLabel = new JLabel();
        shapeImageLabel.setHorizontalAlignment(JLabel.CENTER);

        // 使用GridBagConstraints确保图像在面板中居中
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        imagePanel.add(shapeImageLabel, gbc);

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(imagePanel);
        centerPanel.add(Box.createVerticalStrut(30));

        // Instructions label
        instructionsLabel = new JLabel("Please enter the name of this shape:");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(instructionsLabel);

        // Attempts left label
        attemptsLabel = new JLabel("Attempts left: 3");
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel.setForeground(Color.GRAY);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(attemptsLabel);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(messageLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel - Input field and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20)); // 右对齐，设置间距
        bottomPanel.setBackground(new Color(240, 248, 255));

        // Input field panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(new Color(240, 248, 255));

        answerField = new JTextField(15); // 缩小输入栏
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        answerField.setPreferredSize(new Dimension(200, 35)); // 调整大小
        answerField.addActionListener(e -> submitButton.doClick()); // Submit on Enter key

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.setBackground(new Color(106, 168, 79)); // Green
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        // Use correct rounded border implementation
        submitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(106, 168, 79), 2, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        inputPanel.add(answerField);
        inputPanel.add(Box.createHorizontalStrut(10));
        inputPanel.add(submitButton);

        bottomPanel.add(inputPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        homeButton.setBackground(new Color(241, 112, 107)); // Red
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        // Use correct rounded border implementation
        homeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 112, 107), 2, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        finishButton = new JButton("Finish Session");
        finishButton.setFont(new Font("Arial", Font.PLAIN, 14));
        finishButton.setBackground(new Color(246, 178, 107)); // Orange
        finishButton.setForeground(Color.WHITE);
        finishButton.setFocusPainted(false);
        // Use correct rounded border implementation
        finishButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(246, 178, 107), 2, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        buttonPanel.add(homeButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(finishButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Initialize the task
     */
    public void initializeTask(boolean is3D) {
        this.is3DTask = is3D;
        currentScore = 0;
        scoreLabel.setText("Score: " + currentScore);
        messageLabel.setText("");
    }

    /**
     * Load the shape image while preserving aspect ratio
     */
    public void loadShapeImage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("Image not found: " + imagePath);
                shapeImageLabel.setText("Image Not Found");
                shapeImageLabel.setForeground(Color.RED);
                shapeImageLabel.setIcon(null);
            } else {
                ImageIcon icon = new ImageIcon(imagePath);
                Image originalImage = icon.getImage();
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);

                // 计算在保持宽高比的情况下，能放入250x250区域的最大尺寸
                int newWidth, newHeight;
                double widthRatio = (double) 250 / originalWidth;
                double heightRatio = (double) 250 / originalHeight;
                double ratio = Math.min(widthRatio, heightRatio);

                newWidth = (int) (originalWidth * ratio);
                newHeight = (int) (originalHeight * ratio);

                // 缩放图像，保持宽高比
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                // 将缩放后的图像居中显示在面板中
                shapeImageLabel.setIcon(new ImageIcon(scaledImage));
                shapeImageLabel.setText("");
            }
            // 清除提示语
            messageLabel.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            shapeImageLabel.setText("Image Load Error");
            shapeImageLabel.setForeground(Color.RED);
            shapeImageLabel.setIcon(null);
            // 清除提示语
            messageLabel.setText("");
        }
    }

    /**
     * Set the instruction text
     */
    public void setInstructions(String text) {
        instructionsLabel.setText(text);
    }

    /**
     * Get the user input
     */
    public String getUserInput() {
        return answerField.getText();
    }

    /**
     * Clear the input field
     */
    public void clearInput() {
        answerField.setText("");
        answerField.requestFocus();
    }

    /**
     * Set the number of attempts left
     */
    public void setAttemptsLeft(int attempts) {
        attemptsLabel.setText("Attempts left: " + attempts);
    }

    /**
     * Show a message
     */
    public void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    /**
     * Update the score
     */
    public void updateScore(int score) {
        currentScore = score;
        scoreLabel.setText("Score: " + currentScore);
    }

    /**
     * Add a listener for the submit button
     */
    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    /**
     * Add a listener for the home button
     */
    public void addHomeListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }

    /**
     * Add a listener for the finish button
     */
    public void addFinishListener(ActionListener listener) {
        finishButton.addActionListener(listener);
    }
}