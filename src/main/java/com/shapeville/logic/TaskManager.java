package com.shapeville.logic;

import com.shapeville.main.MainFrame;
import com.shapeville.model.TaskDefinition;
import com.shapeville.tasks.ks1.identification.ShapeIdentificationLogic;
import com.shapeville.tasks.ks1.identification.ShapeIdentificationPanel;
import com.shapeville.ui.panel_templates.TaskPanel; // Assuming TaskPanel is an interface

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the overall flow and sequence of tasks in the application.
 * Decides which task to load next and interacts with MainFrame to display the correct panel.
 */
public class TaskManager {

    private MainFrame mainFrameRef;
    private ScoreManager scoreManagerRef;
    private List<TaskDefinition> taskSequence;
    private int currentTaskIndex;
    private TaskLogic currentActiveTaskLogic; // The logic controller for the currently active task
    private JPanel currentActiveTaskPanel;   // The UI panel for the currently active task

    public TaskManager(MainFrame mainFrame, ScoreManager scoreManager) {
        this.mainFrameRef = mainFrame;
        this.scoreManagerRef = scoreManager;
        this.taskSequence = new ArrayList<>();
        this.currentTaskIndex = -1;
    }

    /**
     * Defines the sequence of tasks for the session.
     * This could be loaded from a config file or database in a real app.
     */
    public void setTaskSequence() {
        taskSequence.clear();
        // TODO: Populate this list with TaskDefinition objects in the desired order
        // Example TaskDefinitions (use constants for IDs)
        taskSequence.add(new TaskDefinition("T1_SHAPE_ID_2D", "ShapeIdentificationPanel", "SHAPE_IDENTIFICATION_2D"));
        taskSequence.add(new TaskDefinition("T2_ANGLE_TYPE", "AngleTypePanel", "ANGLE_TYPE_IDENTIFICATION"));
        // Add Task 3, Task 4, Bonus 1, Bonus 2 definitions...

        System.out.println("Task sequence set with " + taskSequence.size() + " tasks.");
    }


    /**
     * Starts the task sequence from the beginning. Called from HomeScreen, for example.
     */
    public void startTaskSequence() {
         System.out.println("Starting task sequence...");
         scoreManagerRef.resetSession(); // Reset score/progress for new sequence
         currentTaskIndex = -1; // Reset index
         loadNextTask();
    }

    /**
    * Starts a specific task type directly, potentially outside the main sequence.
    * Useful for letting the user choose a specific task from the HomeScreen.
    * @param taskId The unique ID of the task to start.
    */
    public void startSpecificTask(String taskId) {
        System.out.println("Starting specific task: " + taskId);
        // Find the task definition
        TaskDefinition taskToStart = null;
        for (TaskDefinition td : taskSequence) { // Assuming all possible tasks are in the sequence list
            if (td.getTaskId().equals(taskId)) {
                taskToStart = td;
                break;
            }
        }

        if (taskToStart != null) {
            // scoreManagerRef.resetSession(); // Decide if starting specific task resets score
            loadTask(taskToStart);
        } else {
            System.err.println("Error: Task definition not found for ID: " + taskId);
            // Maybe show an error message to the user
             JOptionPane.showMessageDialog(mainFrameRef, "Error: Could not load task " + taskId, "Error", JOptionPane.ERROR_MESSAGE);
             mainFrameRef.navigateToHome(); // Go back home if task fails to load
        }
    }

    /**
     * Loads and displays the next task in the sequence.
     */
    private void loadNextTask() {
        currentTaskIndex++;
        if (currentTaskIndex < taskSequence.size()) {
            TaskDefinition nextTaskDef = taskSequence.get(currentTaskIndex);
            loadTask(nextTaskDef);
        } else {
            System.out.println("Task sequence completed.");
            // All tasks in the sequence are done
            // TODO: Maybe show a summary screen before the end panel?
            mainFrameRef.showPanel(MainFrame.END_PANEL_ID); // Or navigate to a summary panel first
             // Call endSession which shows final score and prompts exit
             mainFrameRef.endSession();
        }
    }

    /**
     * Instantiates and sets up the logic and UI panel for a given task definition.
     * Registers the panel with MainFrame and tells MainFrame to display it.
     *
     * @param taskDef The definition of the task to load.
     */
    private void loadTask(TaskDefinition taskDef) {
         System.out.println("Loading task: " + taskDef.getTaskId() + " - Type: " + taskDef.getTaskType());
         currentActiveTaskLogic = null; // Reset previous logic
         currentActiveTaskPanel = null; // Reset previous panel

         // --- Factory or Switch to create correct Logic and Panel based on taskDef ---
         // This part needs to map Task Types/Panel IDs to actual class instantiations.
         // This is where new tasks modules are integrated.
         try {
             switch (taskDef.getTaskType()) {
                 case "SHAPE_IDENTIFICATION_2D":
                 case "SHAPE_IDENTIFICATION_3D": // Logic might handle both? Or separate task types?
                     ShapeIdentificationLogic shapeLogic = new ShapeIdentificationLogic();
                     shapeLogic.initializeTask(taskDef, scoreManagerRef, this); // Pass dependencies
                     ShapeIdentificationPanel shapePanel = new ShapeIdentificationPanel(mainFrameRef); // Pass mainframe ref if needed by UI directly
                     shapePanel.setTaskLogicCallback(shapeLogic); // Link Panel UI to Logic
                     currentActiveTaskLogic = shapeLogic;
                     currentActiveTaskPanel = shapePanel;
                     break;

                 case "ANGLE_TYPE_IDENTIFICATION":
                      // TODO: Instantiate AngleTypeLogic and AngleTypePanel
                      // AngleTypeLogic angleLogic = new AngleTypeLogic();
                      // angleLogic.initializeTask(taskDef, scoreManagerRef, this);
                      // AngleTypePanel anglePanel = new AngleTypePanel(mainFrameRef);
                      // anglePanel.setTaskLogicCallback(angleLogic);
                      // currentActiveTaskLogic = angleLogic;
                      // currentActiveTaskPanel = anglePanel;
                      System.err.println("Task type not implemented yet: " + taskDef.getTaskType());
                      // Placeholder panel
                      currentActiveTaskPanel = new JPanel();
                      ((JPanel)currentActiveTaskPanel).add(new JLabel("Task Panel for: " + taskDef.getTaskType() + " - Not Implemented"));
                      break;

                 // TODO: Add cases for Task 3, Task 4, Bonus 1, Bonus 2
                 // case "AREA_CALCULATION_RECTANGLE": ...

                 default:
                     System.err.println("Unknown task type: " + taskDef.getTaskType());
                     // Create a placeholder panel indicating error
                      currentActiveTaskPanel = new JPanel();
                      ((JPanel)currentActiveTaskPanel).add(new JLabel("Error: Unknown Task Type - " + taskDef.getTaskType()));
                     break;
             }

             if (currentActiveTaskPanel != null) {
                  mainFrameRef.registerPanel(taskDef.getPanelId(), currentActiveTaskPanel); // Ensure panel is registered
                  mainFrameRef.showPanel(taskDef.getPanelId()); // Show the panel
                  // Optionally call a method on the panel/logic to start the first question
                  if (currentActiveTaskLogic != null) {
                       // Tell the panel to display the first problem from the logic
                       if (currentActiveTaskPanel instanceof TaskPanel) {
                            ((TaskPanel)currentActiveTaskPanel).displayProblem(currentActiveTaskLogic.getCurrentProblem());
                       }
                  }
             }

         } catch (Exception e) {
              System.err.println("Error loading task " + taskDef.getTaskId() + ": " + e.getMessage());
              e.printStackTrace();
              // Show error message and maybe return home
              JOptionPane.showMessageDialog(mainFrameRef, "Error loading task: " + taskDef.getTaskId() + "\n" + e.getMessage(), "Loading Error", JOptionPane.ERROR_MESSAGE);
              mainFrameRef.navigateToHome();
         }
    }

    /**
     * Callback method for TaskLogic instances to call when their specific task
     * (e.g., identifying 4 shapes, calculating 1 area) is fully completed.
     *
     * @param logic The TaskLogic instance that just finished.
     */
    public void onTaskCompleted(TaskLogic logic) {
        System.out.println("Task logic completed: " + logic.getClass().getSimpleName());
        // Current task type (like identify 4 shapes) is done. Load the next task in the sequence.
        scoreManagerRef.incrementTasksCompleted(); // Increment overall session progress
        loadNextTask();
    }

    /**
     * Resets the state when the user navigates away prematurely (e.g., hits Home).
     */
     public void resetCurrentTask() {
         // This might involve telling the currentActiveTaskLogic to stop timers, reset its state, etc.
         if (currentActiveTaskLogic != null) {
              // currentActiveTaskLogic.cancelTask(); // Add such method if needed
         }
         currentActiveTaskLogic = null;
         currentActiveTaskPanel = null;
         // Reset index only if restarting the whole sequence
         // currentTaskIndex = -1; // Depends on desired behavior when hitting Home
         System.out.println("Current task reset due to navigation.");
     }

     // TODO: Add method to get total number of tasks for progress bar
     public int getTotalTasksInSequence() {
         return taskSequence.size();
     }
}