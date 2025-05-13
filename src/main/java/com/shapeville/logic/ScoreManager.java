package com.shapeville.logic;

import com.shapeville.ui.NavigationBar; // To update the UI

/**
 * Manages the player's score and progress throughout the session.
 * Applies scoring rules based on project specifications.
 */
public class ScoreManager {

    private int currentScore;
    private int tasksCompletedInSession;
    private NavigationBar navigationBar; // Reference to update UI

    public ScoreManager() {
        this.currentScore = 0;
        this.tasksCompletedInSession = 0;
    }

    /**
     * Injects the NavigationBar reference so the ScoreManager can update the displayed score/progress.
     * Should be called by MainFrame after both are initialized.
     * @param navigationBar The NavigationBar instance.
     */
    public void setNavigationBar(NavigationBar navigationBar) {
        this.navigationBar = navigationBar;
        updateUI(); // Update UI with initial values
    }

    /**
     * Adds points to the score based on the answer's correctness, attempts, and task difficulty.
     * Implements the scoring rules from Table 1 in the project specification.
     *
     * @param attemptsUsed    Number of attempts taken (1, 2, or 3).
     * @param isAdvancedTask True if the task uses Advanced scoring, false for Basic.
     */
    public int calculatePoints(int attemptsUsed, boolean isAdvancedTask) {
        // TODO: Implement scoring logic based on Table 1
        int points = 0;
        if (isAdvancedTask) { // Advanced Scoring
            switch (attemptsUsed) {
                case 1: points = 6; break;
                case 2: points = 4; break;
                case 3: points = 2; break;
            }
        } else { // Basic Scoring
            switch (attemptsUsed) {
                case 1: points = 3; break;
                case 2: points = 2; break;
                case 3: points = 1; break;
            }
        }
        return points;
    }

    /**
     * Adds the calculated score and provides positive feedback.
     * @param pointsToAdd Points calculated by calculatePoints.
     */
    public void recordScore(int pointsToAdd) {
        if (pointsToAdd > 0) {
             this.currentScore += pointsToAdd;
             System.out.println("Score updated: " + currentScore); // Debugging
             // TODO: Trigger a "Great Job!" feedback mechanism (maybe via MainFrame or a dedicated FeedbackManager)
             // For now, just update the UI
             updateUI();
             // Consider showing a JOptionPane "Great Job" here or letting the TaskPanel handle it based on Feedback object
        }
    }


    public void incrementTasksCompleted() {
        this.tasksCompletedInSession++;
        updateUI();
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getTasksCompleted() {
        return tasksCompletedInSession;
    }

    public void resetSession() {
        this.currentScore = 0;
        this.tasksCompletedInSession = 0;
        updateUI();
    }

    // Helper to update the navigation bar display
    private void updateUI() {
        if (navigationBar != null) {
            navigationBar.updateScore(currentScore);
            // TODO: Get total tasks from TaskManager to update progress bar correctly
            int totalTasks = 10; // Placeholder
            navigationBar.updateProgress(tasksCompletedInSession, totalTasks);
        }
    }
}