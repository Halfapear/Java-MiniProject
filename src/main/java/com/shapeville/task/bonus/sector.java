package com.shapeville.task.bonus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Bonus 2: Sector Area Calculation
 * Allows users to choose a sector and calculate its area.
 */
public class Sector extends JPanel {
    private JTextField answerField;
    private int attempt = 0;
    private int currentSectorIndex = 0;
    private JLabel sectorImageLabel;
    private JLabel questionLabel;
    private JLabel timerLabel;
    private List<SectorShape> sectors;
    private int practicedSectors = 0;
    private JPanel content;
    private Timer timer;
    private int timeRemaining = 300; // 5 minutes (300 seconds)
    private boolean timerRunning = false;
    private final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Constructor
     */
    public Sector() {
        setLayout(new BorderLayout());
        content = new JPanel();
        add(content, BorderLayout.CENTER);
        initializeSectors();
        setupUI();
    }

    /**
     * Initializes all sectors with their descriptions, questions, correct answers, and solution hints.
     */
    private void initializeSectors() {
        sectors = new ArrayList<>();
        
        // Add 8 sectors from Figure 13
        // Sector 1: 90-degree sector, radius 8 cm
        sectors.add(new SectorShape(
            "Sector 1",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            157.75, // (90/360) * π * 8² 
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (90/360) × 3.14 × 8² = 50.24 square centimeters"},
            createSectorImage(1),
            "Radius = 8 cm, Central angle = 90°"
        ));
        
        // Sector 5: 100-degree sector, radius 35 meters
        sectors.add(new SectorShape(
            "Sector 2",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            3355.00, // (100/360) * π * 35² 
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (100/360) × 3.14 × 35² = 3355.00 square meters"},
            createSectorImage(2),
            "Radius = 35 meters, Central angle = 100°"
        ));
        
        // Sector 4: 110-degree sector, radius 22 feet
        sectors.add(new SectorShape(
            "Sector 3",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            1458.12, // (110/360) * π * 22² 
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (110/360) × 3.14 × 22² = 1458.12 square feet"},
            createSectorImage(3),
            "Radius = 22 feet, Central angle = 110°"
        ));
        
        // Sector 2: 130-degree sector, radius 18 feet
        sectors.add(new SectorShape(
            "Sector 4",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            1153.57, // [(130/360) * π * 18²] 
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (130/360) × 3.14 × 18² = 1153.57 square feet"},
            createSectorImage(4),
            "Radius = 18 feet, Central angle = 130°"
        ));
        
        // Sector 3: 240-degree sector, radius 14 cm
        sectors.add(new SectorShape(
            "Sector 5",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            1288.32, // (240/360) * π * 14²
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (240/360) × 3.14 × 14² = 1288.32 square centimeters"},
            createSectorImage(5),
            "Radius = 14 cm, Central angle = 240°"
        ));
        
        // Sector 6: 250-degree sector, radius 15 mm
        sectors.add(new SectorShape(
            "Sector 6",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            1540.56, // (250/360) * π * 15²
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (250/360) × 3.14 × 15² = 1540.56 square millimeters"},
            createSectorImage(6),
            "Radius = 15 mm, Central angle = 250°"
        ));
        
        // Sector 7: 270-degree sector, radius 8 inches
        sectors.add(new SectorShape(
            "Sector 7",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            473.26, // (270/360) * π * 8² 
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (270/360) × 3.14 × 8² = 473.26 square inches"},
            createSectorImage(7),
            "Radius = 8 inches, Central angle = 270°"
        ));
        
        // Sector 8: 280-degree sector, radius 12 yards
        sectors.add(new SectorShape(
            "Sector 8",
            "Calculate the area of the shaded region. Round to two decimal places. (Use π = 3.14)",
            1104.28, // (280/360) * π * 12²
            new String[]{"Sector area = (central angle/360°) × π × radius²", "= (280/360) × 3.14 × 12² = 1104.28 square yards"},
            createSectorImage(8),
            "Radius = 12 yards, Central angle = 280°"
        ));
    }

    /**
     * Creates an image for the sector.
     * @param sectorNumber The number of the sector.
     * @return The sector image.
     */
    private ImageIcon createSectorImage(int sectorNumber) {
        // Load image from resources/assets/sector directory
        String imagePath = "/assets/sectors/sector" + sectorNumber + ".png";
        return com.shapeville.utils.ImageLoader.loadImageAndScale(imagePath, 300, 300);
    }

    /**
     * Sets up the user interface components.
     */
    private void setupUI() {
        content.setLayout(new BorderLayout(10, 10));
        
        // North area for title and timer
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bonus 2: Sector Area Calculation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        
        timerLabel = new JLabel("Time Remaining: 5:00", SwingConstants.CENTER);
        northPanel.add(timerLabel, BorderLayout.CENTER);
        
        content.add(northPanel, BorderLayout.NORTH);
        
        // Center area for problem description and sector image
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("", SwingConstants.CENTER);
        infoPanel.add(questionLabel, BorderLayout.NORTH);
        
        JLabel formulaLabel = new JLabel("<html><body style='width: 300px; text-align: center;'>" +
                "Sector area = (central angle/360°) × π × radius²</body></html>", SwingConstants.CENTER);
        formulaLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        infoPanel.add(formulaLabel, BorderLayout.CENTER);
        
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        
        sectorImageLabel = new JLabel("", SwingConstants.CENTER);
        sectorImageLabel.setPreferredSize(new Dimension(300, 300));
        centerPanel.add(sectorImageLabel, BorderLayout.CENTER);
        
        content.add(centerPanel, BorderLayout.CENTER);
        
        // South area for input and buttons
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JLabel answerLabel = new JLabel("Enter area:");
        southPanel.add(answerLabel);
        
        answerField = new JTextField(10);
        southPanel.add(answerField);
        
        JButton submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(e -> checkAnswer(answerField.getText()));
        southPanel.add(submitButton);
        
        // Add sector selection buttons
        JPanel sectorSelectionPanel = new JPanel();
        sectorSelectionPanel.setLayout(new GridLayout(2, 4, 5, 5));
        
        for (int i = 0; i < sectors.size(); i++) {
            final int index = i;
            JButton sectorButton = new JButton("Sector " + (i + 1));
            sectorButton.addActionListener(e -> {
                currentSectorIndex = index;
                resetTimer();
                showCurrentSector();
            });
            sectorSelectionPanel.add(sectorButton);
        }
        
        // Add home button
        JButton homeButton = new JButton("Return to Home");
        homeButton.addActionListener(e -> {
            // Logic to return to the home page
            if (timer != null) {
                timer.cancel();
            }
             // 获取MainFrame实例并导航到主页
            javax.swing.JFrame topFrame = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof com.shapeville.main.MainFrame) {
                ((com.shapeville.main.MainFrame) topFrame).navigateToHome();
            }
        });
        sectorSelectionPanel.add(homeButton);
        
        content.add(sectorSelectionPanel, BorderLayout.SOUTH);
        content.add(southPanel, BorderLayout.AFTER_LAST_LINE);
        
        // Display the first sector
        showCurrentSector();
        startTimer();
    }
    
    /**
     * Displays the current sector information.
     */
    private void showCurrentSector() {
        SectorShape currentSector = sectors.get(currentSectorIndex);
        questionLabel.setText("<html><body style='width: 400px'>" + 
                              currentSector.getDescription() + "<br>" + 
                              currentSector.getQuestion() + "<br>" +
                              currentSector.getValues() + "</body></html>");
        
        // Display the sector image
        if (currentSector.getImage() != null) {
            sectorImageLabel.setIcon(currentSector.getImage());
            sectorImageLabel.setText("");
        } else {
            // If no image, display the sector description
            sectorImageLabel.setIcon(null);
            sectorImageLabel.setText("Sector " + (currentSectorIndex + 1) + " / " + sectors.size());
        }
        
        // Reset attempt count
        attempt = 0;
    }
    
    /**
     * Starts the countdown timer.
     */
    private void startTimer() {
        if (timerRunning) {
            return;
        }
        
        timerRunning = true;
        timeRemaining = 300; // 5 minutes
        updateTimerLabel();
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    SwingUtilities.invokeLater(() -> updateTimerLabel());
                } else {
                    // Time's up
                    timer.cancel();
                    timerRunning = false;
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(Sector.this, 
                                "Time's up! Let's see the solution.");
                        showSolution();
                    });
                }
            }
        }, 1000, 1000);
    }
    
    /**
     * Resets the timer.
     */
    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timerRunning = false;
        startTimer();
    }
    
    /**
     * Updates the timer label.
     */
    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %d:%02d", minutes, seconds));
    }

    /**
     * Checks if the user's answer is correct.
     * @param input The user's input answer.
     */
    private void checkAnswer(String input) {
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer!");
            return;
        }
        
        try {
            double userAnswer = Double.parseDouble(input);
            SectorShape currentSector = sectors.get(currentSectorIndex);
            
            // Check if the answer is correct (allowing a 5% error margin)
            if (Math.abs(userAnswer - currentSector.getCorrectAnswer()) / currentSector.getCorrectAnswer() <= 0.05) {
                // Stop the timer
                if (timer != null) {
                    timer.cancel();
                    timerRunning = false;
                }
                 // 记录分数 - 根据尝试次数计算得分
                recordScore(attempt + 1);
                
                JOptionPane.showMessageDialog(this, "Correct answer!");
                practicedSectors++;

              // 更新进度条
            updateProgress(practicedSectors, sectors.size());
                
                JOptionPane.showMessageDialog(this, "Correct answer!");
                practicedSectors++;
                
                // Mark this sector as completed
                currentSector.setCompleted(true);
                
                // Check if all sectors are completed
                if (practicedSectors >= sectors.size()) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have completed all sector area calculation exercises!");
                    // Return to home page or proceed to the next task
                } else {
                    // Move to the next incomplete sector
                    moveToNextSector();
                }
            } else {
                attempt++;
                if (attempt >= 3) {
                    // Stop the timer
                    if (timer != null) {
                        timer.cancel();
                        timerRunning = false;
                    }
                    
                    showSolution();
                    
                    // Mark this sector as completed
                    currentSector.setCompleted(true);
                    practicedSectors++;
                    
                    // Check if all sectors are completed
                    if (practicedSectors >= sectors.size()) {
                        JOptionPane.showMessageDialog(this, "You have completed all sector area calculation exercises!");
                        // Return to home page or proceed to the next task
                    } else {
                        // Move to the next incomplete sector
                        moveToNextSector();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer. Please try again!\nYou have " + (3 - attempt) + " attempts remaining.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }


    }
    
     // 添加记录分数的方法
/**
 * 记录用户得分
 * @param attemptsUsed 用户使用的尝试次数
 */
private void recordScore(int attemptsUsed) {
    // 获取MainFrame实例
    javax.swing.JFrame topFrame = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
    if (topFrame instanceof com.shapeville.main.MainFrame) {
        com.shapeville.main.MainFrame mainFrame = (com.shapeville.main.MainFrame) topFrame;
        // 获取ScoreManager并记录分数
        com.shapeville.logic.ScoreManager scoreManager = mainFrame.getScoreManager();
        if (scoreManager != null) {
            // 扇形区域计算是高级任务，使用高级评分
            int points = scoreManager.calculatePoints(attemptsUsed, true);
            scoreManager.recordScoreAndFeedback(points);
        }
    }
}

    /**
     * Displays the solution for the current sector.
     */
    private void showSolution() {
        SectorShape currentSector = sectors.get(currentSectorIndex);
        
        StringBuilder solution = new StringBuilder();
        solution.append("The correct answer is: ").append(df.format(currentSector.getCorrectAnswer())).append("\n\n");
        solution.append("Solution:\n");
        for (String hint : currentSector.getHints()) {
            solution.append("- ").append(hint).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, solution.toString(), 
                "Solution for Sector " + (currentSectorIndex + 1), JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Moves to the next incomplete sector.
     */
    private void moveToNextSector() {
        // Find the next incomplete sector
        int nextIndex = (currentSectorIndex + 1) % sectors.size();
        while (sectors.get(nextIndex).isCompleted() && nextIndex != currentSectorIndex) {
            nextIndex = (nextIndex + 1) % sectors.size();
        }
        
        currentSectorIndex = nextIndex;
        answerField.setText("");
        showCurrentSector();
    }
    
    /**
     * Inner class representing a circular sector.
     */
    private class SectorShape {
        private String description;
        private String question;
        private double correctAnswer;
        private String[] hints;
        private ImageIcon image;
        private boolean completed;
        private String values;
        
        /**
         * Constructor for SectorShape.
         * @param description The description of the sector.
         * @param question The question about the sector.
         * @param correctAnswer The correct area value.
         * @param hints The solution hints.
         * @param image The sector image.
         * @param values The radius and angle values of the sector.
         */
        public SectorShape(String description, String question, double correctAnswer, String[] hints, ImageIcon image, String values) {
            this.description = description;
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.hints = hints;
            this.image = image;
            this.completed = false;
            this.values = values;
        }
        
        /**
         * Gets the description of the sector.
         * @return The description.
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Gets the question about the sector.
         * @return The question.
         */
        public String getQuestion() {
            return question;
        }
        
        /**
         * Gets the correct answer (area).
         * @return The correct area value.
         */
        public double getCorrectAnswer() {
            return correctAnswer;
        }
        
        /**
         * Gets the solution hints.
         * @return The hints as an array of strings.
         */
        public String[] getHints() {
            return hints;
        }
        
        /**
         * Gets the sector image.
         * @return The image.
         */
        public ImageIcon getImage() {
            return image;
        }
        
        /**
         * Checks if this sector is completed.
         * @return True if completed, otherwise false.
         */
        public boolean isCompleted() {
            return completed;
        }
        
        /**
         * Sets the completion status of this sector.
         * @param completed The completion status.
         */
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
        
        /**
         * Gets the radius and angle values of the sector.
         * @return The values.
         */
        public String getValues() {
            return values;
        }
    }

    /**
 * 更新进度条
 * @param current 当前完成的扇形数量
 * @param total 总扇形数量
 */
private void updateProgress(int current, int total) {
    // 获取MainFrame实例
    javax.swing.JFrame topFrame = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
    if (topFrame instanceof com.shapeville.main.MainFrame) {
        com.shapeville.main.MainFrame mainFrame = (com.shapeville.main.MainFrame) topFrame;
        // 获取NavigationBar并更新进度
        mainFrame.getNavigationBar().updateProgress(current, total);
    }
}

}

