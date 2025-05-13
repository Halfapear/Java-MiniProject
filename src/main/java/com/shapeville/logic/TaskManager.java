package com.shapeville.logic;

import com.shapeville.main.MainFrame;
import com.shapeville.model.TaskDefinition;
import com.shapeville.tasks.ks1.angle.AngleTypeLogic; // Placeholder for actual imports
import com.shapeville.tasks.ks1.angle.AngleTypePanel;
import com.shapeville.tasks.ks1.identification.ShapeIdentificationLogic;
import com.shapeville.tasks.ks1.identification.ShapeIdentificationPanel;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private MainFrame mainFrameRef;
    private ScoreManager scoreManagerRef;
    private List<TaskDefinition> masterTaskList; // All possible tasks
    private TaskLogic currentActiveTaskLogic;
    private JPanel currentActiveTaskPanel;       // The UI panel (which should implement TaskPanel)
    private String currentPanelId; // ID of the currently displayed task panel

    // To manage the overall session progress (sequence of different task types)
    private List<String> sessionTaskSequenceIds; // e.g., [TASK_ID_SHAPE_ID_2D, TASK_ID_ANGLE_TYPE, ...]
    private int currentSessionTaskIndex;


    public TaskManager(MainFrame mainFrame, ScoreManager scoreManager) {
        this.mainFrameRef = mainFrame;
        this.scoreManagerRef = scoreManager;
        this.masterTaskList = new ArrayList<>();
        this.sessionTaskSequenceIds = new ArrayList<>();
        this.currentSessionTaskIndex = -1;
        defineMasterTasks();
        defineDefaultSessionSequence(); // Define the flow for a "full game"
    }

    private void defineMasterTasks() {
        // Define all tasks the application supports. HomeScreen buttons will refer to these Task IDs.
        masterTaskList.add(new TaskDefinition(Constants.TASK_ID_SHAPE_ID_2D, Constants.SHAPE_IDENTIFICATION_PANEL_ID, Constants.TASK_TYPE_SHAPE_IDENTIFICATION_2D, Constants.SCORE_BASIC));
        masterTaskList.add(new TaskDefinition(Constants.TASK_ID_SHAPE_ID_3D, Constants.SHAPE_IDENTIFICATION_PANEL_ID, Constants.TASK_TYPE_SHAPE_IDENTIFICATION_3D, Constants.SCORE_ADVANCED));
        masterTaskList.add(new TaskDefinition(Constants.TASK_ID_ANGLE_TYPE, Constants.ANGLE_TYPE_PANEL_ID, Constants.TASK_TYPE_ANGLE_IDENTIFICATION, Constants.SCORE_BASIC));
        // TODO: Add definitions for Task 3, 4, Bonus 1, 2
        // Example: masterTaskList.add(new TaskDefinition("TASK_AREA_RECT", "AreaCalcPanel_Rect", "AREA_CALC_RECT", Constants.SCORE_BASIC));
        System.out.println("Master task list defined with " + masterTaskList.size() + " task types.");
    }

    private void defineDefaultSessionSequence() {
        // Defines a typical flow if user plays through everything or a "Start Game" button
        // For now, let's just add a couple.
        sessionTaskSequenceIds.add(Constants.TASK_ID_SHAPE_ID_2D);
        sessionTaskSequenceIds.add(Constants.TASK_ID_ANGLE_TYPE);
        // TODO: Add other task IDs to define the full game sequence
        System.out.println("Default session sequence defined with " + sessionTaskSequenceIds.size() + " tasks.");
        // Update progress bar max based on this sequence
        if (mainFrameRef != null && mainFrameRef.getScoreManager() != null && masterTaskList.size() > 0) {
             // Bit of a circular dependency for nav bar update, but can be managed
             // Or pass total tasks to NavigationBar constructor or an update method
             mainFrameRef.getScoreManager().setNavigationBar(mainFrameRef.getNavigationBar()); // ensure nav is set
             mainFrameRef.getNavigationBar().updateProgress(0, sessionTaskSequenceIds.size());
        }
    }

    public void startFullSessionSequence() {
        System.out.println("Starting full session task sequence...");
        scoreManagerRef.resetSession();
        currentSessionTaskIndex = -1;
        loadNextTaskInSequence();
    }

    public void startSpecificTask(String taskId) {
        System.out.println("Attempting to start specific task: " + taskId);
        TaskDefinition taskDefToStart = findTaskDefinitionById(taskId);

        if (taskDefToStart != null) {
            // scoreManagerRef.resetSession(); // Decide if starting a specific task resets overall session score/progress
            loadTaskUIAndLogic(taskDefToStart);
        } else {
            System.err.println("Error: Task definition not found for Task ID: " + taskId);
            JOptionPane.showMessageDialog(mainFrameRef, "Error: Could not load task " + taskId, "Task Error", JOptionPane.ERROR_MESSAGE);
            mainFrameRef.navigateToHome();
        }
    }

    private TaskDefinition findTaskDefinitionById(String taskId) {
        for (TaskDefinition td : masterTaskList) {
            if (td.getTaskId().equals(taskId)) {
                return td;
            }
        }
        return null;
    }

    private void loadTaskUIAndLogic(TaskDefinition taskDef) {
        System.out.println("Loading UI and Logic for task: " + taskDef.getTaskId() + " - Type: " + taskDef.getTaskType());
        currentActiveTaskLogic = null;
        currentActiveTaskPanel = null; // This should be the JPanel that implements TaskPanel
        currentPanelId = taskDef.getPanelId();

        // Factory to create correct Logic and Panel
        try {
            TaskPanel taskPanelInstance = null; // The UI
            TaskLogic taskLogicInstance = null;   // The Logic

            switch (taskDef.getTaskType()) {
                case Constants.TASK_TYPE_SHAPE_IDENTIFICATION_2D:
                case Constants.TASK_TYPE_SHAPE_IDENTIFICATION_3D:
                    taskLogicInstance = new ShapeIdentificationLogic();
                    taskPanelInstance = new ShapeIdentificationPanel(mainFrameRef);
                    break;
                case Constants.TASK_TYPE_ANGLE_IDENTIFICATION:
                    taskLogicInstance = new AngleTypeLogic();       // TODO: KS1 Dev to implement AngleTypeLogic
                    taskPanelInstance = new AngleTypePanel(mainFrameRef); // TODO: KS1 Dev to implement AngleTypePanel
                    break;
                // TODO: Add cases for Task 3 (Area), Task 4 (Circle), Bonus 1 (Compound), Bonus 2 (Sector)
                // For each, instantiate their specific ...Logic and ...Panel classes.
                // Example:
                // case Constants.TASK_TYPE_AREA_CALC_RECT:
                //     taskLogicInstance = new AreaCalculationLogic();
                //     taskPanelInstance = new AreaCalculationPanel(mainFrameRef, "RECTANGLE"); // Panel might need type
                //     break;
                default:
                    System.err.println("Unknown task type for UI/Logic instantiation: " + taskDef.getTaskType());
                    JOptionPane.showMessageDialog(mainFrameRef, "Error: Task type '" + taskDef.getTaskType() + "' is not implemented.", "Implementation Error", JOptionPane.ERROR_MESSAGE);
                    mainFrameRef.navigateToHome();
                    return;
            }

            if (taskPanelInstance != null && taskLogicInstance != null) {
                currentActiveTaskLogic = taskLogicInstance;
                currentActiveTaskPanel = (JPanel) taskPanelInstance; // Cast to JPanel for CardLayout

                taskPanelInstance.setTaskLogicCallback(currentActiveTaskLogic); // Link UI to Logic
                currentActiveTaskLogic.initializeTask(taskDef, scoreManagerRef, this); // Initialize Logic

                mainFrameRef.registerPanel(currentPanelId, currentActiveTaskPanel);
                mainFrameRef.showPanel(currentPanelId);

                // Display the first problem
                taskPanelInstance.displayProblem(currentActiveTaskLogic.getCurrentProblem());
            } else {
                 System.err.println("Failed to instantiate Panel or Logic for: " + taskDef.getTaskType());
                 mainFrameRef.navigateToHome();
            }

        } catch (Exception e) {
            System.err.println("Exception while loading task " + taskDef.getTaskId() + ": " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrameRef, "Critical error loading task: " + taskDef.getTaskId(), "Error", JOptionPane.ERROR_MESSAGE);
            mainFrameRef.navigateToHome();
        }
    }

    /**
     * Called by a TaskLogic instance when it has completed all its internal problems
     * (e.g., identified 4 shapes, calculated 1 area).
     */
    public void currentTaskTypeCompleted(TaskLogic completedLogic) {
        System.out.println("Task type completed: " + completedLogic.getClass().getSimpleName());
        scoreManagerRef.incrementTaskTypeCompletedCount(); // Increment overall session progress

        // If we are in a sequence, load the next one.
        // If a specific task was started, this might mean returning to home or a summary.
        // For now, assume it means load next in sequence if sequence was started.
        if (currentSessionTaskIndex != -1 && currentSessionTaskIndex < sessionTaskSequenceIds.size()) {
             loadNextTaskInSequence();
        } else {
             // Specific task finished, or sequence ended unexpectedly
             System.out.println("Specific task type finished or sequence ended. Navigating home.");
             // Maybe show a "Task Set Complete!" dialog before going home?
             JOptionPane.showMessageDialog(mainFrameRef, "You've completed this task set!", "Task Set Complete", JOptionPane.INFORMATION_MESSAGE);
             mainFrameRef.navigateToHome();
        }
    }

    private void loadNextTaskInSequence() {
        currentSessionTaskIndex++;
        if (currentSessionTaskIndex < sessionTaskSequenceIds.size()) {
            String nextTaskId = sessionTaskSequenceIds.get(currentSessionTaskIndex);
            TaskDefinition nextTaskDef = findTaskDefinitionById(nextTaskId);
            if (nextTaskDef != null) {
                loadTaskUIAndLogic(nextTaskDef);
                // Update progress bar display via ScoreManager or direct call if MainFrame has method
                mainFrameRef.getNavigationBar().updateProgress(currentSessionTaskIndex + 1, sessionTaskSequenceIds.size());
            } else {
                System.err.println("Error: Next task in sequence not found: " + nextTaskId);
                mainFrameRef.navigateToHome(); // Or handle error
            }
        } else {
            System.out.println("Entire session task sequence completed!");
            mainFrameRef.endSession(); // All predefined tasks in sequence are done
        }
    }

    /**
     * Called by MainFrame when user navigates away from an active task (e.g., clicks Home).
     */
    public void currentTaskInterrupted() {
        System.out.println("Current task interrupted.");
        // TODO: Add any cleanup logic for the currentActiveTaskLogic if needed (e.g., stop timers)
        // if (currentActiveTaskLogic != null && currentActiveTaskLogic instanceof SomeTimerInterface) {
        //    ((SomeTimerInterface)currentActiveTaskLogic).stopTimer();
        // }
        currentActiveTaskLogic = null;
        currentActiveTaskPanel = null;
        currentPanelId = null;
        // Resetting currentSessionTaskIndex to -1 means if user clicks "Start Game" again, it starts from beginning.
        // If they click a specific task, that task starts. This seems reasonable.
        currentSessionTaskIndex = -1;
    }
}