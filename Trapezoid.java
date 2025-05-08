package shapeville;


public record Trapezoid(int b1, int b2, int height) implements Shape2D {
    @Override public double area() { return 0.5 * (b1 + b2) * height; }
}


