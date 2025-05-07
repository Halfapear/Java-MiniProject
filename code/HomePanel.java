package code;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new BorderLayout());
        add(new NavigationPanel(), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3, 2, 10, 10));

        JButton t1 = new JButton("Task 1: Identify Shapes");
        t1.addActionListener(e -> MainApp.switchPanel(new Task1ShapeIdentificationPanel()));

        JButton t2 = new JButton("Task 2: Identify Angles");
        t2.addActionListener(e -> MainApp.switchPanel(new Task2AngleIdentificationPanel()));

        JButton t3 = new JButton("Task 3: Area Calculation");
        t3.addActionListener(e -> MainApp.switchPanel(new Task3AreaCalculationPanel()));

        JButton t4 = new JButton("Task 4: Circle Area & Circumference");
        t4.addActionListener(e -> MainApp.switchPanel(new Task4CircleCalculationPanel()));

        JButton b1 = new JButton("Bonus 1: Compound Area");
        b1.addActionListener(e -> MainApp.switchPanel(new Bonus1CompoundAreaPanel()));

        JButton b2 = new JButton("Bonus 2: Sector & Arc");
        b2.addActionListener(e -> MainApp.switchPanel(new Bonus2SectorArcPanel()));

        center.add(t1); center.add(t2);
        center.add(t3); center.add(t4);
        center.add(b1); center.add(b2);

        add(center, BorderLayout.CENTER);
    }
}
