package com.shapeville.task.sk2;

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
 * Task 4 Panel: Circle Area and Circumference calculations.
 * Allows the user to choose between Area or Circumference mode. A random radius or diameter is provided,
 * and the user must calculate the area or circumference accordingly.
 * Includes a 3-minute timer, up to 3 attempts, and shows the formula with substituted values after each round.
 */
public class Task4Panel extends JPanel implements TaskPanel {
    private MainFrame mainFrameRef;
    private JButton areaButton;
    private JButton circumferenceButton;
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
    // Flags for completed scenario combinations
    private boolean doneAreaRadius = false;
    private boolean doneAreaDiameter = false;
    private boolean doneCircRadius = false;
    private boolean doneCircDiameter = false;
    // Current problem context
    private boolean currentIsArea;
    private boolean givenIsRadius;
    private double currentCorrectAnswer;
    private String currentFormula;

    public Task4Panel(MainFrame mainFrame) {
        this.mainFrameRef = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Title label
        JLabel titleLabel = new JLabel("Task 4: Circle Area and Circumference", SwingConstants.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);
        add(Box.createVerticalStrut(15));

        // Top panel: mode selection and status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        JLabel modeLabel = new JLabel("Select mode:");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(modeLabel);
        areaButton = new JButton("Area");
        areaButton.setFont(new Font("Arial", Font.PLAIN, 16));
        circumferenceButton = new JButton("Circumference");
        circumferenceButton.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(areaButton);
        topPanel.add(circumferenceButton);
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

        // Mode selection button listeners
        areaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (problemActive) return;
                startNewCircleProblem(true);  // true = Area mode
            }
        });
        circumferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (problemActive) return;
                startNewCircleProblem(false); // false = Circumference mode
            }
        });

        // Answer submission listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!problemActive) return;
                handleSubmit();
            }
        });
    }

    private void startNewCircleProblem(boolean isAreaMode) {
        // Ensure the selected mode still has an unseen scenario
        if (isAreaMode && doneAreaRadius && doneAreaDiameter) {
            JOptionPane.showMessageDialog(this,
                    "You have completed all Area calculations for the circle.",
                    "Mode Complete", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!isAreaMode && doneCircRadius && doneCircDiameter) {
            JOptionPane.showMessageDialog(this,
                    "You have completed all Circumference calculations for the circle.",
                    "Mode Complete", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        currentIsArea = isAreaMode;
        // Decide whether to use radius or diameter for this problem
        if (isAreaMode) {
            if (!doneAreaRadius && !doneAreaDiameter) {
                givenIsRadius = (Math.random() < 0.5);
            } else if (!doneAreaRadius) {
                givenIsRadius = true;
            } else if (!doneAreaDiameter) {
                givenIsRadius = false;
            } else {
                return;  // no scenario left (should not reach here due to checks above)
            }
        } else {
            if (!doneCircRadius && !doneCircDiameter) {
                givenIsRadius = (Math.random() < 0.5);
            } else if (!doneCircRadius) {
                givenIsRadius = true;
            } else if (!doneCircDiameter) {
                givenIsRadius = false;
            } else {
                return;
            }
        }
        problemActive = true;
        attemptsUsed = 0;
        timeRemaining = Constants.TASK_TIME_LIMIT_SEC;
        // Disable mode buttons and reset UI for new problem
        areaButton.setEnabled(false);
        circumferenceButton.setEnabled(false);
        answerField.setText("");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        resultMessageLabel.setText(" ");
        formulaLabel.setText(" ");
        attemptsLabel.setText("Attempts left: " + Constants.DEFAULT_MAX_ATTEMPTS);
        timeLabel.setText("Time left: 03:00");

        // Generate a random value (1–20) for radius or diameter
        int value = 1 + (int)(Math.random() * 20);
        String valueDescription = (givenIsRadius ? "radius " + value : "diameter " + value);
        if (currentIsArea) {
            // Area calculation scenario
            questionLabel.setText("Calculate the area of a circle with " + valueDescription + ".");
            double r = givenIsRadius ? value : (value / 2.0);
            double area = Math.PI * r * r;
            currentCorrectAnswer = area;
            if (givenIsRadius) {
                currentFormula = "Area = π × r² = π × " + value + "² = " + formatNumber(area);
            } else {
                currentFormula = "Area = π × (d/2)² = π × (" + value + "/2)² = " + formatNumber(area);
            }
        } else {
            // Circumference calculation scenario
            questionLabel.setText("Calculate the circumference of a circle with " + valueDescription + ".");
            if (givenIsRadius) {
                double circumference = 2 * Math.PI * value;
                currentCorrectAnswer = circumference;
                currentFormula = "Circumference = 2πr = 2 × π × " + value + " = " + formatNumber(circumference);
            } else {
                double circumference = Math.PI * value;
                currentCorrectAnswer = circumference;
                currentFormula = "Circumference = π × d = π × " + value + " = " + formatNumber(circumference);
            }
        }

        // Start the 3-minute timer
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
                    // Show solution and mark scenario as done
                    resultMessageLabel.setText("Time's up! The solution is shown below.");
                    formulaLabel.setText(currentFormula);
                    markCurrentScenarioDone();
                    // Check if all four scenarios (area/circumference × radius/diameter) are completed
                    if (allScenariosDone()) {
                        JOptionPane.showMessageDialog(mainFrameRef,
                                "Congratulations, you have completed all circle calculations.",
                                "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                        mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
                    } else {
                        updateModeButtons();  // re-enable any remaining mode for further practice
                    }
                }
            }
        });
        timer.start();
    }

    private void handleSubmit() {
        String answerText = answerField.getText().trim();
        if (answerText.isEmpty()) {
            return;
        }
        double userAnswer;
        try {
            userAnswer = Double.parseDouble(answerText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        attemptsUsed++;
        // Use a tolerance for correctness because of π (accept answers within ~1.0 of true value)
        double tolerance = 1.0;
        if (Math.abs(userAnswer - currentCorrectAnswer) < tolerance) {
            // Correct answer
            timer.stop();
            problemActive = false;
            answerField.setEnabled(false);
            submitButton.setEnabled(false);
            markCurrentScenarioDone();
            // Award points (Basic scoring)
            ScoreManager scoreManager = mainFrameRef.getScoreManager();
            int points = scoreManager.calculatePoints(attemptsUsed, Constants.SCORE_BASIC);
            scoreManager.recordScoreAndFeedback(points);
            // Show success feedback and formula
            resultMessageLabel.setText("Correct!");
            formulaLabel.setText(currentFormula);
            if (allScenariosDone()) {
                JOptionPane.showMessageDialog(mainFrameRef,
                        "Congratulations, you have completed all circle calculations.",
                        "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
            } else {
                updateModeButtons();
            }
        } else {
            // Incorrect answer
            if (attemptsUsed < Constants.DEFAULT_MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this, "Incorrect. Try again.",
                        "Incorrect Answer", JOptionPane.INFORMATION_MESSAGE);
                answerField.setText("");
                answerField.requestFocus();
                attemptsLabel.setText("Attempts left: " + (Constants.DEFAULT_MAX_ATTEMPTS - attemptsUsed));
            } else {
                // Third attempt incorrect – no more attempts
                timer.stop();
                problemActive = false;
                answerField.setEnabled(false);
                submitButton.setEnabled(false);
                markCurrentScenarioDone();
                resultMessageLabel.setText("No attempts left. The solution is shown below.");
                formulaLabel.setText(currentFormula);
                if (allScenariosDone()) {
                    JOptionPane.showMessageDialog(mainFrameRef,
                            "Congratulations, you have completed all circle calculations.",
                            "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                    mainFrameRef.getTaskManager().currentTaskTypeCompleted(new TaskLogic(){});
                } else {
                    updateModeButtons();
                }
            }
        }
    }

    // Helper to mark the current scenario (combination of mode and input type) as completed
    private void markCurrentScenarioDone() {
        if (currentIsArea) {
            if (givenIsRadius) {
                doneAreaRadius = true;
            } else {
                doneAreaDiameter = true;
            }
        } else {
            if (givenIsRadius) {
                doneCircRadius = true;
            } else {
                doneCircDiameter = true;
            }
        }
    }

    // Check if all four scenario combinations have been completed
    private boolean allScenariosDone() {
        return doneAreaRadius && doneAreaDiameter && doneCircRadius && doneCircDiameter;
    }

    // Enable or disable the mode buttons based on remaining tasks
    private void updateModeButtons() {
        if (!doneAreaRadius || !doneAreaDiameter) {
            areaButton.setEnabled(true);
        } else {
            areaButton.setEnabled(false);
        }
        if (!doneCircRadius || !doneCircDiameter) {
            circumferenceButton.setEnabled(true);
        } else {
            circumferenceButton.setEnabled(false);
        }
    }

    // Format numbers to two decimal places for display of π calculations
    private String formatNumber(double value) {
        return String.format("%.2f", value);
    }

    // TaskPanel interface methods
    @Override
    public void displayProblem(com.shapeville.model.Problem problem) {
        // Not used in this implementation
    }

    @Override
    public void showFeedback(Feedback feedback) {
        // Not used (feedback is shown via UI components directly)
    }

    @Override
    public void setTaskLogicCallback(TaskLogic logic) {
        // Not used
    }

    @Override
    public String getPanelId() {
        return Constants.CIRCLE_CALC_PANEL_ID;
    }

    @Override
    public void resetState() {
        if (timer != null) {
            timer.stop();
        }
        problemActive = false;
    }
}
