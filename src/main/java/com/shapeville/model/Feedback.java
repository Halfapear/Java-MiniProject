package com.shapeville.model;

/**
 * Represents the feedback given to the user after they attempt an answer.
 */
public class Feedback {
    private final boolean correct;
    private final int pointsEarned;
    private final String message;
    private final String correctAnswerToDisplay; // The correct answer (if incorrect and should be shown)
    private final boolean proceedToNextProblem; // If true, current problem is done, move to next OR end task type
    private final boolean taskTypeComplete; // If true, this feedback concludes the entire task type (e.g., all 4 shapes done)

    public Feedback(boolean correct, int pointsEarned, String message, String correctAnswerToDisplay, boolean proceedToNextProblem, boolean taskTypeComplete) {
        this.correct = correct;
        this.pointsEarned = pointsEarned;
        this.message = message;
        this.correctAnswerToDisplay = correctAnswerToDisplay;
        this.proceedToNextProblem = proceedToNextProblem;
        this.taskTypeComplete = taskTypeComplete;
    }

     public boolean isCorrect() { return correct; }
     public int getPointsEarned() { return pointsEarned; }
     public String getMessage() { return message; }
     public String getCorrectAnswerToDisplay() { return correctAnswerToDisplay; }
     public boolean shouldProceedToNextProblem() { return proceedToNextProblem; }
     public boolean isTaskTypeComplete() { return taskTypeComplete; }
}
