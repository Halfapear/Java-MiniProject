package com.shapeville.model;

public class Angle {
    private double value; // in degrees
    private AngleType correctType;

    public Angle(double value) {
        this.value = value;
        this.correctType = determineType(value);
    }

    public double getValue() { return value; }
    public AngleType getCorrectType() { return correctType; }

    public static AngleType determineType(double angle) {
        // Normalize angle to be within 0-360 for some checks, though input is expected to be
        // double normalizedAngle = ((angle % 360) + 360) % 360; // Handles negative angles too

        if (angle == 0) return AngleType.ZERO;
        if (angle > 0 && angle < 90) return AngleType.ACUTE;
        if (angle == 90) return AngleType.RIGHT;
        if (angle > 90 && angle < 180) return AngleType.OBTUSE;
        if (angle == 180) return AngleType.STRAIGHT;
        if (angle > 180 && angle < 360) return AngleType.REFLEX;
        if (angle == 360) return AngleType.FULL_ROTATION;
        return AngleType.UNKNOWN; // For values outside 0-360 or other unhandled cases
    }

    @Override
    public String toString() {
        return "Angle{" +
               "value=" + value +
               ", correctType=" + correctType.getDisplayName() +
               '}';
    }
}
