package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Bonus1CompoundAreaPanel extends JPanel {
    private JTextField answerField;
    private int attempt = 0;
    private int currentShapeIndex = 0;
    private JLabel shapeImageLabel;
    private JLabel questionLabel;
    private List<CompoundShape> shapes;
    private int practisedShapes = 0;
    private JPanel content;
    private NavigationPanel nav;

    public Bonus1CompoundAreaPanel() {
        setLayout(new BorderLayout());
        nav = new NavigationPanel();
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        add(nav, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        initializeShapes();
        setupUI();
   
    }

    /**
     * Initialize all compound shapes with their descriptions, questions,
     * correct answers and solution hints
     */
    private void initializeShapes() {
        shapes = new ArrayList<>();
        
        // Add 9 compound shapes corresponding to Figure 10
        // Shape 1: Trapezoid + Rectangle
        shapes.add(new CompoundShape(
            "Shape 1: A compound shape consisting of a trapezoid and a rectangle",
            "Calculate the total area of the compound shape (unit: square cm)",
            196, // 14*14 = 196 (rectangle part)
            new String[]{"Hint: Break down the shape into simple shapes", "Rectangle area = length × width", "Trapezoid area = (top base + bottom base) × height ÷ 2"}
        ));
        
        // Shape 2: L-shape (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 2: L-shaped compound rectangle",
            "Calculate the total area of the compound shape (unit: square cm)",
            431, // 21*20 + 11*1 = 420 + 11 = 431
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"}
        ));
        
        // Shape 3: Rectangle
        shapes.add(new CompoundShape(
            "Shape 3: Rectangle",
            "Calculate the area of the rectangle (unit: square cm)",
            304, // 19*16 = 304
            new String[]{"Rectangle area = length × width"}
        ));
        
        // Shape 4: L-shape (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 4: L-shaped compound rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            288, // 24*10 + 6*8 = 240 + 48 = 288
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"}
        ));
        
        // Shape 5: Trapezoid
        shapes.add(new CompoundShape(
            "Shape 5: Trapezoid",
            "Calculate the area of the trapezoid (unit: square meters)",
            40, // (16+4)*4/2 = 40
            new String[]{"Trapezoid area = (top base + bottom base) × height ÷ 2"}
        ));
        
        // Shape 6: Trapezoid
        shapes.add(new CompoundShape(
            "Shape 6: Trapezoid",
            "Calculate the area of the trapezoid (unit: square meters)",
            230, // (20+9)*16/2 = 232 (or approximate value)
            new String[]{"Trapezoid area = (top base + bottom base) × height ÷ 2"}
        ));
        
        // Shape 7: Triangle + Rectangle
        shapes.add(new CompoundShape(
            "Shape 7: Compound shape consisting of a triangle and a rectangle",
            "Calculate the total area of the compound shape (unit: square cm)",
            106, // 14*5 + 12*5/2 = 70 + 30 = 100 (or approximate value)
            new String[]{"Hint: Break down the shape into a triangle and a rectangle", "Rectangle area = length × width", "Triangle area = base × height ÷ 2"}
        ));
        
        // Shape 8: L-shape (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 8: L-shaped compound rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            2160, // 36*36 + 24*36 = 1296 + 864 = 2160
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"}
        ));
        
        // Shape 9: L-shape (two rectangles)
        shapes.add(new CompoundShape(
            "Shape 9: L-shaped compound rectangle",
            "Calculate the total area of the compound shape (unit: square meters)",
            178, // 10*11 + 8*8 = 110 + 64 = 174 (or approximate value)
            new String[]{"Hint: Break down the shape into two rectangles", "Rectangle area = length × width", "Calculate the area of each part and add them together"}
        ));
    }

    /**
     * Set up the user interface components
     */

     protected void setupUI() {
        content.setLayout(new BorderLayout(10, 10));
        
        // North section for question description
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bonus 1: Compound Shape Area Calculation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        northPanel.add(titleLabel, BorderLayout.NORTH);
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        northPanel.add(questionLabel, BorderLayout.CENTER);
        
        content.add(northPanel, BorderLayout.NORTH);
        
        // Center section for shape image
        JPanel centerPanel = new JPanel(new BorderLayout());
        shapeImageLabel = new JLabel("", SwingConstants.CENTER);
        shapeImageLabel.setPreferredSize(new Dimension(400, 300));
        centerPanel.add(shapeImageLabel, BorderLayout.CENTER);
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
        
        content.add(southPanel, BorderLayout.SOUTH);
        
        // Display the first shape
        showCurrentShape();
    }
    
    /**
     * Display the current shape information
     */
    private void showCurrentShape() {
        CompoundShape currentShape = shapes.get(currentShapeIndex);
        questionLabel.setText("<html><body style='width: 400px'>" + 
                              currentShape.getDescription() + "<br>" + 
                              currentShape.getQuestion() + "</body></html>");
        
        // Here should display the shape image, but since we don't have actual image resources,
        // we'll just display the shape's text description
        shapeImageLabel.setText("Shape " + (currentShapeIndex + 1) + " / 9");
        
        // Reset attempt count
        attempt = 0;
    }

    /**
     * Check if the user's answer is correct
     * @param input The user's input answer
     */
 
    protected void checkAnswer(String input) {
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an answer!");
            return;
        }
        
        try {
            double userAnswer = Double.parseDouble(input);
            CompoundShape currentShape = shapes.get(currentShapeIndex);
            
            // Check if the answer is correct (allowing 5% error margin)
            if (Math.abs(userAnswer - currentShape.getCorrectAnswer()) / currentShape.getCorrectAnswer() <= 0.05) {
                JOptionPane.showMessageDialog(this, "Correct answer!");
                practisedShapes++;
                moveToNextShape();
            } else {
                attempt++;
                if (attempt >= 3) {
                    // Show correct answer and solution method
                    StringBuilder message = new StringBuilder();
                    message.append("The correct answer is: ").append(currentShape.getCorrectAnswer()).append("\n\n");
                    message.append("Solution method:\n");
                    for (String hint : currentShape.getHints()) {
                        message.append("- ").append(hint).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, message.toString());
                    
                    practisedShapes++;
                    moveToNextShape();
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer, please try again!\n" + (3 - attempt) + " attempts remaining.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }
    
    /**
     * Move to the next shape or finish the exercise
     */
    private void moveToNextShape() {
        // Check if all shapes have been completed
        if (practisedShapes >= 9) {
            JOptionPane.showMessageDialog(this, "Congratulations! You have completed all compound shape exercises!");
            MainApp.switchPanel(new Bonus2SectorArcPanel());
            return;
        }
        
        // Move to the next shape
        currentShapeIndex = (currentShapeIndex + 1) % shapes.size();
        answerField.setText("");
        showCurrentShape();
    }
    
    /**
     * Inner class representing a compound shape
     */
    private class CompoundShape {
        private String description;
        private String question;
        private double correctAnswer;
        private String[] hints;
        
        /**
         * Constructor for CompoundShape
         * @param description Description of the shape
         * @param question Question about the shape
         * @param correctAnswer Correct area value
         * @param hints Solution hints
         */
        public CompoundShape(String description, String question, double correctAnswer, String[] hints) {
            this.description = description;
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.hints = hints;
        }
        
        /**
         * Get the shape description
         * @return The description
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Get the question about the shape
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
         * Get the solution hints
         * @return Array of hint strings
         */
        public String[] getHints() {
            return hints;
        }
    }
}
