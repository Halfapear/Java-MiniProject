package com.shapeville.task.sk1;

import com.shapeville.main.MainFrame;
import com.shapeville.model.Shape;
import com.shapeville.ui.panel_templates.TaskPanel;
import com.shapeville.utils.Constants;
import com.shapeville.logic.ScoreManager;
import com.shapeville.logic.TaskLogic;
import com.shapeville.model.Feedback;
import com.shapeville.model.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Task 1: 2D and 3D Shape Recognition
 */
public class Task1Panel2D extends JPanel implements TaskPanel {
    private MainFrame mainFrame;
    private JLabel shapeImageLabel;
    private JTextField answerTextField;
    private JButton submitButton;
    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JLabel promptLabel;
    private JLabel typePromptLabel; // 新增：图形类型提示标签
    private List<Shape> shapes;
    private Shape currentShape;
    private ScoreManager scoreManager;
    private int attemptsLeft = 3;
    private int attemptsUsed = 1;
    private int completedShapes = 0;
    private JPanel imageContainer;
    private JPanel inputContainer;
    private JPanel feedbackContainer;

    public Task1Panel2D(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.scoreManager = mainFrame.getScoreManager();
        initializeUI();
        initializeShapes();
        loadRandomShape();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;

        // Apply high contrast background to the main panel
        setBackground(new Color(30, 30, 30));

        // 标题区域（居中）
        JLabel titleLabel = new JLabel("2D Shape Recognition");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // High contrast text
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // 固定大小的图片容器（300x300像素）
        imageContainer = new JPanel(new BorderLayout());
        imageContainer.setPreferredSize(new Dimension(300, 300));
        imageContainer.setMaximumSize(new Dimension(300, 300));
        imageContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        imageContainer.setBackground(new Color(50, 50, 50)); // Contrast background for container
        
        shapeImageLabel = new JLabel();
        shapeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        shapeImageLabel.setVerticalAlignment(JLabel.CENTER);
        imageContainer.add(shapeImageLabel, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        add(imageContainer, gbc);

        // 居中的输入区域
        inputContainer = new JPanel();
        inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
        inputContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputContainer.setBackground(new Color(30, 30, 30)); // Match main panel background
        
        promptLabel = new JLabel("Enter the correct shape name:");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        promptLabel.setForeground(Color.WHITE); // High contrast text
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputContainer.add(promptLabel);
        
        answerTextField = new JTextField(20);
        answerTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        answerTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        answerTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        answerTextField.setBackground(new Color(60, 60, 60)); // High contrast background
        answerTextField.setForeground(Color.WHITE); // High contrast text
        answerTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkAnswer();
                }
            }
        });
        inputContainer.add(answerTextField);
        
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setBackground(new Color(80, 80, 80)); // High contrast background
        submitButton.setForeground(Color.WHITE); // High contrast text
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        inputContainer.add(submitButton);
        
        gbc.gridy = 2;
        add(inputContainer, gbc);

        // 居中的反馈区域
        feedbackContainer = new JPanel();
        feedbackContainer.setLayout(new BoxLayout(feedbackContainer, BoxLayout.Y_AXIS));
        feedbackContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackContainer.setBackground(new Color(30, 30, 30)); // Match main panel background
        
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Color will be set in checkAnswer()
        feedbackContainer.add(resultLabel);
        
        attemptsLabel = new JLabel("Remaining attempts: " + attemptsLeft);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptsLabel.setForeground(Color.YELLOW); // High contrast text for status
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackContainer.add(attemptsLabel);
        
        scoreLabel = new JLabel("Current Score: " + scoreManager.getCurrentScore());
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.YELLOW); // High contrast text for status
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackContainer.add(scoreLabel);
        
        gbc.gridy = 3;
        gbc.weighty = 1.0; // 让底部区域占据剩余空间
        add(feedbackContainer, gbc);
    }

    private void initializeShapes() {
        shapes = new ArrayList<>();
        
        // 添加2D形状
        shapes.add(new Shape("circle", "/assets/2d/circle.png", "2D"));
        shapes.add(new Shape("rectangle", "/assets/2d/rectangle.png", "2D"));
        shapes.add(new Shape("triangle", "/assets/2d/triangle.png", "2D"));
        shapes.add(new Shape("square", "/assets/2d/square.png", "2D"));
        shapes.add(new Shape("heptagon", "/assets/2d/heptagon.png", "2D"));
        shapes.add(new Shape("hexagon", "/assets/2d/hexagon.png", "2D"));
        shapes.add(new Shape("kite", "/assets/2d/kite.png", "2D"));
        shapes.add(new Shape("octagon", "/assets/2d/octagon.png", "2D"));
        shapes.add(new Shape("oval", "/assets/2d/oval.png", "2D"));
        shapes.add(new Shape("pentagon", "/assets/2d/pentagon.png", "2D"));
        shapes.add(new Shape("rhombus", "/assets/2d/rhombus.png", "2D"));
    }

    private void loadRandomShape() {
        if (completedShapes >= Constants.SHAPES_PER_IDENTIFICATION_QUIZ) {
            Object[] options = {"Return to Home", "Try Again"};
            int choice = JOptionPane.showOptionDialog(this,
                    "You've completed " + Constants.SHAPES_PER_IDENTIFICATION_QUIZ + 
                    " shape recognitions! Your total score is: " + scoreManager.getCurrentScore() +
                    ". What would you like to do next?",
                    "Task Completed",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            if (choice == 0) {
                mainFrame.navigateToHome();
            } else {
                resetState();
            }
            scoreManager.incrementTaskTypeCompletedCount();
            return;
        }
        
        Random random = new Random();
        currentShape = shapes.get(random.nextInt(shapes.size()));
        ImageIcon icon = new ImageIcon(getClass().getResource(currentShape.getImagePath()));
        
        shapeImageLabel.setIcon(null);
        if (icon != null && icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            shapeImageLabel.setIcon(icon);
        } else {
            shapeImageLabel.setText("Failed to load image");
        }
        
        attemptsLeft = 3;
        attemptsUsed = 1;
        resultLabel.setText("");
        answerTextField.setText("");
        attemptsLabel.setText("Remaining attempts: " + attemptsLeft);
        submitButton.setEnabled(true);
    }

    private void checkAnswer() {
        String userAnswer = answerTextField.getText().trim();
        boolean isCorrect = userAnswer.equalsIgnoreCase(currentShape.getName());
        // int points = attemptsUsed == 1 ? 3 : attemptsUsed == 2 ? 2 : 1;
        int points = scoreManager.calculatePoints(attemptsUsed,false);        
        String message;
        
        if (isCorrect) {
            scoreManager.recordScoreAndFeedback(points);
            completedShapes++;
            message = "Correct! 😊 +" + points + " points"; // Added symbol and text
            resultLabel.setForeground(new Color(0, 255, 0)); // Green for correct
        } else {
            attemptsLeft--;
            if (attemptsLeft <= 0) {
                message = "Incorrect! 😟 The correct answer is: " + currentShape.getName(); // Added symbol and text
                scoreManager.recordScoreAndFeedback(0);
                completedShapes++;
                resultLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
            } else {
                message = "Try again! 😟 Remaining attempts: " + attemptsLeft; // Added symbol and text
                attemptsUsed++;
                resultLabel.setForeground(new Color(255, 0, 0)); // Red for incorrect
            }
        }
        
        resultLabel.setText(message);
        attemptsLabel.setText("Remaining attempts: " + attemptsLeft);
        scoreLabel.setText("Current Score: " + scoreManager.getCurrentScore());
        
        Feedback feedback = new Feedback(
            isCorrect, 
            points, 
            message, 
            currentShape.getName(), 
            true, 
            completedShapes >= Constants.SHAPES_PER_IDENTIFICATION_QUIZ
        );
        showFeedback(feedback);
        
        if (isCorrect || attemptsLeft <= 0) {
            submitButton.setEnabled(false);
            Timer timer = new Timer(1500, e -> loadRandomShape());
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void resetState() {
        completedShapes = 0;
        scoreManager.resetSession();
        loadRandomShape();
    }

    @Override
    public String getPanelId() {
        return Constants.SHAPE_IDENTIFICATION_PANEL_ID;
        
    }

    @Override
    public void setTaskLogicCallback(TaskLogic logic) {
        // 实现任务逻辑回调
    }

    @Override
    public void displayProblem(Problem problem) {
        // 显示问题
    }

    @Override
    public void showFeedback(Feedback feedback) {
        resultLabel.setText(feedback.getMessage());
        scoreLabel.setText("Current Score: " + scoreManager.getCurrentScore());
    }
}