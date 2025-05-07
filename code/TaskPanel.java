package code;

import javax.swing.*;
import java.awt.*;

public abstract class TaskPanel extends JPanel {
    protected NavigationPanel nav;
    protected JPanel content;

    public TaskPanel() {
        setLayout(new BorderLayout());
        nav = new NavigationPanel();
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        add(nav, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        setupUI();
    }

    protected abstract void setupUI();
    protected abstract void checkAnswer(String input);
}
