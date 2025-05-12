package shapeville.app;

public class ScoreManager {
    private int totalScore = 0;
    
    public void addPoints(int attempts, boolean isAdvanced) {
        int points = 0;
        
        if (isAdvanced) {
            points = switch (attempts) {
                case 1 -> 6;
                case 2 -> 4;
                case 3 -> 2;
                default -> 0;
            };
        } else {
            points = switch (attempts) {
                case 1 -> 3;
                case 2 -> 2;
                case 3 -> 1;
                default -> 0;
            };
        }
        
        totalScore += points;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public String getScoreMessage() {
        return String.format("You have achieved %d points in this session. Goodbye!", totalScore);
    }
}