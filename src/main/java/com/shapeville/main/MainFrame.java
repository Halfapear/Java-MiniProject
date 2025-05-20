package com.shapeville.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane; // Assuming TaskPanel is an interface or class Panels implement/extend
import javax.swing.JPanel;

import com.shapeville.logic.ScoreManager;
import com.shapeville.logic.TaskManager;
import com.shapeville.ui.NavigationBar;
import com.shapeville.ui.panel_templates.EndPanel;
import com.shapeville.ui.panel_templates.HomeScreenPanel;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;

/**
 * The main window (JFrame) of the application.
 * Holds the navigation bar, the main content area (using CardLayout),
 * and references to core managers (TaskManager, ScoreManager).
 * Handles panel switching and overall application flow control initiated by TaskManager.
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPaneContainer; // Panel holding all the switchable panels (cards)
    private NavigationBar navigationBar;
    private ScoreManager scoreManager;
    private TaskManager taskManager;

    // Store registered panels for easy access if needed, managed by CardLayout
    private Map<String, JPanel> registeredPanels;

    // Panel Identifiers (Constants are better defined in a dedicated Constants class)
    public static final String HOME_PANEL_ID = "HOME";
    public static final String END_PANEL_ID = "END";
    // Add IDs for all task panels, e.g., public static final String SHAPE_ID_PANEL_ID = "SHAPE_ID";


    public MainFrame() {
        registeredPanels = new HashMap<>();
        initializeCoreManagers();
        initializeUI();
        registerCorePanels(); // Register home and end panels
        taskManager.setTaskSequence(); // Define the order of tasks
        showPanel(HOME_PANEL_ID); // Show home screen initially
    }

    private void initializeCoreManagers() {
        // Important: Handle dependencies. ScoreManager might need NavigationBar later.
        // TaskManager needs MainFrame to switch panels.
        scoreManager = new ScoreManager();
        taskManager = new TaskManager(this, scoreManager); // Pass dependencies
    }

    private void initializeUI() {
        setTitle("Shapeville Learning");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650); // Slightly larger height for nav bar + content
        setLocationRelativeTo(null); // Center window
        setResizable(false);

        // Create Navigation Bar (pass reference to this MainFrame for callbacks)
        navigationBar = new NavigationBar(this);
        add(navigationBar, BorderLayout.NORTH);
        scoreManager.setNavigationBar(navigationBar); // Link score manager to UI display

        // Create Content Pane Container with CardLayout
        cardLayout = new CardLayout();
        contentPaneContainer = new JPanel(cardLayout);

        // Add components to the JFrame
        add(navigationBar, BorderLayout.NORTH);
        add(contentPaneContainer, BorderLayout.CENTER);
    }

    private void registerCorePanels() {
        registerPanel(Constants.HOME_PANEL_ID, new HomeScreenPanel(this));
        registerPanel(Constants.END_PANEL_ID, new EndPanel());
        // 任务面板将在 TaskManager 中动态注册
    }

    /**
     * Registers a panel (like a task screen or home screen) with the CardLayout.
     *
     * @param panelId A unique String identifier for the panel.
     * @param panel   The JPanel instance to register.
     */
    public void registerPanel(String panelId, JPanel panel) {
        contentPaneContainer.add(panel, panelId);
        registeredPanels.put(panelId, panel);
        System.out.println("Registered panel: " + panelId); // For debugging
    }

    /**
     * Switches the currently displayed panel in the CardLayout.
     * Also notifies the panel that it's being shown (if it implements TaskPanel interface).
     *
     * @param panelId The unique String identifier of the panel to show.
     */
    public void showPanel(String panelId) {
        System.out.println("Switching to panel: " + panelId); // For debugging
        cardLayout.show(contentPaneContainer, panelId);

        // Optional: Notify the panel it's visible (useful for starting tasks)
         JPanel panelToShow = registeredPanels.get(panelId);
         if (panelToShow instanceof TaskPanel) {
             // ((TaskPanel) panelToShow).onPanelShown(); // If TaskPanel interface has this method
             // The actual task starting logic might be better handled by TaskManager calling
             // the panel's specific startTask method after showing it.
         }
    }

    // --- Navigation Methods (Called by NavigationBar or TaskManager) ---

    public void navigateToHome() {
        // TODO: Add logic if leaving a task needs confirmation or state reset
        showPanel(HOME_PANEL_ID);
        taskManager.resetCurrentTask(); // Tell TaskManager the flow is interrupted
    }

    public void endSession() {
        // TODO: Maybe show final score before ending?
        showPanel(END_PANEL_ID);
        // Optional: Disable navigation buttons on EndPanel?
        // Using JOptionPane to actually close after showing EndPanel might be better UX
         int choice = JOptionPane.showConfirmDialog(this,
                "Session ended. Your final score: " + scoreManager.getCurrentScore() + "\nClose application?",
                "End Session",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
         if (choice == JOptionPane.OK_OPTION) {
             System.exit(0);
         } else {
             // If cancelled, maybe go back home?
             navigateToHome();
         }
    }

    // --- Getters for Managers (Needed by Panels/Logic) ---

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

   
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }
    
    /**
     * 设置内容面板
     * 
     * @param panel 要设置的面板
     */
    public void setContentPanel(JPanel panel) {
        // 这里不需要做额外操作，因为registerPanel和showPanel已经处理了面板的添加和显示
        // 这个方法只是为了兼容TaskManager中的调用
        System.out.println("设置内容面板: " + panel.getClass().getSimpleName());
    }
}

