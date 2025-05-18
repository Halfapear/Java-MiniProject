package code;

import javax.swing.*;

public class Task2AngleIdentificationPanel extends TaskPanel {
    private JTextField answerField;
    private JLabel angleLabel;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        angleLabel = new JLabel("🎯 What type of angle is 90°?", SwingConstants.CENTER);
        answerField = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> checkAnswer(answerField.getText()));

        content.add(angleLabel);
        content.add(answerField);
        content.add(submit);
    }

    @Override
    protected void checkAnswer(String input) {
        if (input.equalsIgnoreCase("right")) {
            JOptionPane.showMessageDialog(this, "Great job!");
            MainApp.switchPanel(new Task3AreaCalculationPanel());
        } else {
            attempt++;
            if (attempt >= 3) {
                JOptionPane.showMessageDialog(this, "Correct answer: right angle");
                MainApp.switchPanel(new Task3AreaCalculationPanel());
            }
        }
    }
}
