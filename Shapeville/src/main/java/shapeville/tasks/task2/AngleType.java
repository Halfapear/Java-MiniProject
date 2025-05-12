package shapeville.tasks.task2;

public enum AngleType {
    ACUTE("Acute angle", "less than 90° and greater than 0°"),
    RIGHT("Right angle", "equal to 90°"),
    OBTUSE("Obtuse angle", "less than 180° and greater than 90°"),
    STRAIGHT("Straight angle", "equal to 180°"),
    REFLEX("Reflex angle", "greater than 180° and less than 360°");
    
    private final String name;
    private final String description;
    
    AngleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public static AngleType fromDegrees(int degrees) {
        degrees = degrees % 360;
        
        if (degrees == 0) return null; // Not technically an angle
        if (degrees == 90) return RIGHT;
        if (degrees == 180) return STRAIGHT;
        if (degrees < 90) return ACUTE;
        if (degrees < 180) return OBTUSE;
        return REFLEX;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
}