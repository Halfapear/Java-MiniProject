package com.shapeville.model;

public enum AngleType {
    ACUTE("Acute Angle"),
    RIGHT("Right Angle"),
    OBTUSE("Obtuse Angle"),
    STRAIGHT("Straight Angle"),
    REFLEX("Reflex Angle"),
    FULL_ROTATION("Full Rotation (360°)"), // Added for clarity if needed
    ZERO("Zero Angle (0°)"),             // Added for clarity if needed
    UNKNOWN("Unknown");

    private final String displayName;

    AngleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
