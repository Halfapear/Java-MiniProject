package code;

import javax.swing.*;

public class Task4CircleCalculationPanel extends TaskPanel {
    private int radius = 2;
    private JTextField answerField;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        JLabel question = new JLabel("⚪ Radius = 2. What is the area? (π=3.14)");
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
            double ans = Double.parseDouble(input);
            if (Utils.isClose(ans, 3.14 * 4)) {
                JOptionPane.showMessageDialog(this, "Great job!");
                MainApp.switchPanel(new Bonus1CompoundAreaPanel());
            } else {
                attempt++;
                if (attempt >= 3) {
                    JOptionPane.showMessageDialog(this, "Correct answer: 12.56");
                    MainApp.switchPanel(new Bonus1CompoundAreaPanel());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
        }
    }
}
