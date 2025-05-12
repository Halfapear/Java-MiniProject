package shapeville.tasks.task2;

import javafx.scene.Scene;
import javafx.stage.Stage;
import shapeville.app.ScoreManager;

public class AngleIdentification {
    private final Stage primaryStage;
    private final ScoreManager scoreManager;
    private final AngleVisualizer visualizer;
    
    public AngleIdentification(Stage primaryStage, ScoreManager scoreManager) {
        this.primaryStage = primaryStage;
        this.scoreManager = scoreManager;
        this.visualizer = new AngleVisualizer();
    }
    
    public void start() {
        // Setup UI for angle identification
        showNextAngle();
    }
    
    private void showNextAngle() {
        // Generate a random angle (multiple of 10 between 0-360)
        int angle = (int) (Math.random() * 36) * 10;
        
        // Display the angle visually
        visualizer.displayAngle(angle);
        
        // Prompt user to identify angle type
    }
    
    private void handleUserAnswer(String userAnswer, AngleType correctType) {
        // Validate answer
        // Update score based on attempts
        // Provide feedback
    }
}