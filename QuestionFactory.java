package shapeville;

import java.security.SecureRandom;
import java.util.Map;
import static shapeville.Consts.*;

public final class QuestionFactory {
    private static final SecureRandom RND = new SecureRandom();
    private QuestionFactory() {}

    //——— 5.1 Task 3 题目 —————————————————————
    public static Question nextAreaQuestion(ShapeType type) {
        return switch (type) {
            case RECTANGLE -> {
                int w = rnd(), h = rnd();
                yield new Question(
                    "求面积：矩形，宽 " + w + "，高 " + h,
                    new Rectangle(w, h).area(),
                    Map.of("w", w, "h", h));
            }
            case PARALLELOGRAM -> {
                int b = rnd(), h = rnd();
                yield new Question(
                    "求面积：平行四边形，底 " + b + "，高 " + h,
                    new Parallelogram(b, h).area(),
                    Map.of("b", b, "h", h));
            }
            case TRIANGLE -> {
                int b = rnd(), h = rnd();
                yield new Question(
                    "求面积：三角形，底 " + b + "，高 " + h,
                    new Triangle(b, h).area(),
                    Map.of("b", b, "h", h));
            }
            case TRAPEZOID -> {
                int b1 = rnd(), b2;
                do { b2 = rnd(); } while (b2 == b1);       // 避免等腰?
                int h = rnd();
                yield new Question(
                    "求面积：梯形，上底 " + b1 + "，下底 " + b2 + "，高 " + h,
                    new Trapezoid(b1, b2, h).area(),
                    Map.of("b1", b1, "b2", b2, "h", h));
            }
        };
    }

    //——— 5.2 Task 4 题目 —————————————————————
    public static Question nextCircleQuestion(boolean needArea) {
        int r = rnd();
        if (needArea) {
            return new Question(
                "求面积：圆，半径 " + r,
                new Circle(r).area(),
                Map.of("r", r));
        } else {
            return new Question(
                "求周长：圆，半径 " + r,
                new Circle(r).circumference(),
                Map.of("r", r));
        }
    }

    //——— 工具方法 —————————————
    private static int rnd() { return RND.nextInt(MAX - MIN + 1) + MIN; }
}
