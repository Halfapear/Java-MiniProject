package com.shapeville.ui.panel_templates;

import com.shapeville.logic.TaskLogic;
import com.shapeville.model.Problem;
import com.shapeville.model.Feedback;

/**
 * Interface defining the contract for all Task UI Panels.
 * Implementations of this interface will typically be JPanels.
 */
public interface TaskPanel {
    void displayProblem(Problem problem);
    void showFeedback(Feedback feedback);
    void setTaskLogicCallback(TaskLogic logic);
    String getPanelId(); // For CardLayout registration
    void resetState();   // To clear UI for next problem or when leaving panel
    // void onPanelShown(); // Optional: called when panel becomes visible
}
