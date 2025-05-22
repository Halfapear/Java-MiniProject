package com.shapeville.task.sk1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Arc2D;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.shapeville.logic.ScoreManager;
import com.shapeville.logic.TaskLogic;
import com.shapeville.main.MainFrame;
import com.shapeville.model.Feedback;
import com.shapeville.model.Problem;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;

public class Task2Panel extends JPanel implements TaskPanel {
    private MainFrame mainFrame;
    private ScoreManager scoreManager;
    private JLabel angleVisualLabel;
    private JTextField angleInputField;
    private JButton submitButton;
    private JLabel feedbackLabel;
    private JLabel angleTypeLabel;
    private JLabel attemptsLabel;
    private JLabel inputPromptLabel; // 新增：输入框前的提示标签
    private int currentAttempts = 3;
    private int completedAngles = 0;
    private final int[] targetAngles = {30, 90, 120, 180, 200, 270, 300, 360};
    private int currentIndex = 0;
    private int currentAngle = 0; // 控制UI显示的角度，初始为0
    private int targetAngle = 0;  // 存储目标角度
    private boolean angleEntered = false;
    private static final int VISUALIZATION_WIDTH = 300;
    private static final int VISUALIZATION_HEIGHT = 300;
    private static final int anglesToIdentify = 4; // 需要识别4种角度类型

    public Task2Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.scoreManager = mainFrame.getScoreManager();
        initializeUI();
        loadNextAngle();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Apply high contrast background to the main panel
        setBackground(new Color(30, 30, 30));

        // Title
        JLabel title = new JLabel("Angle Recognition", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE); // High contrast text
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        add(titlePanel);
        add(Box.createVerticalStrut(10));

        // Angle visualization panel
        angleVisualLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAngle(g, currentAngle, VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT);
            }
        };

        angleVisualLabel.setPreferredSize(new Dimension(VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT));
        angleVisualLabel.setMaximumSize(new Dimension(VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT));
        angleVisualLabel.setMinimumSize(new Dimension(VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT));
        angleVisualLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        // 居中显示
        JPanel visualizationWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        visualizationWrapper.setBackground(new Color(50, 50, 50)); // Contrast background for container
        visualizationWrapper.add(angleVisualLabel);
        add(visualizationWrapper);
        add(Box.createVerticalStrut(20));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(30, 30, 30)); // Match main panel background
        inputPromptLabel = new JLabel("Enter angle (0-360, multiples of 10):"); // 新增：输入框前的提示标签
        inputPromptLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        inputPromptLabel.setForeground(Color.WHITE); // High contrast text
        inputPanel.add(inputPromptLabel);
        angleInputField = new JTextField(10);
        angleInputField.setFont(new Font("Arial", Font.PLAIN, 18));
        angleInputField.setBackground(new Color(60, 60, 60)); // High contrast background
        angleInputField.setForeground(Color.WHITE); // High contrast text
        angleInputField.setToolTipText("Enter angle (0-360, multiples of 10)");
        inputPanel.add(angleInputField);
        add(inputPanel);
        add(Box.createVerticalStrut(10));

        // Angle type display
        angleTypeLabel = new JLabel(" ", SwingConstants.CENTER);
        angleTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        // Color will be set in handleSubmit()
        add(angleTypeLabel);
        add(Box.createVerticalStrut(10));

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.setBackground(new Color(80, 80, 80)); // High contrast background
        submitButton.setForeground(Color.WHITE); // High contrast text
        submitButton.addActionListener(this::handleSubmit);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        add(buttonPanel);
        add(Box.createVerticalStrut(10));

        // Feedback label
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        // Color will be set in handleSubmit()
        // 将 feedbackLabel 放置在一个居中布局的面板中
        JPanel feedbackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        feedbackPanel.setBackground(new Color(30, 30, 30)); // Match main panel background
        feedbackPanel.add(feedbackLabel);
        add(feedbackPanel);
        add(Box.createVerticalStrut(5));

        // Attempts counter
        attemptsLabel = new JLabel("Attempts left: 3", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsLabel.setForeground(Color.YELLOW); // High contrast text for status
        // 将 attemptsLabel 放置在一个居中布局的面板中
        JPanel attemptsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attemptsPanel.add(attemptsLabel);
        add(attemptsPanel);
        add(Box.createVerticalStrut(20));
    }

    private void drawAngle(Graphics g, int degrees, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Clear background
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, width, height);

        // Calculate drawing area
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // Draw base line (horizontal ray from origin) with half the width
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3.0f)); // Set the stroke width to half (3/2 = 1.5)
        g2d.drawLine(centerX, centerY, centerX + radius, centerY); // Draw a ray starting from the origin

        // Only draw angle line after user input
        if (degrees > 0) {
            double angleRad = Math.toRadians(degrees);
            int endX = (int) (centerX + radius * Math.cos(angleRad));
            int endY = (int) (centerY - radius * Math.sin(angleRad));
            g2d.drawLine(centerX, centerY, endX, endY);
        }

        // Only draw angle arc after user has entered the angle
        if (angleEntered && degrees > 0) {
            g2d.setColor(new Color(65, 105, 225));
            g2d.setStroke(new BasicStroke(4));
            Arc2D arc = new Arc2D.Double(
                    centerX - radius, centerY - radius,
                    radius * 2, radius * 2,
                    0, degrees, Arc2D.OPEN);
            g2d.draw(arc);
        }

        // Draw origin point
        g2d.setColor(Color.RED);
        g2d.fillOval(centerX - 5, centerY - 5, 10, 10);

        // Draw angle label or question mark
        String angleText = angleEntered ? (degrees + "°") : "?";
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(angleText);
        int textX = centerX + (int) (radius * 0.6 * Math.cos(Math.toRadians(angleEntered ? degrees / 2 : 0))) - textWidth / 2;
        int textY = centerY - (int) (radius * 0.6 * Math.sin(Math.toRadians(angleEntered ? degrees / 2 : 0))) - 5;


        g2d.setColor(Color.RED);
        g2d.drawString(angleText, textX, textY);
    }

    private void loadNextAngle() {
        completedAngles++;

        // 选择下一个目标角度，但不更新currentAngle（保持0度）
        targetAngle = targetAngles[currentIndex % targetAngles.length];
        currentIndex++;

        // 重置UI状态，保持角度为0
        currentAngle = 0;
        angleInputField.setText("");
        inputPromptLabel.setText("Enter angle (0-360, multiples of 10):"); // 重置提示语
        angleInputField.setToolTipText("Enter angle (0-360, multiples of 10)");
        angleInputField.requestFocus();
        currentAttempts = 3;
        angleEntered = false;
        angleTypeLabel.setText("");
        attemptsLabel.setText("Attempts left: 3");
        // 去掉鼓励性提示语
        feedbackLabel.setText(" ");

        // 刷新UI显示0度
        SwingUtilities.invokeLater(() -> angleVisualLabel.repaint());
    }

    private void handleSubmit(ActionEvent e) {
        if (!angleEntered) {
            // First step: Enter angle
            try {
                int angle = Integer.parseInt(angleInputField.getText().trim());
                if (angle < 0 || angle > 360 || angle % 10 != 0) {
                    feedbackLabel.setText("Please enter a multiple of 10 between 0-360 :("); // Changed emoji to text symbol
                    feedbackLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
                    return;
                }

                // 只有用户提交后才更新currentAngle
                currentAngle = angle;
                angleEntered = true;
                angleInputField.setText("");
<<<<<<< HEAD
                inputPromptLabel.setText("Enter angle type (zero, acute, right, obtuse, straight, reflex, full):"); // 更新提示语
                angleInputField.setToolTipText("Enter angle type (zero, acute, right, obtuse, straight, reflex, full)");
=======
                inputPromptLabel.setText("Enter angle type (acute, right, obtuse, reflex): :)"); // Changed emoji to text symbol
                inputPromptLabel.setForeground(new Color(0, 255, 0)); // Green for success in first step
                angleInputField.setToolTipText("Enter angle type (acute, right, obtuse, reflex)");
>>>>>>> JZBUI

                // 刷新UI显示用户输入的角度
                SwingUtilities.invokeLater(() -> angleVisualLabel.repaint());
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number :("); // Changed emoji to text symbol
                feedbackLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
            }
        } else {
            // Second step: Identify angle type
            String input = angleInputField.getText().trim().toLowerCase();
            String correctType = determineAngleType(currentAngle).toLowerCase();

            if (input.equals(correctType)) {
                // Correct answer
                int points = scoreManager.calculatePoints(3 - currentAttempts + 1, false);
                feedbackLabel.setText("Correct! :) " + getRandomCongratulation() + " +" + points + " points"); // Changed emoji to text symbol
                feedbackLabel.setForeground(new Color(0, 255, 0)); // Green for correct
                scoreManager.recordScoreAndFeedback(points);

                // Move to next angle after delay
                Timer timer = new Timer(1000, evt -> loadNextAngle());
                timer.setRepeats(false);
                timer.start();
            } else {
                // Incorrect answer
                currentAttempts--;
                attemptsLabel.setText("Attempts left: " + currentAttempts);

                if (currentAttempts > 0) {
                    feedbackLabel.setText("Try again! :( " + getRandomEncouragement()); // Changed emoji to text symbol
                    feedbackLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
                } else {
                    feedbackLabel.setText("Incorrect! :( Correct answer: " + correctType); // Changed emoji to text symbol
                    feedbackLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
                    angleTypeLabel.setText("This is a " + correctType + " angle");
                    angleTypeLabel.setForeground(new Color(0, 255, 0)); // Green for correct type reveal
                    scoreManager.recordScoreAndFeedback(0);

                    // Move to next angle after delay
                    Timer timer = new Timer(2000, evt -> loadNextAngle());
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }

    private String determineAngleType(int angle) {
        if (angle == 0) return "Zero";
        if (angle < 90) return "Acute";
        if (angle == 90) return "Right";
        if (angle < 180) return "Obtuse";
        if (angle == 180) return "Straight";
        if (angle < 360) return "Reflex";
        return "Full";
    }

    private String getRandomCongratulation() {
        String[] congrats = {"Well done!", "Excellent!", "Perfect!", "You got it!", "Great job!"};
        return congrats[new Random().nextInt(congrats.length)];
    }

    private String getRandomEncouragement() {
        String[] encouragements = {"You can do it!", "Almost there!", "Think carefully!", "One more try!"};
        return encouragements[new Random().nextInt(encouragements.length)];
    }

    private void showCompletionDialog() {
        Object[] options = {"Return to Home", "Try Again"};
        int choice = JOptionPane.showOptionDialog(this,
                "You've completed " + anglesToIdentify +
                        " angle recognitions! Your total score is: " + scoreManager.getCurrentScore() +
                        ". What would you like to do next?",
                "Task Completed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            mainFrame.navigateToHome();
        } else {
            resetState();
        }
        scoreManager.incrementTaskTypeCompletedCount();
    }

    @Override
    public void displayProblem(Problem problem) {
    }

    @Override
    public void showFeedback(Feedback feedback) {
    }

    @Override
    public void setTaskLogicCallback(TaskLogic logic) {
    }

    @Override
    public String getPanelId() {
        return Constants.ANGLE_TYPE_PANEL_ID;
    }

    @Override
    public void resetState() {
        completedAngles = 0;
        currentIndex = 0;
        scoreManager.resetSession();
        loadNextAngle();
    }
}