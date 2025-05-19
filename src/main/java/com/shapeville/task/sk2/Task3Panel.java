package com.shapeville.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.shapeville.main.MainFrame;
import com.shapeville.model.Feedback;
import com.shapeville.logic.ScoreManager;
import com.shapeville.logic.TaskLogic;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;

/**
 * Task 3 Panel: Area Calculation of Shapes (Rectangle, Parallelogram, Triangle, Trapezium).
 * This panel allows the user to select a shape and calculate its area.
 * It includes a 3-minute timer, up to 3 attempts, and displays the correct solution and feedback after each round.
 */
public class Task3Panel extends JPanel implements TaskPanel {
    private MainFrame mainFrameRef;
    private JComboBox<String> shapeSelector;
    private JLabel timeLabel;
    private JLabel attemptsLabel;
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton submitButton;
    private JLabel resultMessageLabel;
    private JLabel formulaLabel;
    private Timer timer;
    private int timeRemaining;
    private int attemptsUsed;
    private boolean problemActive;
    private String currentShape;
    private double currentCorrectAnswer;
    private String currentFormula;
    // Track which shapes have been completed in this session
    private java.util.Set<String> completedShapes;

    public Task3Panel(MainFrame mainFrame) {
        this.mainFrameRef = mainFrame;
        this.completedShapes = new java.util.HashSet<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Title label
        JLabel titleLabel = new JLabel("Task 3: Area Calculation of Shapes", SwingConstants.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);
        add(Box.createVerticalStrut(15));

        // Top panel: shape selection and status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        JLabel selectLabel = new JLabel("Select shape:");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(selectLabel);

        String[] shapes = {"Rectangle", "Parallelogram", "Triangle", "Trapezium"};
        shapeSelector = new JComboBox<>(shapes);
        shapeSelector.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(shapeSelector);

        timeLabel = new JLabel("Time left: --:--");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(timeLabel);
        attemptsLabel = new JLabel("Attempts left: " + Constants.DEFAULT_MAX_ATTEMPTS);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(attemptsLabel);

        add(topPanel);
        add(Box.createVerticalStrut(10));

        // Question label
        questionLabel = new JLabel(" ", SwingConstants.LEFT);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(questionLabel);
        add(Box.createVerticalStrut(10));

        // Answer input panel
        JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel answerLabel = new JLabel("Your answer:");
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        answerField = new JTextField(10);
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        answerPanel.add(answerLabel);
        answerPanel.add(answerField);
        answerPanel.add(submitButton);
        add(answerPanel);
        add(Box.createVerticalStrut(10));

        // Feedback panel for result message and formula
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        resultMessageLabel = new JLabel(" ");
        resultMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formulaLabel = new JLabel(" ");
        formulaLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackPanel.add(resultMessageLabel);
        feedbackPanel.add(formulaLabel);
        add(feedbackPanel);

        // Listener for shape selection (starts a new problem when a shape is selected)
        shapeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (problemActive) {
                    // Ignore selection changes during an active problem
                    return;
                }
                String shape = (String) shapeSelector.getSelectedItem();
                if (shape != null) {
                    if (completedShapes.contains(shape)) {
                        // Shape already completed – notify user (should not normally happen since shape remains selected)
                        JOptionPane.showMessageDialog(Task3Panel.this,
                                "You have already completed the " + shape.toLowerCase() + " area task.",
                                "Task Already Completed", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    startNewShapeProblem(shape);
                }
            }
        });

        // Listener for answer submission
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!problemActive) return;
                handleSubmit();
            }
        });
    }

    private void startNewShapeProblem(String shape) {
        // Begin a new problem for the selected shape
        currentShape = shape;
        problemActive = true;
        attemptsUsed = 0;
        timeRemaining = Constants.TASK_TIME_LIMIT_SEC;  // 180 seconds
        // Reset and disable/enable appropriate UI components
        shapeSelector.setEnabled(false);
        answerField.setText("");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        resultMessageLabel.setText(" ");
        formulaLabel.setText(" ");
        attemptsLabel.setText("Attempts left: " + Constants.DEFAULT_MAX_ATTEMPTS);
        timeLabel.setText("Time left: 03:00");

        // Generate random dimensions and prepare question and solution formula
        String questionText = "";
        if (shape.equals("Rectangle")) {
            int length = 1 + (int)(Math.random() * 20);
            int width  = 1 + (int)(Math.random() * 20);
            questionText = "Calculate the area of a rectangle with length " + length + " and width " + width + ".";
            double area = length * width;
            currentCorrectAnswer = area;
            currentFormula = "Area = length × width = " + length + " × " + width + " = " + formatNumber(area);
        } else if (shape.equals("Parallelogram")) {
            int base   = 1 + (int)(Math.random() * 20);
            int height = 1 + (int)(Math.random() * 20);
            questionText = "Calculate the area of a parallelogram with base " + base + " and height " + height + ".";
            double area = base * height;
            currentCorrectAnswer = area;
            currentFormula = "Area = base × height = " + base + " × " + height + " = " + formatNumber(area);
        } else if (shape.equals("Triangle")) {
            int base   = 1 + (int)(Math.random() * 20);
            int height = 1 + (int)(Math.random() * 20);
            questionText = "Calculate the area of a triangle with base " + base + " and height " + height + ".";
            double area = 0.5 * base * height;
            currentCorrectAnswer = area;
            currentFormula = "Area = ½ × base × height = 0.5 × " + base + " × " + height + " = " + formatNumber(area);
        } else if (shape.equals("Trapezium")) {
            int a = 1 + (int)(Math.random() * 20);  // base1
            int b = 1 + (int)(Math.random() * 20);  // base2
            int h = 1 + (int)(Math.random() * 20);  // height
            questionText = "Calculate the area of a trapezium with base1 = " + a + ", base2 = " + b + ", and height " + h + ".";
            double area = 0.5 * (a + b) * h;
            currentCorrectAnswer = area;
            currentFormula = "Area = ½ × (a + b) × h = 0.5 × (" + a + " + " + b + ") × " + h + " = " + formatNumber(area);
        }
        questionLabel.setText(questionText);

        // Start the countdown timer (3 minutes)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timeLabel.setText(String.format("Time left: %02d:%02d", minutes, seconds));
                if (timeRemaining <= 0) {
                    // Time is up
                    timer.stop();
                    timeLabel.setText("Time left: 00:00");
                    problemActive = false;
                    answerField.setEnabled(false);
                    submitButton.setEnabled(false);
                    // Show correct solution and feedback
                    resultMessageLabel.setText("Time's up! The solution is shown below.");
                    formulaLabel.setText(currentFormula);
                    completedShapes.add(currentShape);
                    // After timeout, no score is awarded
                    // Check if all shapes are done
                    if (completedShapes.size() == shapes.length) {
                        // Completed all 4 shapes
                        JOptionPane.showMessageDialog(mainFrameRef,
                                "Congratulations, you have completed all 4 shapes.",
                                "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                        mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
                    } else {
                        // Enable selection for the next shape
                        shapeSelector.setEnabled(true);
                        // The combo will remain on the last selected item; user can choose another
                    }
                }
            }
        });
        timer.start();
    }

    private void handleSubmit() {
        String answerText = answerField.getText().trim();
        if (answerText.isEmpty()) {
            return; // do nothing if no answer entered
        }
        double userAnswer;
        try {
            userAnswer = Double.parseDouble(answerText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        attemptsUsed++;
        if (Math.abs(userAnswer - currentCorrectAnswer) < 1e-6) {
            // Correct answer entered
            timer.stop();
            problemActive = false;
            answerField.setEnabled(false);
            submitButton.setEnabled(false);
            completedShapes.add(currentShape);
            // Award points based on attempts used (Basic scoring)
            ScoreManager scoreManager = mainFrameRef.getScoreManager();
            int points = scoreManager.calculatePoints(attemptsUsed, Constants.SCORE_BASIC);
            scoreManager.recordScoreAndFeedback(points);
            // Show success feedback and solution
            resultMessageLabel.setText("Correct!");
            formulaLabel.setText(currentFormula);
            // Check if all 4 shapes have been completed
            if (completedShapes.size() == shapes.length) {
                JOptionPane.showMessageDialog(mainFrameRef,
                        "Congratulations, you have completed all 4 shapes.",
                        "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
            } else {
                // Enable selection for another shape
                shapeSelector.setEnabled(true);
            }
        } else {
            // Incorrect answer
            if (attemptsUsed < Constants.DEFAULT_MAX_ATTEMPTS) {
                // There are attempts remaining – prompt to try again
                JOptionPane.showMessageDialog(this, "Incorrect. Try again.",
                        "Incorrect Answer", JOptionPane.INFORMATION_MESSAGE);
                answerField.setText("");
                answerField.requestFocus();
                attemptsLabel.setText("Attempts left: " + (Constants.DEFAULT_MAX_ATTEMPTS - attemptsUsed));
            } else {
                // No attempts left (third incorrect attempt)
                timer.stop();
                problemActive = false;
                answerField.setEnabled(false);
                submitButton.setEnabled(false);
                completedShapes.add(currentShape);
                // Show failure feedback and correct solution
                resultMessageLabel.setText("No attempts left. The solution is shown below.");
                formulaLabel.setText(currentFormula);
                // No points are awarded since the answer was not correct within 3 attempts
                if (completedShapes.size() == shapes.length) {
                    JOptionPane.showMessageDialog(mainFrameRef,
                            "Congratulations, you have completed all 4 shapes.",
                            "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                    mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
                } else {
                    shapeSelector.setEnabled(true);
                }
            }
        }
    }

    // Helper to format numbers for display (no trailing .0 for integers, one decimal for .5 values, two decimals otherwise)
    private String formatNumber(double value) {
        if (Math.abs(value - Math.round(value)) < 1e-6) {
            return String.valueOf((long)Math.round(value));           // Integer value
        } else if (Math.abs(value * 2 - Math.round(value * 2)) < 1e-6) {
            return String.format("%.1f", value);                      // One decimal place (e.g., x.5)
        } else {
            return String.format("%.2f", value);                      // Two decimal places
        }
    }

    // TaskPanel interface methods
    @Override
    public void displayProblem(com.shapeville.model.Problem problem) {
        // Not used explicitly in this integrated implementation
    }

    @Override
    public void showFeedback(Feedback feedback) {
        // Not used explicitly; feedback is shown via labels/dialogs in the UI
    }

    @Override
    public void setTaskLogicCallback(TaskLogic logic) {
        // Not used; UI and logic are combined in this panel
    }

    @Override
    public String getPanelId() {
        return Constants.AREA_CALC_PANEL_ID;
    }

    @Override
    public void resetState() {
        // Clean up when leaving this panel (e.g., returning Home)
        if (timer != null) {
            timer.stop();
        }
        problemActive = false;
    }
}
