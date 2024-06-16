/**
* @Description 游戏控制器 
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
* {@code @Description} 游戏控制器类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public class Controller {

    // 游戏控制器所控制的组件
    private Player player;
    private final Queue<Wall> walls = new LinkedList<>();
    private final Background background = new Background(new Vec(0, 0), new Dimension(Board.boardWidth, Board.boardHeight), new Vec(Controller.WALL_SPEED, 0), new Vec(0, 0));

    // 定义游戏场景大小
    public static final int TOP = -200; // 为了游戏体验为上方留了预留量，会影响到组件渲染
    public static final int BOTTOM = Board.boardHeight;
    public static final int LEFT = 0;
    public static final int RIGHT = Board.boardWidth;

    // 定义墙的移动速度，背景的速度也基于这个值
    public static final int WALL_SPEED = -5;

    // 设置玩家初始状态
    private final int initialPlayX = 200;
    private final int initialPlayY = (TOP+BOTTOM)/2;
    private final double initialVelocityX = 0;
    private final double initialVelocityY = 0;
    private final double initialAccelerationX = 0;
    private final double initialAccelerationY = 0.5;

    // 实例化储存玩家信息的相关对象
    private final Vec PlayerPosition = new Vec(initialPlayX, initialPlayY);
    private final Vec PlayerVelocity = new Vec(initialVelocityX, initialVelocityY);
    private final Vec PlayerAcceleration = new Vec(initialAccelerationX, initialAccelerationY);

    // 游戏状态
    private static boolean isGaming = false;
    private static boolean isGameOver = false;
    
    // 最后一次渲染的墙，用于墙的生成
    private Wall lastWall = null;

    // 游戏失败时的音效
    private Clip gameoverClip;

    // 用于与客户端交互
    private final Client client;
    
    /**
    * {@code @Description} 构造函数，初始化客户端和游戏资源 
    * {@code @Param}  
    * {@code @return}  
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    public Controller(Client client) {
        this.client = client;
        // 游戏失败的音效在这里初始化，玩家相关的在其他地方
        loadGameoverClip();
        Wall.loadImage();
    }

    /**
    * {@code @Description} 加载游戏失败的音效 
    * {@code @Param}  
    * {@code @return}  
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    public void loadGameoverClip() {
        System.out.println("Loading game over clip");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/resources/sounds/gameover.wav")));
            gameoverClip = AudioSystem.getClip();
            gameoverClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * {@code @Description} 获取游戏是否处于失败状态 
    * {@code @Param} void 
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    public static boolean getIsGameOver() {
        return isGameOver;
    }

    /**
    * {@code @Description} 获取背景组件 
    * {@code @Param} void 
    * {@code @return} Background 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    public Background getBackground() {
        return background;
    }

    /**
    * {@code @Description} 获取游戏是否处于进行状态 
    * {@code @Param} void 
    * {@code @return} boolean 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public static boolean getIsGaming() {
        return isGaming;
    }

    /**
    * {@code @Description} 获取位置显示组件 
    * {@code @Param} void 
    * {@code @return} LocationText 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    // 必须先执行 getPlayer 才能执行此方法
    public LocationText getLocationText() {
        return new LocationText(new Vec(10, 20), 20, player);
    }

    /**
    * {@code @Description} 获取玩家组件 
    * {@code @Param} void 
    * {@code @return} Player 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public Player getPlayer() {
        player = new Player(PlayerPosition, PlayerVelocity, PlayerAcceleration);
        return player;
    }

    /**
    * {@code @Description} 开始游戏 
    * {@code @Param} void 
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public void startGame() {
        // 如果游戏在进行中就返回
        if (isGaming) return;

        // 如果游戏处于失败状态就开始新游戏
        if (isGameOver) {
            requestNewGame();
        }

        // 使各组件开始运动
        walls.forEach(w->w.setIsPhysical(true));
        player.setIsPhysical(true);
        background.setIsPhysical(true);
        
        // 改变游戏状态
        isGaming = true;
    }

    /**
    * {@code @Description} 停止游戏 
    * {@code @Param} void 
    * {@code @return} void 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public void stopGame() {
        // 如果游戏在停止中就返回
        if (!isGaming) return;

        // 使各组件停止运动
        walls.forEach(w->w.setIsPhysical(false));
        player.setIsPhysical(false);
        background.setIsPhysical(false);

        // 改变游戏状态
        isGaming = false;
    }

    /**
    * {@code @Description} 开始新的游戏 
    * {@code @Param} void 
    * {@code @return} void 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public void requestNewGame() {
        // 改变游戏状态
        isGameOver = false;

        // 设置玩家为初始状态
        player.setPosition(initialPlayX, initialPlayY);
        player.setAcceleration(initialAccelerationX, initialAccelerationY);
        player.setVelocity(initialVelocityX, initialVelocityY);
        player.resetScore();

        // 清空墙队列
        while (!walls.isEmpty()) {
            walls.poll();
        }
        //设置最后的墙为空
        lastWall = null;

        // 重置背景位置
        background.position.x = 0;
    }
    
    /**
    * {@code @Description} 游戏失败 
    * {@code @Param} void 
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    private void gameOver() {
        // 播放游戏失败音效
        if (gameoverClip.isRunning()) {
            gameoverClip.stop();
        }
        gameoverClip.setFramePosition(0);
        gameoverClip.start();

        // 改变游戏状态
        isGameOver = true;
        // 停止游戏
        stopGame();

        // 判断本局游戏分数是否打破记录
        if (App.getUser().getScore()<player.getScore()) {
            App.getUser().setScore(player.getScore());
            client.saveToLocal(App.getUser());
            JOptionPane.showMessageDialog(null, "新的最高分记录，将上传成绩到排行榜");

            if (!client.addScore(App.getUser())) {
                JOptionPane.showMessageDialog(null, "上传失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
    * {@code @Description} 游戏控制器的 tick 方法 
    * {@code @Param} void 
    * {@code @return} void 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午3:19
    */
    public void tick() {
        if (isGaming) {
            // 判断游戏是否失败
            if (player.getBottomY() > BOTTOM || player.getPosition().y < TOP) {
                gameOver();
            }

            walls.forEach(w -> {
                if (player.isCollision(w)) {
                    gameOver();
                }

                if (player.getPosition().x > w.getPosition().x+w.getSize().width) {
                    if (!w.isScored()) {
                        player.addScore();
                        w.scored();
                    }
                }
            });

            // 建造和拆除墙
            buildWalls();
            destroyWalls();
        }
    }

    /**
    * {@code @Description} 建造墙
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午12:52
    */
    private void buildWalls() {
        if (lastWall != null) {
            if (lastWall.getPosition().x < RIGHT-500-lastWall.getSize().width) {
                buildSingleWall();
            }
        } else {
            buildSingleWall();
        }
    }

    /**
    * {@code @Description} 建造单对墙
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午12:52
    */
    private void buildSingleWall() {
        // 设置缝隙的大小
        int minGap = 150;
        int maxGap = 200;
        int gapHeight = minGap + (int)(Math.random() * (maxGap - minGap + 1));

        // 设置墙的高度
        int minWallHeight = 100;
        int maxWallHeight = Board.boardHeight - minGap - minWallHeight;
        int topWallHeight = minWallHeight / 2 + (int)(Math.random() * (maxWallHeight - minWallHeight + 1));
        int bottomWallHeight = Board.boardHeight - topWallHeight - gapHeight;

        // 设置墙的宽度
        int wallWidth = 128;

        // 更新 lastWall
        lastWall = Wall.createTopWall(topWallHeight, wallWidth);

        // 更新墙队列
        walls.add(lastWall);
        walls.add(Wall.createBottomWall(bottomWallHeight, wallWidth));
    }

    /**
    * {@code @Description} 拆除墙
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    private void destroyWalls() {
        if (walls.peek() != null) {
            // 超出屏幕外面就拆掉
            if (walls.peek().getPosition().x < LEFT-walls.element().getSize().width) {
                walls.remove();
            }
        }
    }

    /**
    * {@code @Description} 获取墙队列
    * {@code @Param} void
    * {@code @return} Queue<Wall>
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    public Queue<Wall> getWalls() {
        return walls;
    }
}
