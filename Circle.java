package shapeville;

public record Circle(int radius) {
    public double area()          { return Math.PI * radius * radius; }
    public double circumference() { return 2 * Math.PI * radius; }
}