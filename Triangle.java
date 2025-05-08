package shapeville;


public record Triangle(int base, int height) implements Shape2D {
    @Override public double area() { return 0.5 * base * height; }
}


