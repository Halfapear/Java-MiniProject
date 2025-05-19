package com.shapeville.task.sk1;

import com.shapeville.main.MainFrame;
import com.shapeville.logic.ScoreManager;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;
import com.shapeville.logic.TaskLogic;
import com.shapeville.model.Feedback;
import com.shapeville.model.Problem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Task 2: Angle Type Recognition
 * Displays an angle image and prompts the user to identify its type
 */
public class Task2Panel extends JPanel implements TaskPanel {
    private MainFrame mainFrame;
    private JLabel angleImageLabel;
    private JComboBox<String> angleTypeComboBox;
    private JButton submitButton;
    private JLabel resultLabel;
    private JLabel promptLabel;
    private int currentAngle;
    private ScoreManager scoreManager;

    public Task2Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.scoreManager = mainFrame.getScoreManager();
        initializeUI();
        loadRandomAngle();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Task 2: Angle Type Recognition");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        // Image Container
        angleImageLabel = new JLabel();
        angleImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        angleImageLabel.setPreferredSize(new Dimension(300, 300));
        angleImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(angleImageLabel);
        add(Box.createVerticalStrut(20));

        // Prompt
        promptLabel = new JLabel("Select the correct angle type:");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        promptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(promptLabel);

        // Dropdown for angle types
        angleTypeComboBox = new JComboBox<>(new String[]{
            "Acute Angle (0-90°)",
            "Right Angle (90°)",
            "Obtuse Angle (90-180°)",
            "Straight Angle (180°)",
            "Reflex Angle (180-360°)"
        });
        angleTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        angleTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(angleTypeComboBox);
        add(Box.createVerticalStrut(15));

        // Submit Button
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Fixed: Added ActionEvent parameter to match ActionListener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(e); // Pass ActionEvent to checkAnswer
            }
        });
        add(submitButton);
        add(Box.createVerticalStrut(15));

        // Result Display
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(resultLabel);
    }

    private void loadRandomAngle() {
        Random random = new Random();
        currentAngle = random.nextInt(361); // Generates angles between 0 and 360 inclusive
        
        String imagePath = "/assets/angles/angle_" + currentAngle + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            angleImageLabel.setIcon(icon);
        } else {
            generateAngleImage(currentAngle);
        }
        
        resultLabel.setText("");
        angleTypeComboBox.setSelectedIndex(0); // Reset selection
    }

    private void generateAngleImage(int degrees) {
        int width = 300, height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // Draw coordinate system
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        
        int centerX = width / 2, centerY = height / 2;
        int radius = 100;
        
        // Draw initial horizontal line
        g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
        
        // Draw angle line
        double radians = Math.toRadians(degrees);
        int endX = centerX + (int) (radius * Math.cos(radians));
        int endY = centerY - (int) (radius * Math.sin(radians));
        g2d.drawLine(centerX, centerY, endX, endY);
        
        // Draw arc for angle measurement
        g2d.setColor(Color.RED);
        g2d.drawArc(centerX - 50, centerY - 50, 100, 100, 0, -degrees);
        
        // Add degree text
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(degrees + "°", centerX + 40, centerY - 40);
        
        g2d.dispose();
        angleImageLabel.setIcon(new ImageIcon(image));
    }

    // Fixed: Added ActionEvent parameter to match ActionListener signature
    private void checkAnswer(ActionEvent e) { // Modified method signature
        String selectedType = (String) angleTypeComboBox.getSelectedItem();
        String correctType = getAngleType(currentAngle);
        boolean isCorrect = selectedType.contains(correctType.split(" ")[0].toLowerCase()); // Compare base type
        int points = scoreManager.calculatePoints(1, false); // Basic scoring
        
        String message;
        if (isCorrect) {
            message = "Correct! +" + points + " points";
            scoreManager.recordScoreAndFeedback(points);
        } else {
            message = "Incorrect! The correct type is: " + correctType;
            scoreManager.recordScoreAndFeedback(0);
        }
        
        resultLabel.setText(message);
        
        // Show feedback and load next angle after delay
        Feedback feedback = new Feedback(
            isCorrect, 
            points, 
            message, 
            correctType, 
            true, 
            false
        );
        showFeedback(feedback);
        
        new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                loadRandomAngle();
            }
        }).start();
    }

    private String getAngleType(int degrees) {
        if (degrees == 0) return "Zero Angle (0°)";
        if (degrees < 90) return "Acute Angle (0-90°)";
        if (degrees == 90) return "Right Angle (90°)";
        if (degrees < 180) return "Obtuse Angle (90-180°)";
        if (degrees == 180) return "Straight Angle (180°)";
        if (degrees < 360) return "Reflex Angle (180-360°)";
        return "Full Rotation (360°)";
    }

    @Override
    public void resetState() {
        loadRandomAngle();
        scoreManager.resetSession();
    }

    @Override
    public String getPanelId() {
        return Constants.ANGLE_RECOGNITION_PANEL_ID;
    }

    @Override
    public void setTaskLogicCallback(TaskLogic logic) {
        // Not implemented in this version
    }

    @Override
    public void displayProblem(Problem problem) {
        // Not implemented in this version
    }

    @Override
    public void showFeedback(Feedback feedback) {
        resultLabel.setText(feedback.getMessage());
        scoreManager.recordScoreAndFeedback(feedback.getPointsEarned());
    }
}