# Java-MiniProject
# project structure & functions
Shapeville/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── shapeville/
│   │   │   │   ├── app/                # 主应用类
│   │   │   │   │   ├── Main.java
│   │   │   │   │   ├── ShapevilleApp.java
│   │   │   │   │   └── ScoreManager.java
│   │   │   │   ├── tasks/               # 任务实现
│   │   │   │   │   ├── task1/          # 任务1：形状识别
│   │   │   │   │   │   ├── Shape2D.java
│   │   │   │   │   │   ├── Shape3D.java
│   │   │   │   │   │   ├── ShapeIdentification.java
│   │   │   │   │   │   └── ShapesData.java
│   │   │   │   │   ├── task2/          # 任务2：角度识别
│   │   │   │   │   │   ├── AngleIdentification.java
│   │   │   │   │   │   ├── AngleType.java
│   │   │   │   │   │   └── AngleVisualizer.java
│   │   │   │   ├── ui/                 # UI组件
│   │   │   │   │   ├── components/     # 可复用UI组件
│   │   │   │   │   ├── screens/        # 屏幕控制器
│   │   │   │   │   └── theme/         # CSS主题
│   │   │   │   ├── utils/              # 工具类
│   │   │   │   └── model/              # 数据模型
│   │   │   └── module-info.java
│   │   └── resources/                  # 资源文件
│   │       ├── images/                 # 图片资源
│   │       │   ├── shapes/             # 形状图片
│   │       │   └── angles/             # 角度示意图
│   │       ├── css/                    # 样式表
│   │       │   └── style.css
│   │       └── fxml/                   # FXML界面文件
│   │           ├── task1/              # 任务1界面
│   │           │   ├── shape2d_screen.fxml
│   │           │   ├── shape3d_screen.fxml
│   │           │   └── shape_identification.fxml
│   │           ├── task2/              # 任务2界面
│   │           │   ├── angle_identification.fxml
│   │           │   └── angle_visualization.fxml
│   │           └── main/               # 主界面
│   │               ├── home_screen.fxml
│   │               └── progress_screen.fxml
│   ├── test/                           # 测试代码
│   │   └── java/
│   │       └── shapeville/
│   │           ├── task1/              # 任务1测试
│   │           └── task2/              # 任务2测试
└── build.gradle                        # 构建配置