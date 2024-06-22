# FlappyBirdPlus

![Icon](/src/resources/images/icon.png) </br>
FlappyBirdPlus 是一个简单且令人上瘾的游戏，灵感来自原始的 Flappy Bird。本项目包括一个用于玩游戏的客户端应用程序和一个用于管理排行榜的服务器应用程序。

## 功能

- **客户端 (FlappyBirdPlus)**: 提供直观的控制和有趣的游戏玩法。
- **服务器 (FlappyBirdPlusServer)**: 管理排行榜、存储玩家分数并处理玩家数据。

## 要求

### 通用要求
- Java 开发工具包 (JDK) 17 或更高版本

### 客户端
- Swing (用于图形界面)

### 服务器
- SQLite (用于数据库)
- Google Gson (用于 JSON 解析)
- om.sun.net.httpserver.HttpServer (用于 HTTP 服务器)

## 构建

### 客户端构建

1. 克隆代码库：
   ```bash
   git clone https://github.com/yourusername/FlappyBirdPlus.git
   cd FlappyBirdPlus
   ```

2. 使用 `IDEA IntelliJ` 构建项目：
   ```bash
   idea ./
   ```
   `构建` > `构建工件...` > `FlappyBirdPlus:jar`

### 服务器构建

1. 进入服务器代码目录：
   ```bash
   cd FlappyBirdPlusServer
   ```

2. 使用 `IDEA IntelliJ` 构建项目：
   ```bash
   idea ./
   ```
   `构建` > `构建工件...` > `FlappyBirdPlusServer:jar`

## 运行

### 运行客户端

1. 进入客户端目标目录：
   ```bash
   cd out/artifacts/FlappyBirdPlus_jar/
   ```

2. 运行客户端应用程序：
   ```bash
   java -jar FlappyBirdPlus.jar
   ```

3. 以测试模式运行客户端应用程序：
   ```bash
   java -jar FlappyBirdPlus.jar test
   ```

### 运行服务器

1. 进入服务器目标目录：
   ```bash
   cd out/artifacts/FlappyBirdPlusServer_jar/
   ```

2. 运行服务器应用程序：
   ```bash
   java -jar FlappyBirdPlusServer.jar
   ```

3. 服务器启动后，您可以通过浏览器或其他 HTTP 客户端访问以下端点：
    - `http://localhost:8000/addScore` 添加分数
    - `http://localhost:8000/getTopUsers` 获取排行榜前十名
    - `http://localhost:8000/getUserScore` 获取某个用户的分数
    - `http://localhost:8000/removeScore` 删除某个用户的分数

## 数据库
### 类型
`Sqlite`
### 结构

1. **表 `sqlite_master`**:
   - 该表是SQLite系统表，用于存储数据库中的元数据。
   - **列**：
      - `type`：类型为TEXT，表示数据库对象的类型（例如，table, index, view等）。
      - `name`：类型为TEXT，表示对象的名称。
      - `tbl_name`：类型为TEXT，表示对象所属的表名。
      - `rootpage`：类型为INT，表示对象的root page。
      - `sql`：类型为TEXT，表示创建对象的SQL语句。

2. **表 `USER`**:
   - 这是一个用户自定义表，用于存储用户信息。
   - **列**：
      - `NAME`：类型为TEXT，表示用户的名称。此列作为主键。
      - `SCORE`：类型为INTEGER，表示用户的分数。
   - **键**：
      - `key #1`：主键为 `NAME` 列。
   - **索引**：
      - `sqlite_autoindex_USER_1`：这是自动创建的唯一索引，针对 `NAME` 列，确保其唯一性。

## 关于

- **作者**: 古佳乐
- **学号**: 8008123201
- **班级**: 计算机Ⅱ类2307班
- **版本**: 1.0
- **完成时间**: 2024年6月16日