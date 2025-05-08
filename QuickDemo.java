package shapeville;

import java.util.Scanner;

/**
 * QuickDemo —— CLI 测试 Task3 & Task4
 * ------------------------------------
 * • Task3：四种平面图形面积（R / P / T / Tr）
 * • Task4：圆的面积/周长  (A = area, C = circumference)
 * - 每题 3 分钟倒计时；超时或第三次错误自动结束并显示公式 + 鼓励语
 * - 全部练习完毕后自动退出
 */
public class QuickDemo {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        /* ========== Task 3 ========== */
        AreaTaskModule task3 = new AreaTaskModule();
        System.out.println("\n=== Task 3  平面图形面积练习 ===");
        while (!task3.isFinished()) {
            ShapeType shape = readShape();
            if (!task3.chooseShape(shape)) {
                System.out.println(">> 该形状已做过，请换一个！\n");
                continue;
            }
            runQuestion(() -> task3.prompt(),
                    ans -> task3.submit(ans),
                    () -> task3.solutionText());
        }
        System.out.println("🎉 Task 3 完成！\n");

        /* ========== Task 4 ========== */
        CircleTaskModule task4 = new CircleTaskModule();
        System.out.println("=== Task 4  圆的面积 / 周长练习 ===");
        while (!task4.isFinished()) {
            CircleTaskModule.CircleMode mode = readMode();
            if (!task4.chooseMode(mode)) {
                System.out.println(">> 该模式已完成两种参数，请换一个！\n");
                continue;
            }
            runQuestion(() -> task4.prompt(),
                    ans -> task4.submit(ans),
                    () -> task4.solutionText());
        }
        System.out.println("🎉 Task 4 完成！全部测试结束。");
        SC.close();
    }

    /* ---------- CLI 交互核心 ---------- */

    /** 通用做题循环：显示题干→提交答案→直到 solutionText() 非 null */
    private static void runQuestion(PromptSupplier prompt,
                                    SubmitChecker checker,
                                    SolutionSupplier sol) {

        System.out.println(prompt.get());
        while (sol.get() == null) {
            System.out.print("> ");
            double user;
            try { user = Double.parseDouble(SC.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("请输入数字！"); continue; }

            if (checker.check(user)) {
                System.out.println("√ 正确！\n");
            } else if (sol.get() == null) {
                System.out.println("× 错，再试一次！（或等待计时结束）");
            }
        }
        System.out.println("公式: " + sol.get() + "\n");
    }

    /* ---------- 辅助读取 ---------- */

    private static ShapeType readShape() {
        while (true) {
            System.out.print("选择形状 R/P/T/Tr (矩形/平行四边/三角/梯形): ");
            switch (SC.nextLine().trim().toUpperCase()) {
                case "R":  return ShapeType.RECTANGLE;
                case "P":  return ShapeType.PARALLELOGRAM;
                case "T":  return ShapeType.TRIANGLE;
                case "TR": return ShapeType.TRAPEZOID;
                default:   System.out.println("输入无效，请重新输入！");
            }
        }
    }

    private static CircleTaskModule.CircleMode readMode() {
        while (true) {
            System.out.print("选择模式 A(面积) / C(周长): ");
            String s = SC.nextLine().trim().toUpperCase();
            if ("A".equals(s)) return CircleTaskModule.CircleMode.AREA;
            if ("C".equals(s)) return CircleTaskModule.CircleMode.CIRCUMFERENCE;
            System.out.println("输入无效，请重新输入！");
        }
    }

    /* ---------- 函数式接口 ---------- */
    @FunctionalInterface private interface PromptSupplier   { String get(); }
    @FunctionalInterface private interface SubmitChecker    { boolean check(double ans); }
    @FunctionalInterface private interface SolutionSupplier { String  get(); }
}
