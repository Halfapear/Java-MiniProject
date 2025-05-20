package com.shapeville.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.shapeville.main.MainFrame;

/**
 * NavigationBar - 顶部导航栏，显示分数和导航按钮。
 */
public class NavigationBar extends JPanel {
    private JLabel scoreLabel; // 显示当前分数
    private JButton homeButton; // 返回主页按钮
    private JButton endSessionButton; // 结束会话按钮
    private JProgressBar progressBar; // 显示任务进度
    private MainFrame mainFrameRef; // 引用 MainFrame，用于交互

    /**
     * 构造函数，初始化导航栏组件。
     * 
     * @param mainFrame MainFrame 的引用，用于导航操作。
     */
    public NavigationBar(MainFrame mainFrame) {
        this.mainFrameRef = mainFrame;
        initializeUI();
    }

    /**
     * 初始化导航栏的 UI 组件。
     */
    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 50));
        setBackground(new Color(200, 200, 200)); // 灰色背景

        // 左侧：分数显示
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel, BorderLayout.WEST);

        // 中间：进度条
        progressBar = new JProgressBar(0, 100); // 默认最大值为 100
        progressBar.setStringPainted(true); // 显示百分比
        add(progressBar, BorderLayout.CENTER);

        // 右侧：导航按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        homeButton = new JButton("Home");
        endSessionButton = new JButton("End Session");

        // 添加按钮监听器
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameRef.navigateToHome();
            }
        });

        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameRef.endSession();
            }
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(endSessionButton);
        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * 更新分数显示。
     * 
     * @param score 当前分数。
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * 更新进度条。
     * 
     * @param current 当前任务索引。
     * @param total   总任务数。
     */
    public void updateProgress(int current, int total) {
        if (total > 0) {
            int progress = (int) ((current / (double) total) * 100);
            progressBar.setValue(progress);
            progressBar.setString(current + "/" + total);
        } else {
            progressBar.setValue(0);
            progressBar.setString("0/0");
        }
    }
}