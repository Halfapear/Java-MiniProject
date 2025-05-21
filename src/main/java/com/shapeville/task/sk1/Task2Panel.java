

package com.shapeville.task.sk1;

import com.shapeville.main.MainFrame;
import com.shapeville.logic.*;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;
import com.shapeville.model.Feedback;
import com.shapeville.model.Problem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.geom.Arc2D;

public class Task2Panel extends JPanel implements TaskPanel {
    private MainFrame mainFrame;
    private ScoreManager scoreManager;
    private JLabel angleVisualLabel;
    private JTextField angleInputField;
    private JButton submitButton;
    private JLabel feedbackLabel;
    private JLabel angleTypeLabel;
    private JLabel attemptsLabel;
    private int currentAttempts = 3;
    private int completedAngles = 0;
    private final int[] targetAngles = {30, 90, 120, 180, 200, 270, 300, 360};
    private int currentIndex = 0;
    private int currentAngle = 0; // 控制UI显示的角度，初始为0
    private int targetAngle = 0;  // 存储目标角度
    private boolean angleEntered = false;
    private static final int VISUALIZATION_WIDTH = 450;
    private static final int VISUALIZATION_HEIGHT = 450;
    private int anglesToIdentify = 4; // 需要识别4种角度类型

    public Task2Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.scoreManager = mainFrame.getScoreManager();
        initializeUI();
        loadNextAngle();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Task 2: Angle Recognition", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);
        add(Box.createVerticalStrut(10));

        // Angle visualization panel
        angleVisualLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAngle(g, currentAngle, getWidth(), getHeight());
            }
        };
        
        angleVisualLabel.setPreferredSize(new Dimension(VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT));
        angleVisualLabel.setMaximumSize(new Dimension(VISUALIZATION_WIDTH, VISUALIZATION_HEIGHT));
        angleVisualLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(angleVisualLabel);
        add(Box.createVerticalStrut(20));

        // Input panel
        JPanel inputPanel = new JPanel();
        angleInputField = new JTextField(10);
        angleInputField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputPanel.add(new JLabel("Enter angle (0-360, multiples of 10):"));
        inputPanel.add(angleInputField);
        add(inputPanel);
        add(Box.createVerticalStrut(10));

        // Angle type display
        angleTypeLabel = new JLabel(" ", SwingConstants.CENTER);
        angleTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        angleTypeLabel.setForeground(new Color(0, 100, 0));
        add(angleTypeLabel);
        add(Box.createVerticalStrut(10));

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.addActionListener(this::handleSubmit);
        add(submitButton);
        add(Box.createVerticalStrut(10));

        // Feedback label
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(feedbackLabel);
        add(Box.createVerticalStrut(5));

        // Attempts counter
        attemptsLabel = new JLabel("Attempts left: 3", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(attemptsLabel);
        add(Box.createVerticalStrut(20));

        // Home button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setPreferredSize(new Dimension(120, 35));
        homeButton.addActionListener(e -> mainFrame.navigateToHome());
        add(homeButton);
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

        // Draw base line (horizontal)
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);

        // Only draw angle line after user input
        if (degrees > 0) {
            double angleRad = Math.toRadians(degrees);
            int endX = (int)(centerX + radius * Math.cos(angleRad));
            int endY = (int)(centerY - radius * Math.sin(angleRad));
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
        g2d.fillOval(centerX-5, centerY-5, 10, 10);

        // Draw angle label
        String angleText = angleEntered ? (degrees + "°") : "0°";
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(angleText);
        int textX = centerX + (int)(radius * 0.6 * Math.cos(Math.toRadians(angleEntered ? degrees/2 : 0))) - textWidth/2;
        int textY = centerY - (int)(radius * 0.6 * Math.sin(Math.toRadians(angleEntered ? degrees/2 : 0))) - 5;
        
        // Text background
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(textX-5, textY-fm.getAscent()-2, textWidth+10, fm.getHeight()+4, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(textX-5, textY-fm.getAscent()-2, textWidth+10, fm.getHeight()+4, 10, 10);
        
        // Draw angle text
        g2d.setColor(Color.RED);
        g2d.drawString(angleText, textX, textY);
    }

    private void loadNextAngle() {
        if (completedAngles >= anglesToIdentify) {
            showCompletionDialog();
            return;
        }
        
        // 选择下一个目标角度，但不更新currentAngle（保持0度）
        targetAngle = targetAngles[currentIndex % targetAngles.length];
        currentIndex++;
        
        // 重置UI状态，保持角度为0
        currentAngle = 0;
        angleInputField.setText("");
        angleInputField.requestFocus();
        currentAttempts = 3;
        angleEntered = false;
        feedbackLabel.setText("Enter the angle shown");
        angleTypeLabel.setText("");
        attemptsLabel.setText("Attempts left: 3");
        
        // 刷新UI显示0度
        SwingUtilities.invokeLater(() -> angleVisualLabel.repaint());
    }

    private void handleSubmit(ActionEvent e) {
        if (!angleEntered) {
            // First step: Enter angle
            try {
                int angle = Integer.parseInt(angleInputField.getText().trim());
                if (angle < 0 || angle > 360 || angle % 10 != 0) {
                    feedbackLabel.setText("Please enter a multiple of 10 between 0-360");
                    return;
                }
                
                // 只有用户提交后才更新currentAngle
                currentAngle = angle;
                angleEntered = true;
                angleInputField.setText("");
                angleInputField.setToolTipText("Enter angle type (acute, right, obtuse, reflex)");
                feedbackLabel.setText("What type of angle is " + angle + "°?");
                
                // 刷新UI显示用户输入的角度
                SwingUtilities.invokeLater(() -> angleVisualLabel.repaint());
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number");
            }
        } else {
            // Second step: Identify angle type
            String input = angleInputField.getText().trim().toLowerCase();
            String correctType = determineAngleType(currentAngle).toLowerCase();
            
            if (input.equals(correctType)) {
                // Correct answer
                int points = scoreManager.calculatePoints(3 - currentAttempts + 1, false);
                feedbackLabel.setText("Correct! " + getRandomCongratulation() + " +" + points + " points");
                angleTypeLabel.setText("This is a " + correctType + " angle");
                scoreManager.recordScoreAndFeedback(points);
                completedAngles++;
                
                // Move to next angle after delay
                Timer timer = new Timer(2000, evt -> loadNextAngle());
                timer.setRepeats(false);
                timer.start();
            } else {
                // Incorrect answer
                currentAttempts--;
                attemptsLabel.setText("Attempts left: " + currentAttempts);
                
                if (currentAttempts > 0) {
                    feedbackLabel.setText("Try again! " + getRandomEncouragement());
                } else {
                    feedbackLabel.setText("Correct answer: " + correctType);
                    angleTypeLabel.setText("This is a " + correctType + " angle");
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
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // 标题
        JLabel titleLabel = new JLabel("Task Completed!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dialogPanel.add(titleLabel, gbc);

        // 说明文本
        gbc.gridy = 1;
        JLabel messageLabel = new JLabel("You've identified all " + anglesToIdentify + " angle types!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogPanel.add(messageLabel, gbc);

        // Home按钮
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 16));
        homeButton.setPreferredSize(new Dimension(120, 40));
        homeButton.addActionListener(e -> {
            mainFrame.navigateToHome();
            ((JDialog) SwingUtilities.getWindowAncestor(this)).dispose();
        });
        dialogPanel.add(homeButton, gbc);

        // Try Again按钮
        gbc.gridx = 1;
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.setFont(new Font("Arial", Font.BOLD, 16));
        tryAgainButton.setPreferredSize(new Dimension(120, 40));
        tryAgainButton.addActionListener(e -> {
            resetState();
            ((JDialog) SwingUtilities.getWindowAncestor(this)).dispose();
        });
        dialogPanel.add(tryAgainButton, gbc);

        // 创建并显示对话框
        JDialog dialog = new JDialog((Frame) mainFrame, "Task Completed", true);
        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override public void displayProblem(Problem problem) {}
    @Override public void showFeedback(Feedback feedback) {}
    @Override public void setTaskLogicCallback(TaskLogic logic) {}
    
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