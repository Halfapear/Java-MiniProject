package com.shapeville.logic;

import javax.swing.JOptionPane;

import com.shapeville.ui.NavigationBar; // For "Great Job!"

public class ScoreManager {
    private int currentScore = 0;
    private int tasksOfTypeCompletedInSession; // Renamed for clarity
    private NavigationBar navigationBar;

    public ScoreManager() {
        resetSession();
    }

    public void setNavigationBar(NavigationBar navigationBar) {
        this.navigationBar = navigationBar;
        updateUI();
    }

    public int calculatePoints(int attemptsUsed, boolean isAdvancedTask) {
        int points = 0;
        if (attemptsUsed < 1 || attemptsUsed > 3) return 0; // Invalid attempts

        if (isAdvancedTask) { // Advanced Scoring from project spec Table 1
            if (attemptsUsed == 1) points = 6;
            else if (attemptsUsed == 2) points = 4;
            else if (attemptsUsed == 3) points = 2;
        } else { // Basic Scoring
            if (attemptsUsed == 1) points = 3;
            else if (attemptsUsed == 2) points = 2;
            else if (attemptsUsed == 3) points = 1;
        }
        return points;
    }

    public void recordScoreAndFeedback(int pointsToAdd) {
        if (pointsToAdd > 0) {
            this.currentScore += pointsToAdd;
            // Display "Great job!" message as per project spec
            JOptionPane.showMessageDialog(null, // Parent component (can be MainFrame if passed)
                    "Great job! You earned " + pointsToAdd + " points!",
                    "Feedback",
                    JOptionPane.INFORMATION_MESSAGE);
            updateUI();
        }
    }

    public void incrementTaskTypeCompletedCount() {
        this.tasksOfTypeCompletedInSession++;
        updateUI();
    }

    public int getCurrentScore() { return currentScore; }
    //public int getCurrentScore() { return 2; }
    public int getTasksOfTypeCompletedCount() { return tasksOfTypeCompletedInSession; }

    public void resetSession() {
        this.currentScore = 0;
        this.tasksOfTypeCompletedInSession = 0;
        if (navigationBar != null) { // Ensure nav bar exists before updating
            updateUI();
        }
    }

    private void updateUI() {
        if (navigationBar != null) {
            navigationBar.updateScore();
            // Progress bar update needs total number of tasks in the *entire session flow*
            // This might be managed by TaskManager
            // For now, let's assume TaskManager informs NavigationBar directly or via MainFrame
        }
    }

}