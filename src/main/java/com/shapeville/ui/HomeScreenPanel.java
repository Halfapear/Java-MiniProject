package com.shapeville.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.shapeville.main.MainFrame;
import com.shapeville.utils.Constants;

public class HomeScreenPanel extends JPanel {
    private MainFrame mainFrameRef;

    public HomeScreenPanel(MainFrame mainFrame) {
        this.mainFrameRef = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        JLabel title = new JLabel("Welcome to Shapeville!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 20, 20));
        // Use constants for Task IDs for robustness
        addButton(buttonGrid, "Task 1: Identify Shapes (2D)", Constants.TASK_ID_SHAPE_ID_2D);
        addButton(buttonGrid, "Task 2: Identify Angles", Constants.TASK_ID_ANGLE_TYPE);
        // TODO: Add actual Task IDs for other buttons from Constants.java
        addButton(buttonGrid, "Task 3: Area Calculation", Constants.TASK_ID_AREA_CALC);
        addButton(buttonGrid, "Task 4: Circle Calc",      Constants.TASK_ID_CIRCLE_CALC);

        addButton(buttonGrid, "Bonus 1: Compound Area", Constants.TASK_ID_COMPOUND_AREA);
        addButton(buttonGrid, "Bonus 2: Sector Calc", Constants.TASK_ID_SECTOR_CALC);
        add(buttonGrid, BorderLayout.CENTER);

        JLabel instructions = new JLabel("Click a task to start learning!", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.ITALIC, 14));
        add(instructions, BorderLayout.SOUTH);
    }

    private void addButton(JPanel container, String buttonText, String taskId) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        // When a button is clicked, tell TaskManager to start that specific task.
        // TaskManager will then load the correct Panel and Logic.
        button.addActionListener(e -> mainFrameRef.getTaskManager().startSpecificTask(taskId));
        container.add(button);
    }
}
