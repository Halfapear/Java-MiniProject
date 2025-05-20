package com.shapeville.task.sk1.angle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 角度类型识别任务面板
 */
public class AngleRecognitionPanel extends JPanel {
    private AngleVisualPanel angleVisualPanel;
    private JTextField angleInputField;
    private JLabel instructionsLabel;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JButton submitButton;
    private JButton homeButton;

    private int currentScore = 0;
    private int attemptsLeft = 3;
    private final int totalTypes = 4; // 需要识别的角度类型数量
    private Set<String> identifiedTypes = new HashSet<>(); // 已识别的角度类型
    private int currentAngle; // 当前显示的角度值

    private final Map<String, Integer> typeCounters = new HashMap<>();
    private final String[] angleTypes = {"acute", "right", "obtuse", "straight", "reflex"};

    public AngleRecognitionPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        initUI();
        initTask();
        addEventListeners();
    }

    private void initUI() {
        // 顶部面板：得分
        JPanel topPanel = new JPanel(new FlowLayout(1));
        topPanel.setBackground(new Color(70, 130, 180));
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // 中央面板：角度显示与输入
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        angleVisualPanel = new AngleVisualPanel();
        
        // 使用JPanel包装并居中
        JPanel visualWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        visualWrapper.add(angleVisualPanel);
        
        instructionsLabel = new JLabel("Enter an angle (0-360, multiples of 10):", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        angleInputField = new JTextField(10);
        angleInputField.setFont(new Font("Arial", Font.PLAIN, 16));
        angleInputField.setHorizontalAlignment(JTextField.CENTER);

        attemptsLabel = new JLabel("Attempts left: 3", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel.setForeground(Color.GRAY);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(visualWrapper);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(instructionsLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(angleInputField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(attemptsLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(messageLabel);

        add(centerPanel, BorderLayout.CENTER);

        // 底部面板：按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(1));
        submitButton = new JButton("Submit");
        homeButton = new JButton("Home");
        buttonPanel.add(submitButton);
        buttonPanel.add(homeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initTask() {
        // 初始化类型计数器
        for (String type : angleTypes) {
            typeCounters.put(type, 0);
        }
        
        // 初始化为0度
        currentAngle = 0;
        angleVisualPanel.setAngle(currentAngle);
        
        promptNewAngle();
    }

    private void promptNewAngle() {
        // 确保生成的角度类型是还未被识别的
        do {
            Random random = new Random();
            currentAngle = random.nextInt(37) * 10; // 0-360, 步长10
        } while (typeCounters.get(getAngleType(currentAngle)) >= 1 && identifiedTypes.size() < totalTypes);

        // 重置为0度显示
        angleVisualPanel.setAngle(0);
        instructionsLabel.setText("Enter an angle (0-360, multiples of 10):");
        angleInputField.setText("");
        attemptsLeft = 3; // 重置尝试次数
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        messageLabel.setText("");
    }

    private void drawAngle(int angle) {
        currentAngle = angle;
        angleVisualPanel.setAngle(angle);
    }

    private void addEventListeners() {
        submitButton.addActionListener(e -> handleAngleSubmission());
        homeButton.addActionListener(e -> System.exit(0));
    }

    private void handleAngleSubmission() {
        String input = angleInputField.getText().trim();

        if (input.isEmpty()) {
            setMessage("Please enter an angle measurement.", Color.RED);
            return;
        }

        try {
            int userAngle = Integer.parseInt(input);

            // 验证输入有效性
            if (userAngle < 0 || userAngle > 360 || userAngle % 10 != 0) {
                setMessage("Invalid input. Must be a multiple of 10 between 0-360.", Color.RED);
                return;
            }

            // 显示角度视觉表示
            currentAngle = userAngle;
            drawAngle(currentAngle);

            // 切换到类型识别模式
            instructionsLabel.setText("What type of angle is this? (acute/right/obtuse/straight/reflex)");
            angleInputField.setText("");

            // 移除旧的提交监听器，添加新的类型识别监听器
            for (ActionListener listener : submitButton.getActionListeners()) {
                submitButton.removeActionListener(listener);
            }
            submitButton.addActionListener(e -> handleTypeSubmission());

        } catch (NumberFormatException ex) {
            setMessage("Please enter a valid number.", Color.RED);
        }
    }

    private void handleTypeSubmission() {
        String userType = angleInputField.getText().trim().toLowerCase();
        String correctType = getAngleType(currentAngle);

        if (userType.isEmpty()) {
            setMessage("Please enter an angle type.", Color.RED);
            return;
        }

        // 验证输入的类型是否有效
        if (!Arrays.asList(angleTypes).contains(userType)) {
            setMessage("Invalid type. Valid types: acute, right, obtuse, straight, reflex", Color.RED);
            return;
        }

        boolean isCorrect = userType.equals(correctType);
        
        if (isCorrect) {
            // 正确回答
            currentScore += attemptsLeft; // 剩余尝试次数作为得分
            scoreLabel.setText("Score: " + currentScore);
            setMessage("Correct! This is a " + correctType + " angle.", Color.GREEN);

            // 记录已识别的类型
            identifiedTypes.add(correctType);
            typeCounters.put(correctType, typeCounters.get(correctType) + 1);
        } else {
            // 错误回答
            attemptsLeft--;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft > 0) {
                setMessage("Incorrect. Try again.", Color.RED);
                return; // 保持当前问题，不重置
            } else {
                // 尝试用完，显示正确答案
                setMessage("The correct type is: " + correctType, Color.BLUE);
            }
        }

        // 只有在回答正确或尝试用完时才重置
        SwingUtilities.invokeLater(() -> {
            // 重置为0度
            angleVisualPanel.setAngle(0);

            // 恢复初始的提交监听器
            for (ActionListener listener : submitButton.getActionListeners()) {
                submitButton.removeActionListener(listener);
            }
            submitButton.addActionListener(e -> handleAngleSubmission());

            // 检查是否完成所有类型
            if (identifiedTypes.size() >= totalTypes) {
                JOptionPane.showMessageDialog(this, 
                    "Congratulations! You've identified all angle types!\nFinal Score: " + currentScore, 
                    "Task Complete", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // 使用新线程实现延迟
            new Thread(() -> {
                try {
                    Thread.sleep(1500); // 延迟1.5秒
                    SwingUtilities.invokeLater(this::promptNewAngle);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });
    }

    private String getAngleType(int angle) {
        if (angle == 0 || angle == 360) return "reflex";
        else if (angle < 90) return "acute";
        else if (angle == 90) return "right";
        else if (angle < 180) return "obtuse";
        else if (angle == 180) return "straight";
        else return "reflex";
    }

    private void setMessage(String text, Color color) {
        messageLabel.setText(text);
        messageLabel.setForeground(color);
    }
}