import javax.swing.*;

/**
 * 主应用程序 - 管理面板切换
 */
public class AngelRecognitionApp extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    public AngelRecognitionApp() {
        setTitle("Shapeville - Task 2: Angle Recognition");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 显示角度识别任务面板
        AngleRecognitionPanel taskPanel = new AngleRecognitionPanel();
        add(taskPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AngelRecognitionApp().setVisible(true));
    }
}