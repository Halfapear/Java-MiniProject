package shapeville;

import java.util.*;

/**
 * Task 3 – 常见平面图形面积练习模块
 * <p>
 * ✅ 功能列表
 * <ul>
 *   <li>学生手动选择四种图形（矩形、平行四边、三角、梯形）；重复选择会被拒绝。</li>
 *   <li>每题随机 1–20 的整数参数，计时 3 分钟。</li>
 *   <li>每题 3 次机会；超时直接计为一次错误并结束该题。</li>
 *   <li>答对或第三次错误/超时，输出公式 + 数值；若失败附加鼓励语。</li>
 *   <li>四形全部练习完后 {@link #isFinished()} 返回 true，可触发“恭喜完成 Task 3”。</li>
 * </ul>
 */
public final class AreaTaskModule {

    /* ───── 常量 & 字段 ───── */
    private static final long DURATION_MS = 180_000;              // 3 min
    private final Set<ShapeType> remaining = EnumSet.allOf(ShapeType.class);

    private Question  current;                                    // 当前题
    private ShapeType currentShape;
    private int       triesLeft;
    private long      startMs;                                    // 题目开始时间戳
    private String    solutionText;                               // 公式 + 数值 +（可选）鼓励

    /* ───── 公开接口 ───── */

    /**
     * 学生选择下一道题的形状。
     *
     * @param shape 选定图形
     * @return true = 选择成功；false = 此形状已练过
     */
    public boolean chooseShape(ShapeType shape) {
        if (!remaining.contains(shape)) return false;             // 已做过
        currentShape = shape;
        current      = QuestionFactory.nextAreaQuestion(shape);
        triesLeft    = 3;
        startMs      = System.currentTimeMillis();                // 计时开始
        solutionText = null;
        remaining.remove(shape);
        return true;
    }

    /** 当前题干文本，用于放到 JLabel/CLI。 */
    public String prompt() { return current.prompt(); }

    /**
     * 学生提交一次答案。
     *
     * @return true = 正确；false = 错误/超时
     */
    public boolean submit(double userAns) {
        if (triesLeft == 0) return false;                         // 已结束

        /* 超时视为一次错误并直接结束 */
        if (isTimeUp()) {
            triesLeft = 0;
            buildSolution(true);                                  // 带鼓励语
            return false;
        }

        boolean ok = AnswerChecker.isCorrect(userAns, current.answer());
        if (ok) {
            buildSolution(false);                                 // 正确无鼓励语
            triesLeft = 0;
            return true;
        }

        if (--triesLeft == 0) buildSolution(true);               // 第三次错 → 公式+鼓励
        return false;
    }

    /** 公式 + 数值文本；若未结束返回 null。 */
    public String solutionText() { return solutionText; }

    /** 四种图形是否全部完成？ */
    public boolean isFinished() { return remaining.isEmpty(); }

    /* ───── 内部工具 ───── */

    /** 是否已到 180 s 限时 */
    private boolean isTimeUp() {
        return System.currentTimeMillis() - startMs >= DURATION_MS;
    }

    /** 生成并缓存公式文本；includeEnc = true 时附加鼓励语 */
    private void buildSolution(boolean includeEnc) {
        Map<String,Integer> p = current.params();
        solutionText = switch (currentShape) {
            case RECTANGLE -> String.format("面积 = 长×宽 = %d × %d = %.1f",
                    p.get("w"), p.get("h"), current.answer());
            case PARALLELOGRAM -> String.format("面积 = 底×高 = %d × %d = %.1f",
                    p.get("b"), p.get("h"), current.answer());
            case TRIANGLE -> String.format("面积 = ½×底×高 = 0.5 × %d × %d = %.1f",
                    p.get("b"), p.get("h"), current.answer());
            case TRAPEZOID -> String.format("面积 = ½×(上底+下底)×高 = 0.5 × (%d+%d) × %d = %.1f",
                    p.get("b1"), p.get("b2"), p.get("h"), current.answer());
        };
        if (includeEnc) {
            solutionText += "。再接再厉，虽然这次做错了，但是看了公式下次就会了";
        }
    }
}
