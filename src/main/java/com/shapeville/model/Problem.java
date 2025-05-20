package com.shapeville.model;

/**
 * A generic container for the data representing a single problem/question
 * presented to the user in a task. The specific content depends on the task type.
 */
public class Problem {
    private String taskId;      // Identifier for the task this problem belongs to
    private Object problemData; // Can hold a Shape, Angle, Map<String, Double> for dimensions, etc.
    private String questionText; // The textual question or instruction for this problem

    public Problem(String taskId, String questionText, Object problemData) {
        this.taskId = taskId;
        this.questionText = questionText;
        this.problemData = problemData;
    }

    public String getTaskId() { return taskId;}
    public String getQuestionText() { return questionText; }
    public Object getProblemData() { return problemData; }

    @SuppressWarnings("unchecked")
    public <T> T getProblemDataAs(Class<T> type) {
         if (problemData != null && type.isInstance(problemData)) {
             return (T) problemData;
         }
         return null;
    }
}