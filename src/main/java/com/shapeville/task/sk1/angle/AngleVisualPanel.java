import javax.swing.*;
import java.awt.*;

/**
 * 专门用于绘制角度视觉表示的面板
 */
class AngleVisualPanel extends JPanel {
    private int angle;

    public AngleVisualPanel() {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    public void setAngle(int angle) {
        this.angle = angle;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3; // 调整半径大小

        // 绘制圆弧
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 0, angle);

        // 绘制水平射线
        g2d.drawLine(centerX, centerY, centerX + radius * 2, centerY);

        // 绘制倾斜射线
        double angleRadians = Math.toRadians(angle);
        int endX = (int) (centerX + radius * 2 * Math.cos(angleRadians));
        int endY = (int) (centerY - radius * 2 * Math.sin(angleRadians));
        g2d.drawLine(centerX, centerY, endX, endY);

        // 绘制箭头
        drawArrow(g2d, centerX + radius * 2, centerY);
        drawArrow(g2d, endX, endY);
    }

    private void drawArrow(Graphics2D g2d, int x, int y) {
        int arrowSize = 10;
        double angle = Math.atan2(y - getHeight() / 2, x - getWidth() / 2);
        int arrowX1 = (int) (x - arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY1 = (int) (y - arrowSize * Math.sin(angle + Math.PI / 6));
        int arrowX2 = (int) (x - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY2 = (int) (y - arrowSize * Math.sin(angle - Math.PI / 6));
        g2d.fillPolygon(new int[]{x, arrowX1, arrowX2}, new int[]{y, arrowY1, arrowY2}, 3);
    }
}