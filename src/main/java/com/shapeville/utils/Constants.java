package com.shapeville.utils;

public class Constants {
    // --- Panel Identifiers (for CardLayout and TaskDefinition.panelId) ---
    public static final String HOME_PANEL_ID = "HOME_PANEL";
    public static final String END_PANEL_ID = "END_PANEL";
    public static final String SHAPE_IDENTIFICATION_PANEL_ID = "SHAPE_ID_PANEL";
    public static final String ANGLE_TYPE_PANEL_ID = "ANGLE_TYPE_PANEL";
    // TODO: Add Panel IDs for Task 3, 4, Bonus 1, 2

    // --- Task Type Identifiers (for TaskDefinition.taskType and TaskManager logic mapping) ---
    public static final String TASK_TYPE_SHAPE_IDENTIFICATION_2D = "SHAPE_ID_2D";
    public static final String TASK_TYPE_SHAPE_IDENTIFICATION_3D = "SHAPE_ID_3D";
    public static final String TASK_TYPE_ANGLE_IDENTIFICATION = "ANGLE_ID";
    // TODO: Add Task Types for Task 3, 4, Bonus 1, 2

    // --- Task Identifiers (for TaskDefinition.taskId and HomeScreen buttons) ---
    // These are unique IDs for specific task instances/configurations that can be started.
    public static final String TASK_ID_SHAPE_ID_2D = "TASK_SHAPE_ID_2D"; // User selects 2D shapes
    public static final String TASK_ID_SHAPE_ID_3D = "TASK_SHAPE_ID_3D"; // User selects 3D shapes
    public static final String TASK_ID_ANGLE_TYPE = "TASK_ANGLE_TYPE";
    // TODO: Add Task IDs for Task 3, 4, Bonus 1, 2 (e.g., "TASK_AREA_RECT", "TASK_AREA_TRIANGLE")

    // Scoring levels
    public static final boolean SCORE_ADVANCED = true;
    public static final boolean SCORE_BASIC = false;

    // Default settings
    public static final int DEFAULT_MAX_ATTEMPTS = 3;
    public static final int SHAPES_PER_IDENTIFICATION_QUIZ = 4;
    public static final int ANGLES_PER_IDENTIFICATION_QUIZ = 4;
    // TODO: Add other constants (e.g., time limits in milliseconds)
}package com.shapeville.utils;

public class Constants {
    // --- Panel Identifiers (for CardLayout and TaskDefinition.panelId) ---
    public static final String HOME_PANEL_ID = "HOME_PANEL";
    public static final String END_PANEL_ID = "END_PANEL";
    public static final String SHAPE_IDENTIFICATION_PANEL_ID = "SHAPE_ID_PANEL";
    public static final String ANGLE_TYPE_PANEL_ID = "ANGLE_TYPE_PANEL";
    // TODO: Add Panel IDs for Task 3, 4, Bonus 1, 2

    // --- Task Type Identifiers (for TaskDefinition.taskType and TaskManager logic mapping) ---
    public static final String TASK_TYPE_SHAPE_IDENTIFICATION_2D = "SHAPE_ID_2D";
    public static final String TASK_TYPE_SHAPE_IDENTIFICATION_3D = "SHAPE_ID_3D";
    public static final String TASK_TYPE_ANGLE_IDENTIFICATION = "ANGLE_ID";
    // TODO: Add Task Types for Task 3, 4, Bonus 1, 2

    // --- Task Identifiers (for TaskDefinition.taskId and HomeScreen buttons) ---
    // These are unique IDs for specific task instances/configurations that can be started.
    public static final String TASK_ID_SHAPE_ID_2D = "TASK_SHAPE_ID_2D"; // User selects 2D shapes
    public static final String TASK_ID_SHAPE_ID_3D = "TASK_SHAPE_ID_3D"; // User selects 3D shapes
    public static final String TASK_ID_ANGLE_TYPE = "TASK_ANGLE_TYPE";
    // TODO: Add Task IDs for Task 3, 4, Bonus 1, 2 (e.g., "TASK_AREA_RECT", "TASK_AREA_TRIANGLE")

    // Scoring levels
    public static final boolean SCORE_ADVANCED = true;
    public static final boolean SCORE_BASIC = false;

    // Default settings
    public static final int DEFAULT_MAX_ATTEMPTS = 3;
    public static final int SHAPES_PER_IDENTIFICATION_QUIZ = 4;
    public static final int ANGLES_PER_IDENTIFICATION_QUIZ = 4;
    // TODO: Add other constants (e.g., time limits in milliseconds)
}