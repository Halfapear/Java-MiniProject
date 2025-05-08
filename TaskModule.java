package shapeville;

/**
 * 所有任务模块对外暴露的统一接口。
 * GUI 侧只负责调用 3 个方法，不知道算法细节。
 */
public interface TaskModule {

    /** 产生下一道题；返回题干文字（便于直接放到 JLabel）。 */
    String next();

    /** 学生提交一个答案，返回本次是否正确。 */
    boolean submit(double userAnswer);

    /** 若已结束（超时或三次机会用完），返回正确值给 GUI 展示。 */
    double correctAnswer();
}