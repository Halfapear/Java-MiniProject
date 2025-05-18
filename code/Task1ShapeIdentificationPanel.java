package code;

import javax.swing.*;

public class Task1ShapeIdentificationPanel extends TaskPanel {
    private JTextField answerField;
    private JLabel shapeLabel;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        shapeLabel = new JLabel("🔷 What shape is this? (e.g., square)", SwingConstants.CENTER);
        answerField = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> checkAnswer(answerField.getText()));

        content.add(shapeLabel);
        content.add(answerField);
        content.add(submit);
    }

    @Override
    protected void checkAnswer(String input) {
        if (input.equalsIgnoreCase("square")) {
            JOptionPane.showMessageDialog(this, "Great job!");
            MainApp.switchPanel(new Task2AngleIdentificationPanel());
        } else {
            attempt++;
            if (attempt >= 3) {
                JOptionPane.showMessageDialog(this, "Correct answer: square");
                MainApp.switchPanel(new Task2AngleIdentificationPanel());
            }
        }
    }
}
