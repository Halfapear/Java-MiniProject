package com.shapeville.task.sk1.shape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Shape Recognition Educational Application - Main Program Entry
 */
public class ShapeRecognitionApp extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    // Different panels of the application
    private WelcomePanel welcomePanel;
    private TaskPanel taskPanel;
    private ResultsPanel resultsPanel;
    
    // Task data
    private ShapeTask currentTask;
    private boolean is3DTask = false;
    private int totalScore = 0;
    
    public ShapeRecognitionApp() {
        // Set basic window properties
        setTitle("Shape Recognition Educational App");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        
        // Initialize panels
        welcomePanel = new WelcomePanel();
        taskPanel = new TaskPanel();
        resultsPanel = new ResultsPanel();
        
        // Show the welcome panel
        showPanel(welcomePanel);
        
        // Register panel event listeners
        registerListeners();
    }
    
    /**
     * Show the specified panel
     */
    private void showPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
    
    /**
     * Register panel event listeners
     */
    private void registerListeners() {
        // Welcome panel events
        welcomePanel.addStart2DListener(e -> startTask(false));
        welcomePanel.addStart3DListener(e -> startTask(true));
        
        // Task panel events
        taskPanel.addSubmitListener(e -> checkAnswer());
        taskPanel.addHomeListener(e -> showPanel(welcomePanel));
        taskPanel.addFinishListener(e -> showResults());
        
        // Results panel events
        resultsPanel.addHomeListener(e -> showPanel(welcomePanel));
    }
    
    /**
     * Start a new task
     */
    private void startTask(boolean is3D) {
        is3DTask = is3D;
        currentTask = new ShapeTask(is3D);
        totalScore = 0;
        taskPanel.initializeTask(is3D);
        showPanel(taskPanel);
        loadNextShape();
    }
    
    /**
     * Load the next shape
     */
    private void loadNextShape() {
        String imagePath = currentTask.getRandomShape();
        taskPanel.loadShapeImage(imagePath);
        taskPanel.setInstructions("Please enter the name of this " + (is3DTask ? "3D" : "2D") + " shape:");
        taskPanel.clearInput();
        taskPanel.setAttemptsLeft(3);
    }
    
    /**
     * Check the user's answer
     */
    private void checkAnswer() {
        String userInput = taskPanel.getUserInput().trim();
        if (userInput.isEmpty()) {
            taskPanel.showMessage("Please enter the shape name!", Color.RED);
            return;
        }
        
        boolean isCorrect = currentTask.validateAnswer(userInput);
        int attemptsLeft = currentTask.getAttemptsLeft();
        
        if (isCorrect) {
            totalScore += currentTask.getCurrentPoints();
            taskPanel.updateScore(totalScore);
            taskPanel.showMessage("Great! Correct answer!", Color.GREEN);
            
            // Load next shape after a delay
            Timer timer = new Timer(1500, e -> {
                if (currentTask.isTaskComplete()) {
                    showResults();
                } else {
                    loadNextShape();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else if (attemptsLeft > 0) {
            taskPanel.showMessage("Wrong answer, please try again!", Color.RED);
            taskPanel.setAttemptsLeft(attemptsLeft);
        } else {
            taskPanel.showMessage("The correct answer is: " + currentTask.getCorrectAnswer(), Color.BLUE);
            
            // Load next shape after a delay
            Timer timer = new Timer(2000, e -> {
                if (currentTask.isTaskComplete()) {
                    showResults();
                } else {
                    loadNextShape();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    /**
     * Show the results panel
     */
    private void showResults() {
        resultsPanel.setResults(totalScore, is3DTask);
        showPanel(resultsPanel);
    }
    
    public static void main(String[] args) {
        // Create and display the GUI in the event dispatch thread
        SwingUtilities.invokeLater(() -> {
            ShapeRecognitionApp app = new ShapeRecognitionApp();
            app.setVisible(true);
        });
    }
}    