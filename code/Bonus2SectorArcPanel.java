package code;

import javax.swing.*;

public class Bonus2SectorArcPanel extends TaskPanel {
    private JTextField answerField;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        JLabel question = new JLabel("📐 Sector: radius = 3, angle = 60°. Arc length?");
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
            double expected = (60 / 360.0) * 2 * Math.PI * 3;
            if (Utils.isClose(ans, expected)) {
                JOptionPane.showMessageDialog(this, "Well done!");
                MainApp.switchPanel(new EndPanel());
            } else {
                attempt++;
                if (attempt >= 3) {
                    JOptionPane.showMessageDialog(this, "Correct answer: " + expected);
                    MainApp.switchPanel(new EndPanel());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
        }
    }
}
