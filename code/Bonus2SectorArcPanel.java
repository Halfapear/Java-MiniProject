package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Bonus2SectorArcPanel extends TaskPanel {
    private JTextField answerField;
    private int attempt = 0;
    private int currentSectorIndex = 0;
    private JLabel sectorImageLabel;
    private JLabel questionLabel;
    private List<SectorProblem> sectorProblems;
    private int practisedSectors = 0;
    private Timer timer;
    private int timeRemaining = 300; // 5 minutes in seconds
    private JLabel timerLabel;
    private boolean timerRunning = false;
    
    // Constants for PI value
    private static final double PI = 3.14;

    public Bonus2SectorArcPanel() {
        super();
        initializeSectorProblems();
    }

    /**
     * Initialize all sector problems with their parameters, questions,
     * correct answers and solution formulas
     */
    private void initializeSectorProblems() {
        sectorProblems = new ArrayList<>();
        
        // Add 8 sector problems corresponding to Figure 13
        // Problem 1: 90° sector with radius 8 cm
        sectorProblems.add(new SectorProblem(
            1,
            8.0, // radius in cm
            90.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            50.24, // (90/360) * π * 8² = 50.24
            "Area of sector = (θ/360°) × π × r²",
            "cm"
        ));
        
        // Problem 2: 130° sector with radius 10 ft
        sectorProblems.add(new SectorProblem(
            2,
            10.0, // radius in ft
            130.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            113.22, // (130/360) * π * 10² = 113.22
            "Area of sector = (θ/360°) × π × r²",
            "ft"
        ));
        
        // Problem 3: 250° sector with radius 11 cm
        sectorProblems.add(new SectorProblem(
            3,
            11.0, // radius in cm
            250.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            264.19, // (250/360) * π * 11² = 264.19
            "Area of sector = (θ/360°) × π × r²",
            "cm"
        ));
        
        // Problem 4: 110° sector with radius 22 ft
        sectorProblems.add(new SectorProblem(
            4,
            22.0, // radius in ft
            110.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            464.31, // (110/360) * π * 22² = 464.31
            "Area of sector = (θ/360°) × π × r²",
            "ft"
        ));
        
        // Problem 5: 100° sector with radius 2.5 m
        sectorProblems.add(new SectorProblem(
            5,
            2.5, // radius in m
            100.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            5.45, // (100/360) * π * 2.5² = 5.45
            "Area of sector = (θ/360°) × π × r²",
            "m"
        ));
        
        // Problem 6: 270° sector with radius 8 in
        sectorProblems.add(new SectorProblem(
            6,
            8.0, // radius in in
            270.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            135.65, // (270/360) * π * 8² = 135.65
            "Area of sector = (θ/360°) × π × r²",
            "in"
        ));
        
        // Problem 7: 280° sector with radius 12 yd
        sectorProblems.add(new SectorProblem(
            7,
            12.0, // radius in yd
            280.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            316.85, // (280/360) * π * 12² = 316.85
            "Area of sector = (θ/360°) × π × r²",
            "yd"
        ));
        
        // Problem 8: 290° sector with radius 15 mm
        sectorProblems.add(new SectorProblem(
            8,
            15.0, // radius in mm
            290.0, // angle in degrees
            "Calculate the area of the shaded sector (use π = 3.14)",
            342.71, // (290/360) * π * 15² = 342.71
            "Area of sector = (θ/360°) × π × r²",
            "mm"
        ));
    }

    /**
     * Set up the user interface components
     */
    @Override
    protected void setupUI() {
        content.setLayout(new BorderLayout(10, 10));
        
        // North section for title and timer
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bonus 2: Sector Area Calculation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        
        timerLabel = new JLabel("Time remaining: 5:00", SwingConstants.CENTER);
        northPanel.add(timerLabel, BorderLayout.CENTER);
        
        content.add(northPanel, BorderLayout.NORTH);
        
        // Center section for question and sector image
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        centerPanel.add(questionLabel, BorderLayout.NORTH);
        
        sectorImageLabel = new JLabel("", SwingConstants.CENTER);
        sectorImageLabel.setPreferredSize(new Dimension(300, 300));
        centerPanel.add(sectorImageLabel, BorderLayout.CENTER);
        
        content.add(centerPanel, BorderLayout.CENTER);
        
        // South section for input and buttons
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
        
        for (int i = 0; i < sectorProblems.size(); i++) {
            final int index = i;
            JButton sectorButton = new JButton("Sector " + (i + 1));
            sectorButton.addActionListener(e -> {
                currentSectorIndex = index;
                resetTimer();
                showCurrentSector();
            });
            sectorSelectionPanel.add(sectorButton);
        }
        
        content.add(sectorSelectionPanel, BorderLayout.SOUTH);
        content.add(southPanel, BorderLayout.AFTER_LAST_LINE);
        
        // Display the first sector
        showCurrentSector();
        startTimer();
    }
    
    /**
     * Display the current sector information
     */
    private void showCurrentSector() {
        SectorProblem currentSector = sectorProblems.get(currentSectorIndex);
        
        // Format the question with sector details
        String questionText = String.format("<html><body style='width: 400px'>" +
                "Sector %d: radius = %.1f %s, angle = %.0f°<br>" +
                "%s</body></html>",
                currentSector.getNumber(),
                currentSector.getRadius(),
                currentSector.getUnit(),
                currentSector.getAngle(),
                currentSector.getQuestion());
        
        questionLabel.setText(questionText);
        
        // Display sector number and parameters
        sectorImageLabel.setText(String.format("<html><div style='text-align: center;'>" +
                "<h2>Sector %d</h2>" +
                "<p>Radius = %.1f %s</p>" +
                "<p>Angle = %.0f°</p>" +
                "</div></html>",
                currentSector.getNumber(),
                currentSector.getRadius(),
                currentSector.getUnit(),
                currentSector.getAngle()));
        
        // Reset attempt count
        attempt = 0;
        
        // Reset and start timer
        resetTimer();
    }

    /**
     * Start the countdown timer
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
                        JOptionPane.showMessageDialog(Bonus2SectorArcPanel.this, 
                                "Time's up! Let's see the solution.");
                        showSolution();
                    });
                }
            }
        }, 1000, 1000);
    }
    
    /**
     * Reset the timer
     */
    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timerRunning = false;
        startTimer();
    }
    
    /**
     * Update the timer label
     */
    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time remaining: %d:%02d", minutes, seconds));
    }

    /**
     * Check if the user's answer is correct
     * @param input The user's input answer
     */
    @Override
    protected void checkAnswer(String input) {
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer!");
            return;
        }
        
        try {
            double userAnswer = Double.parseDouble(input);
            SectorProblem currentSector = sectorProblems.get(currentSectorIndex);
            
            // Check if the answer is correct (allowing 5% error margin)
            if (Math.abs(userAnswer - currentSector.getCorrectAnswer()) / currentSector.getCorrectAnswer() <= 0.05) {
                // Stop the timer
                if (timer != null) {
                    timer.cancel();
                    timerRunning = false;
                }
                
                JOptionPane.showMessageDialog(this, "Correct answer!");
                practisedSectors++;
                
                // Mark this sector as completed
                currentSector.setCompleted(true);
                
                // Check if all sectors have been completed
                if (practisedSectors >= 8) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have completed all sector exercises!");
                    MainApp.switchPanel(new HomePanel());
                } else {
                    // Move to the next uncompleted sector
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
                    practisedSectors++;
                    
                    // Check if all sectors have been completed
                    if (practisedSectors >= 8) {
                        JOptionPane.showMessageDialog(this, "You have completed all sector exercises!");
                        MainApp.switchPanel(new HomePanel());
                    } else {
                        // Move to the next uncompleted sector
                        moveToNextSector();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer, please try again!\n" + (3 - attempt) + " attempts remaining.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }
    
    /**
     * Show the solution for the current sector
     */
    private void showSolution() {
        SectorProblem currentSector = sectorProblems.get(currentSectorIndex);
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        // Calculate the solution step by step
        double angle = currentSector.getAngle();
        double radius = currentSector.getRadius();
        double area = (angle / 360.0) * PI * radius * radius;
        
        StringBuilder solution = new StringBuilder();
        solution.append("Formula: Area of sector = (θ/360°) × π × r²\n\n");
        solution.append("Step 1: Substitute the values\n");
        solution.append(String.format("Area = (%.0f/360) × %.2f × (%.1f)²\n", angle, PI, radius));
        solution.append(String.format("Area = %.4f × %.2f × %.2f\n", angle/360.0, PI, radius*radius));
        solution.append(String.format("Area = %.2f square %s\n", area, currentSector.getUnit()));
        
        JOptionPane.showMessageDialog(this, solution.toString(), 
                "Solution for Sector " + currentSector.getNumber(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Move to the next uncompleted sector
     */
    private void moveToNextSector() {
        // Find the next uncompleted sector
        int nextIndex = (currentSectorIndex + 1) % sectorProblems.size();
        while (sectorProblems.get(nextIndex).isCompleted() && nextIndex != currentSectorIndex) {
            nextIndex = (nextIndex + 1) % sectorProblems.size();
        }
        
        currentSectorIndex = nextIndex;
        answerField.setText("");
        showCurrentSector();
    }
    
    /**
     * Inner class representing a sector problem
     */
    private class SectorProblem {
        private int number;
        private double radius;
        private double angle;
        private String question;
        private double correctAnswer;
        private String formula;
        private String unit;
        private boolean completed;
        
        /**
         * Constructor for SectorProblem
         * @param number Problem number
         * @param radius Radius of the sector
         * @param angle Angle of the sector in degrees
         * @param question Question about the sector
         * @param correctAnswer Correct area value
         * @param formula Formula to calculate the area
         * @param unit Unit of measurement
         */
        public SectorProblem(int number, double radius, double angle, String question, 
                            double correctAnswer, String formula, String unit) {
            this.number = number;
            this.radius = radius;
            this.angle = angle;
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.formula = formula;
            this.unit = unit;
            this.completed = false;
        }
        
        /**
         * Get the problem number
         * @return The problem number
         */
        public int getNumber() {
            return number;
        }
        
        /**
         * Get the radius of the sector
         * @return The radius
         */
        public double getRadius() {
            return radius;
        }
        
        /**
         * Get the angle of the sector
         * @return The angle in degrees
         */
        public double getAngle() {
            return angle;
        }
        
        /**
         * Get the question about the sector
         * @return The question
         */
        public String getQuestion() {
            return question;
        }
        
        /**
         * Get the correct answer (area)
         * @return The correct area value
         */
        public double getCorrectAnswer() {
            return correctAnswer;
        }
        
        /**
         * Get the formula to calculate the area
         * @return The formula
         */
        public String getFormula() {
            return formula;
        }
        
        /**
         * Get the unit of measurement
         * @return The unit
         */
        public String getUnit() {
            return unit;
        }
        
        /**
         * Check if this sector problem has been completed
         * @return True if completed, false otherwise
         */
        public boolean isCompleted() {
            return completed;
        }
        
        /**
         * Set the completion status of this sector problem
         * @param completed The completion status
         */
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
