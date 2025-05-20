import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 形状任务类 - 管理形状识别任务的逻辑
 */
class ShapeTask {
    private String[] shapes2D = {"circle", "rectangle", "triangle", "oval", "octagon", "square", 
                               "heptagon", "rhombus", "pentagon", "hexagon", "kite"};
    private String[] shapes3D = {"cube", "cuboid", "cylinder", "sphere", "triangular prism", 
                               "square-based pyramid", "cone", "tetrahedron"};
    
    private String[] currentShapes;
    private int attemptsLeft = 3;
    private int currentPoints = 0;
    private int totalShapes = 4; // 每个任务显示4个形状
    private int shapesCompleted = 0;
    private String correctAnswer;
    private boolean is3D;
    private List<Integer> shapeIndices; // 用于跟踪已使用的形状索引
    private int currentShapeIndex = -1; // 当前形状的索引
    
    public ShapeTask(boolean is3D) {
        this.is3D = is3D;
        currentShapes = is3D ? shapes3D : shapes2D;
        initializeShapeIndices();
    }
    
    /**
     * 初始化形状索引列表并打乱顺序
     */
    private void initializeShapeIndices() {
        shapeIndices = new ArrayList<>();
        for (int i = 0; i < currentShapes.length; i++) {
            shapeIndices.add(i);
        }
        Collections.shuffle(shapeIndices, new Random());
    }
    
    /**
     * 获取随机形状图片路径
     */
    public String getRandomShape() {
        // 如果是新形状或所有形状都已使用，选择下一个形状
        if (currentShapeIndex == -1 || shapesCompleted >= totalShapes) {
            if (shapesCompleted >= totalShapes) {
                // 如果已经完成了所有形状，重新打乱顺序
                initializeShapeIndices();
                shapesCompleted = 0;
            }
            
            currentShapeIndex = shapeIndices.get(shapesCompleted);
            correctAnswer = currentShapes[currentShapeIndex];
            attemptsLeft = 3; // 重置尝试次数
            currentPoints = 0;
        }
        
        return "assets/" + (is3D ? "3d/" : "2d/") + correctAnswer + ".png";
    }
    
    /**
     * 验证用户答案
     */
    public boolean validateAnswer(String userInput) {
        if (attemptsLeft <= 0) {
            return false;
        }
        
        attemptsLeft--;
        
        // 如果答案正确
        if (userInput.equalsIgnoreCase(correctAnswer)) {
            // 根据尝试次数计算得分
            currentPoints = attemptsLeft == 2 ? 3 : attemptsLeft == 1 ? 2 : 1;
            shapesCompleted++;
            currentShapeIndex = -1; // 标记为需要选择新形状
            return true;
        }
        
        // 如果尝试次数用完
        if (attemptsLeft == 0) {
            shapesCompleted++;
            currentShapeIndex = -1; // 标记为需要选择新形状
            currentPoints = 0; // 尝试用完不得分
        }
        
        return false;
    }
    
    /**
     * 获取当前形状的正确答案
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    /**
     * 获取剩余尝试次数
     */
    public int getAttemptsLeft() {
        return attemptsLeft;
    }
    
    /**
     * 获取当前形状的得分
     */
    public int getCurrentPoints() {
        return currentPoints;
    }
    
    /**
     * 检查任务是否完成
     */
    public boolean isTaskComplete() {
        return shapesCompleted >= totalShapes;
    }
}