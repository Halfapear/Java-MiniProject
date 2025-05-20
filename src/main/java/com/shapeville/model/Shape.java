package com.shapeville.model;

public class Shape {
    private String name;
    private String imagePath; // Relative path from resources/assets, e.g., "/assets/shapes/circle.png"
    private String type;      // e.g., "2D_BASIC", "3D_ADVANCED", "CIRCLE", "RECTANGLE"

    public Shape(String name, String imagePath, String type) {
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return "Shape{" +
               "name='" + name + '\'' +
               ", imagePath='" + imagePath + '\'' +
               ", type='" + type + '\'' +
               '}';
    }
    // TODO: Consider adding equals() and hashCode() if shapes are stored in Sets or used as Map keys
}