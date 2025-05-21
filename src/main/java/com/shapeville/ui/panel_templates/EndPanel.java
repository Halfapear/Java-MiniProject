package com.shapeville.ui.panel_templates;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.shapeville.logic.ScoreManager;

public class EndPanel extends JPanel {
    private ScoreManager scoreManager;

    public EndPanel(ScoreManager scoreManager) {
        this.scoreManager = scoreManager; // 保存 ScoreManager 的引用
        int finalScore = scoreManager.getCurrentScore(); // 调用 getCurrentScore()
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        JLabel label = new JLabel("Thank you for playing Shapeville!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        // 显示最终得分
        JLabel scoreLabel = new JLabel("Your final score: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(scoreLabel, BorderLayout.SOUTH);
    }
}
