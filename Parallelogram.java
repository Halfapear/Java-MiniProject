package shapeville;


public record Parallelogram(int base, int height) implements Shape2D {
    @Override public double area() { return base * height; }
}


