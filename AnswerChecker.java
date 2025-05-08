package shapeville;

import java.util.Map;

/** 用户答案校验工具 */
public final class AnswerChecker {
    private AnswerChecker() {}

    public static boolean isCorrect(double user, double truth) {
        return Math.abs(user - truth) < Consts.EPS;
    }
}
