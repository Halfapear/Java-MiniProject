package shapeville;

public record Rectangle(int w, int h) implements Shape2D {
    @Override public double area() { return w * h; }
}
