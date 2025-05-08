package shapeville;

import java.util.Map;

/** 统一的题目载体（可扩展） */
public record Question(String prompt,           // 用户要看到的题干
                       double answer,           // 正确结果
                       Map<String, Integer> params) { } // 随机参数（可给 GUI 标注）
