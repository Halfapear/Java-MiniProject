package code;

import javax.swing.*;

public class Task3AreaCalculationPanel extends TaskPanel {
    private int length = 5, width = 4;
    private JTextField answerField;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        JLabel question = new JLabel("📐 Rectangle: length = 5, width = 4. What is the area?");
        answerField = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> checkAnswer(answerField.getText()));

        content.add(question);
        content.add(answerField);
        content.add(submit);
    }

    @Override
    protected void checkAnswer(String input) {
        try {
            int ans = Integer.parseInt(input);
            if (ans == 20) {
                JOptionPane.showMessageDialog(this, "Great job!");
                MainApp.switchPanel(new Task4CircleCalculationPanel());
            } else {
                attempt++;
                if (attempt >= 3) {
                    JOptionPane.showMessageDialog(this, "Correct answer: 20");
                    MainApp.switchPanel(new Task4CircleCalculationPanel());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
        }
    }
}
