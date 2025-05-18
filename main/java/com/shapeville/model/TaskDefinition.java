package com.shapeville.model;

/**
 * Defines metadata for a specific task within the application sequence.
 */
public class TaskDefinition {
    private final String taskId;          // Unique identifier for the task (e.g., Constants.TASK_ID_SHAPE_ID_2D)
    private final String panelId;         // Identifier used by CardLayout in MainFrame (e.g., Constants.SHAPE_ID_PANEL_ID)
    private final String taskType;        // Type identifier used by TaskManager to load correct logic (e.g., Constants.TASK_TYPE_SHAPE_ID_2D)
    private final boolean isAdvancedScoring; // Does this task use advanced scoring table?

    public TaskDefinition(String taskId, String panelId, String taskType, boolean isAdvancedScoring) {
        this.taskId = taskId;
        this.panelId = panelId;
        this.taskType = taskType;
        this.isAdvancedScoring = isAdvancedScoring;
    }

     public String getTaskId() { return taskId; }
     public String getPanelId() { return panelId; }
     public String getTaskType() { return taskType; }
     public boolean isAdvancedScoring() { return isAdvancedScoring; }
}