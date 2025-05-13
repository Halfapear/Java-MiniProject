## 项目结构

```plaintext
com.shapeville
├── main                 // 主程序入口和主框架类
├── ui                   // 主框架UI组件 (如HomeScreen, NavigationBar) 和 通用UI工具
│   ├── core             // 核心UI组件，例如自定义按钮，通用对话框等 (可选)
│   └── panel_templates  // 任务面板的基类或接口 (如 TaskPanel)
├── model                // 共享数据模型 (Shape, Angle, UserProgress, etc.)
├── logic                // 核心业务逻辑 (TaskManager, ScoreManager, etc.)
├── tasks(我目前没动)                // 各个任务模块的包
│   ├── ks1
│   │   ├── identification  // Task 1: Shape Identification
│   │   └── angle         // Task 2: Angle Types
│   ├── ks2
│   │   ├── area          // Task 3: Area Calculation
│   │   └── circle        // Task 4: Circle Calculations
│   └── bonus
│       ├── compound      // Bonus 1: Compound Shapes
│       └── sector        // Bonus 2: Sector/Arc
├── assets               // 存放图片、声音等资源 (通常在 src/main/resources/assets)
└── utils                // 通用工具类 (ImageLoader, Validators, etc.)


.gitignore 文件已配置，忽略 .class 文件

架构使用maven 我的vscode source总会会出bug；你新建个文件应该会出现package com.shapeville.main;类似的内容 如果没有先看看有没有按照maven for java插件 再不行就call一下我

