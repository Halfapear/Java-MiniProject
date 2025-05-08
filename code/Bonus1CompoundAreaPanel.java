package code;

import javax.swing.*;

public class Bonus1CompoundAreaPanel extends TaskPanel {
    private JTextField answerField;
    private int attempt = 0;

    @Override
    protected void setupUI() {
        JLabel question = new JLabel("🧩 Shape made of two rectangles: 3x2 and 4x1. Total area?");
        answerField = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> checkAnswer(answerField.getText()));

        content.add(question);
        content.add(answerField);
        content.add(submit);
    }

    @Override
    protected void checkAnswer(String input) {
        if (input.equals("10")) {
            JOptionPane.showMessageDialog(this, "Awesome!");
            MainApp.switchPanel(new Bonus2SectorArcPanel());
        } else {
            attempt++;
            if (attempt >= 3) {
                JOptionPane.showMessageDialog(this, "Correct: 3*2 + 4*1 = 10");
                MainApp.switchPanel(new Bonus2SectorArcPanel());
            }
        }
    }
}
