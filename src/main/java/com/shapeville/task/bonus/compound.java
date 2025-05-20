package com.shapeville.task.bonus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities; 
/**
 * Bonus 1: Compound Shape Area Calculation
 * Allows users to select a compound shape and calculate its area.
 */
public class compound extends JPanel {
    private JTextField answerField;
    private int attempt = 0;
    private int currentShapeIndex = 0;
    private JLabel shapeImageLabel;
    private JLabel questionLabel;
    private JLabel timerLabel;
    private List<CompoundShape> shapes;
    private int practisedShapes = 0;
    private JPanel content;
    private Timer timer;
    private int timeRemaining = 300; // 5 minutes (300 seconds)
    private boolean timerRunning = false;

    /**
     * Constructor
     */
    public compound() {
        setLayout(new BorderLayout());
        content = new JPanel();
        add(content, BorderLayout.CENTER);
        initializeShapes();
        setupUI();
    }

    /**
     * Initializes all compound shapes with their descriptions, questions, correct answers, and solution hints.
     */
    private void initializeShapes() {
        shapes = new ArrayList<>();
        
        // Add 6 compound shapes from Figure 10
        // Shape 1: L-shaped (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 1: L-shaped Compound Rectangle",
            "Calculate the total area of the compound shape (unit: square centimeters)",
            310, // 20*10 + 11*10 = 200 + 110 = 310
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"},
            createShapeImage(1)
        ));
        
        // Shape 2: Rectangle
        shapes.add(new CompoundShape(
            "Shape 2: L-shaped Compound Rectangle",
            "Calculate the total area of the compound shape (unit: square centimeters)",
            598, // 16*16 + 19*18 = 256 + 324 = 598
            new String[]{"Rectangle area = length × width"},
            createShapeImage(2)
        ));
        
        // Shape 3: L-shaped (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 3: Convex-shaped Compound Rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            288, // 24*6 + 12*12 = 288
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"},
            createShapeImage(3)
        ));
        
        // Shape 4: Trapezoid
        shapes.add(new CompoundShape(
            "Shape 4: Trapezoid",
            "Calculate the area of the trapezoid (unit: square meters)",
            18, // (3+6)*4/2 = 18
            new String[]{"Trapezoid area = (top base + bottom base) × height ÷ 2"},
            createShapeImage(4)
        ));
        
        // Shape 5: L-shaped (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 5: Convex-shaped Compound Rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            3456, // 36*36 + 60*36 = 3456
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"},
            createShapeImage(5)
        ));
        
        // Shape 6: L-shaped (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 6: L-shaped Compound Rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            174, // 10*11 + 8*8 = 110 + 64 = 174 (or approximate value)
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"},
            createShapeImage(6)
        ));
    }

    /**
     * Creates a shape image.
     * @param shapeNumber The shape number.
     * @return The shape image.
     */
    private ImageIcon createShapeImage(int shapeNumber) {
        // Load image from resources/assets/compound directory
        String imagePath = "/resources/assets/compound/compound" + shapeNumber + ".png";
        return com.shapeville.utils.ImageLoader.loadImageAndScale(imagePath, 400, 300);
    }

    /**
     * Sets up the user interface components.
     */
    private void setupUI() {
        content.setLayout(new BorderLayout(10, 10));
        
        // North area for title and timer
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bonus 1: Compound Shape Area Calculation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        
        timerLabel = new JLabel("Time Remaining: 5:00", SwingConstants.CENTER);
        northPanel.add(timerLabel, BorderLayout.CENTER);
        
        content.add(northPanel, BorderLayout.NORTH);
        
        // Center area for problem description and shape image
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        centerPanel.add(questionLabel, BorderLayout.NORTH);
        
        shapeImageLabel = new JLabel("", SwingConstants.CENTER);
        shapeImageLabel.setPreferredSize(new Dimension(400, 300));
        centerPanel.add(shapeImageLabel, BorderLayout.CENTER);
        
        content.add(centerPanel, BorderLayout.CENTER);
        
        // South area for input and buttons
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JLabel answerLabel = new JLabel("Enter Area:");
        southPanel.add(answerLabel);
        
        answerField = new JTextField(10);
        southPanel.add(answerField);
        
        JButton submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(e -> checkAnswer(answerField.getText()));
        southPanel.add(submitButton);
        
        // Add shape selection buttons
        JPanel shapeSelectionPanel = new JPanel();
        shapeSelectionPanel.setLayout(new GridLayout(2, 3, 5, 5));
        
        for (int i = 0; i < shapes.size(); i++) {
            final int index = i;
            JButton shapeButton = new JButton("Shape " + (i + 1));
            shapeButton.addActionListener(e -> {
                currentShapeIndex = index;
                resetTimer();
                showCurrentShape();
            });
            shapeSelectionPanel.add(shapeButton);
        }
        
        // Add home button
        JButton homeButton = new JButton("Return to Home");
        homeButton.addActionListener(e -> {
            // Logic to return to the home page
            if (timer != null) {
                timer.cancel();
            }
             // 获取MainFrame实例并导航到主页
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof com.shapeville.main.MainFrame) {
                ((com.shapeville.main.MainFrame) topFrame).navigateToHome();
            }
        });
        shapeSelectionPanel.add(homeButton);
        
        content.add(shapeSelectionPanel, BorderLayout.SOUTH);
        content.add(southPanel, BorderLayout.AFTER_LAST_LINE);
        
        // Display the first shape
        showCurrentShape();
        startTimer();
    }
    
    /**
     * Displays the current shape information.
     */
    private void showCurrentShape() {
        CompoundShape currentShape = shapes.get(currentShapeIndex);
        questionLabel.setText("<html><body style='width: 400px'>" + 
                              currentShape.getDescription() + "<br>" + 
                              currentShape.getQuestion() + "</body></html>");
        
        // Display shape image
        if (currentShape.getImage() != null) {
            shapeImageLabel.setIcon(currentShape.getImage());
            shapeImageLabel.setText("");
        } else {
            // If no image, display shape description
            shapeImageLabel.setIcon(null);
            shapeImageLabel.setText("Shape " + (currentShapeIndex + 1) + " / " + shapes.size());
        }
        
        // Reset attempt count
        attempt = 0;
    }
    
    /**
     * Starts the countdown timer.
     */
    private void startTimer() {
        if (timerRunning) {
            return;
        }
        
        timerRunning = true;
        timeRemaining = 300; // 5 minutes
        updateTimerLabel();
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    SwingUtilities.invokeLater(() -> updateTimerLabel());
                } else {
                    // Time's up
                    timer.cancel();
                    timerRunning = false;
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(compound.this, 
                                "Time's up! Let's see the solution.");
                        showSolution();
                    });
                }
            }
        }, 1000, 1000);
    }
    
    /**
     * Resets the timer.
     */
    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timerRunning = false;
        startTimer();
    }
    
    /**
     * Updates the timer label.
     */
    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %d:%02d", minutes, seconds));
    }

    /**
     * Checks if the user's answer is correct.
     * @param input The user's input answer.
     */
    private void checkAnswer(String input) {
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer!");
            return;
        }
        
        try {
            double userAnswer = Double.parseDouble(input);
            CompoundShape currentShape = shapes.get(currentShapeIndex);
            
            // Check if the answer is correct (allowing a 5% error margin)
            if (Math.abs(userAnswer - currentShape.getCorrectAnswer()) / currentShape.getCorrectAnswer() <= 0.05) {
                // Stop the timer
                if (timer != null) {
                    timer.cancel();
                    timerRunning = false;
                }
        
                  // 记录分数 - 根据尝试次数计算得分
                recordScore(attempt + 1);
                
                JOptionPane.showMessageDialog(this, "Correct Answer!");
                practisedShapes++;

              
                
                // Mark this shape as completed
                currentShape.setCompleted(true);
                
                // Check if all shapes are completed
                if (practisedShapes >= shapes.size()) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have completed all compound shape exercises!");
                    // Return to home page or proceed to the next task
                } else {
                    // Move to the next incomplete shape
                    moveToNextShape();
                }
            } else {
                attempt++;
                if (attempt >= 3) {
                    // Stop the timer
                    if (timer != null) {
                        timer.cancel();
                        timerRunning = false;
                    }
                    
                    showSolution();
                    
                    // Mark this shape as completed
                    currentShape.setCompleted(true);
                    practisedShapes++;
                    
                    // Check if all shapes are completed
                    if (practisedShapes >= shapes.size()) {
                        JOptionPane.showMessageDialog(this, "You have completed all compound shape exercises!");
                        // Return to home page or proceed to the next task
                    } else {
                        // Move to the next incomplete shape
                        moveToNextShape();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer, please try again!\nYou have " + (3 - attempt) + " attempts remaining.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }
    

    /**
     * Displays the solution for the current shape.
     */
    private void showSolution() {
        CompoundShape currentShape = shapes.get(currentShapeIndex);
        
        StringBuilder solution = new StringBuilder();
        solution.append("The correct answer is: ").append(currentShape.getCorrectAnswer()).append("\n\n");
        solution.append("Solution:\n");
        for (String hint : currentShape.getHints()) {
            solution.append("- ").append(hint).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, solution.toString(), 
                "Solution for Shape " + (currentShapeIndex + 1), JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Moves to the next incomplete shape.
     */
    private void moveToNextShape() {
        // Find the next incomplete shape
        int nextIndex = (currentShapeIndex + 1) % shapes.size();
        while (shapes.get(nextIndex).isCompleted() && nextIndex != currentShapeIndex) {
            nextIndex = (nextIndex + 1) % shapes.size();
        }
        
        currentShapeIndex = nextIndex;
        answerField.setText("");
        showCurrentShape();
    }
    
    // 添加记录分数的方法
/**
 * 记录用户得分
 * @param attemptsUsed 用户使用的尝试次数
 */
private void recordScore(int attemptsUsed) {
    // 获取MainFrame实例
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    if (topFrame instanceof com.shapeville.main.MainFrame) {
        com.shapeville.main.MainFrame mainFrame = (com.shapeville.main.MainFrame) topFrame;
        // 获取ScoreManager并记录分数
        com.shapeville.logic.ScoreManager scoreManager = mainFrame.getScoreManager();
        if (scoreManager != null) {
            // 复合形状计算是高级任务，使用高级评分
            int points = scoreManager.calculatePoints(attemptsUsed, true);
            scoreManager.recordScoreAndFeedback(points);
        }
    }
}
    /**
     * Inner class representing a compound shape.
     */
    private class CompoundShape {
        private String description;
        private String question;
        private double correctAnswer;
        private String[] hints;
        private ImageIcon image;
        private boolean completed;
        
        /**
         * Constructor for CompoundShape.
         * @param description The description of the shape.
         * @param question The question about the shape.
         * @param correctAnswer The correct area value.
         * @param hints The solution hints.
         * @param image The shape image.
         */
        public CompoundShape(String description, String question, double correctAnswer, String[] hints, ImageIcon image) {
            this.description = description;
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.hints = hints;
            this.image = image;
            this.completed = false;
        }
        
        /**
         * Gets the description of the shape.
         * @return The description.
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Gets the question about the shape.
         * @return The question.
         */
        public String getQuestion() {
            return question;
        }
        
        /**
         * Gets the correct answer (area).
         * @return The correct area value.
         */
        public double getCorrectAnswer() {
            return correctAnswer;
        }
        
        /**
         * Gets the solution hints.
         * @return The hints as an array of strings.
         */
        public String[] getHints() {
            return hints;
        }
        
        /**
         * Gets the shape image.
         * @return The image.
         */
        public ImageIcon getImage() {
            return image;
        }
        
        /**
         * Checks if this shape is completed.
         * @return True if completed, otherwise false.
         */
        public boolean isCompleted() {
            return completed;
        }
        
        /**
         * Sets the completion status of this shape.
         * @param completed The completion status.
         */
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}