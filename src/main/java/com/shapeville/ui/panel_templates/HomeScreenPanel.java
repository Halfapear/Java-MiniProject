package com.shapeville.ui.panel_templates;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

        initializeUI();

        JLabel instructions = new JLabel("Click a task to start learning!", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.ITALIC, 14));
        add(instructions, BorderLayout.SOUTH);
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // add(createTaskButton("2D Shape Identification", Constants.TASK_ID_SHAPE_ID_2D));
        // add(createTaskButton("3D Shape Identification", Constants.TASK_ID_SHAPE_ID_3D));
        add(createTaskButton("3D & 2D Shape Identification", Constants.TASK_ID_SHAPE_ID_2D_AND_3D));
        add(createTaskButton("Angle Recognition", Constants.TASK_ID_ANGLE_TYPE));
        add(createTaskButton("Area Calculation", Constants.TASK_ID_AREA_CALC));
        add(createTaskButton("Circle Calculation", Constants.TASK_ID_CIRCLE_CALC));
        add(createTaskButton("Compound Area", Constants.TASK_ID_COMPOUND_AREA));
    }

    private JButton createTaskButton(String label, String taskId) {
        JButton button = new JButton(label);
        button.addActionListener(e -> mainFrameRef.getTaskManager().startSpecificTask(taskId));
        return button;
    }
}
