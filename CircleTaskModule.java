package shapeville;

import java.security.SecureRandom;
import java.util.*;

/**
 * Task 4 – 圆的面积 / 周长练习模块
 * <p>
 * ✅ 功能概览
 * <ul>
 *   <li>学生先选择模式：{@code AREA}（面积）或 {@code CIRCUMFERENCE}（周长）。</li>
 *   <li>系统需要在 “半径 r” 与 “直径 d” 两种参数之间分配题目，确保四种组合
 *       {@code (A|C) × (r|d)} 均被练习且不重复。</li>
 *   <li>每题随机整数 1–20；限时 3 min；3 次机会；超时等同一次错误。</li>
 *   <li>答对或第三次错误/超时，输出公式 + 数值；若失败附加鼓励语。</li>
 *   <li>四组合全部完成后 {@link #isFinished()} 返回 {@code true}，便于 GUI 弹窗。</li>
 * </ul>
 */
public final class CircleTaskModule {

    /* ───── 枚举 ───── */
    public enum CircleMode  { AREA, CIRCUMFERENCE }
    private enum ParamType { RADIUS, DIAMETER }

    /* ───── 常量与字段 ───── */
    private static final long DURATION_MS = 180_000;              // 3 min
    private final Map<CircleMode, EnumSet<ParamType>> remaining = new EnumMap<>(CircleMode.class);
    private final SecureRandom rnd = new SecureRandom();

    private CircleMode currentMode;
    private ParamType  currentParam;
    private Question   current;
    private int        triesLeft;
    private long       startMs;
    private String     solutionText;

    /* ───── 构造 ───── */
    public CircleTaskModule() {
        remaining.put(CircleMode.AREA,          EnumSet.allOf(ParamType.class));
        remaining.put(CircleMode.CIRCUMFERENCE, EnumSet.allOf(ParamType.class));
    }

    /* ───── 公开接口 ───── */

    /**
     * 学生选择模式（面积或周长）。
     *
     * @return {@code true} = 进入新题；{@code false} = 此模式两种参数已作完
     */
    public boolean chooseMode(CircleMode mode) {
        EnumSet<ParamType> left = remaining.get(mode);
        if (left.isEmpty()) return false;                       // 已完成此模式

        currentMode  = mode;
        currentParam = (left.size() == 1)
                ? left.iterator().next()                  // 仅剩一种参数
                : (rnd.nextBoolean() ? ParamType.RADIUS
                : ParamType.DIAMETER);

        left.remove(currentParam);                             // 标记已做
        current     = createQuestion(currentMode, currentParam);
        triesLeft   = 3;
        startMs     = System.currentTimeMillis();              // 计时
        solutionText = null;
        return true;
    }

    /** 当前题干文本（含随机参数）。 */
    public String prompt() { return current.prompt(); }

    /**
     * 提交答案。
     *
     * @return {@code true} = 正确；{@code false} = 错误或超时
     */
    public boolean submit(double userAns) {
        if (triesLeft == 0) return false;

        if (isTimeUp()) {                                      // 超时
            triesLeft = 0;
            buildSolution(true);
            return false;
        }

        boolean ok = AnswerChecker.isCorrect(userAns, current.answer());
        if (ok) {
            buildSolution(false);
            triesLeft = 0;
            return true;
        }

        if (--triesLeft == 0) buildSolution(true);             // 第三次错
        return false;
    }

    /** 公式 + 数值文本；未结束时返回 {@code null}。 */
    public String solutionText() { return solutionText; }

    /** 四种组合是否全部完成？ */
    public boolean isFinished() {
        return remaining.values().stream().allMatch(Set::isEmpty);
    }

    /* ───── 内部工具 ───── */

    private boolean isTimeUp() {
        return System.currentTimeMillis() - startMs >= DURATION_MS;
    }

    /** 创建题目对象 */
    private Question createQuestion(CircleMode mode, ParamType param) {
        int v = rnd.nextInt(Consts.MAX - Consts.MIN + 1) + Consts.MIN; // 1–20
        String prompt;     double ans;     Map<String,Integer> params;

        if (param == ParamType.RADIUS) {
            prompt = (mode == CircleMode.AREA ? "求面积" : "求周长") + "：圆，半径 " + v;
            params = Map.of("r", v);
            ans    = (mode == CircleMode.AREA)
                    ? Math.PI * v * v
                    : 2 * Math.PI * v;
        } else {                                                          // 直径
            prompt = (mode == CircleMode.AREA ? "求面积" : "求周长") + "：圆，直径 " + v;
            params = Map.of("d", v);
            ans    = (mode == CircleMode.AREA)
                    ? Math.PI * Math.pow(v / 2.0, 2)
                    : Math.PI * v;
        }
        return new Question(prompt, ans, params);
    }

    /** 生成公式文本（含可选鼓励语） */
    private void buildSolution(boolean includeEnc) {
        solutionText = switch (currentParam) {
            case RADIUS -> (currentMode == CircleMode.AREA)
                    ? String.format("面积 = πr² = %.2f × %d² = %.2f",
                    Math.PI, current.params().get("r"), current.answer())
                    : String.format("周长 = 2πr = 2 × %.2f × %d = %.2f",
                    Math.PI, current.params().get("r"), current.answer());
            case DIAMETER -> (currentMode == CircleMode.AREA)
                    ? String.format("面积 = π(d/2)² = %.2f × (%.1f)² = %.2f",
                    Math.PI, current.params().get("d") / 2.0, current.answer())
                    : String.format("周长 = πd = %.2f × %d = %.2f",
                    Math.PI, current.params().get("d"), current.answer());
        };
        if (includeEnc) {
            solutionText += "。再接再厉，虽然这次做错了，但是看了公式下次就会了";
        }
    }
}
