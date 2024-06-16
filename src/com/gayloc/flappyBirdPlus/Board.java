/**
* @Description 游戏画面组件
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* {@code @Description} 游戏画面组件
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public class Board extends JPanel implements ActionListener, KeyListener, MouseListener {

    // 储存游戏控制器
    private final Controller controller;

    // 游戏场景大小，窗口大小也基于这个数值，理论上支持所有主流分辨率显示器
    public static final int boardWidth = 1280;
    public static final int boardHeight = 720;

    // 所有游戏中会出现的组件
    private final Player player;
    private final LocationText lt;
    private final java.util.Queue<Wall> walls;
    private final ScoreText scoreText;
    private final Background background;
    private final HintText hintText;
    // 特殊的情况，需要等游戏初始化完成后才能加载这个组件
    private static BestScoreText bestScoreText = null;

    /**
    * {@code @Description} 获取最佳记录组件，用手更新用户信息
    * {@code @Param} void
    * {@code @return} BestScoreText
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午9:08
    */
    public static BestScoreText getBestScoreText() {
        return bestScoreText;
    }

    /**
    * {@code @Description} 构造函数
    * {@code @Param} Client c
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午3:19
    */
    public Board(Client c) {
        // 创建游戏控制器
        controller = new Controller(c);

        // 获取游戏中出现的墙
        walls = controller.getWalls();

        // 用于窗口自动设置大小
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // 默认背景色，理论上看不见，调试用
        setBackground(new Color(232, 232, 232));

        // 初始化所有游戏组件
        player = controller.getPlayer();
        lt = controller.getLocationText();
        scoreText = new ScoreText(player);
        background = controller.getBackground();
        hintText = new HintText();
        bestScoreText = new BestScoreText(App.getUser());

        // 设置 tick 的时长
        int DELAY = 5;

        // 设置计时器
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
    * {@code @Description} 计时器函数，每个 tick 触发一次
    * {@code @Param} ActionEvent e
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午3:19
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        // 每个组件各自执行 tick 方法
        player.tick();
        lt.tick();
        walls.forEach(Wall::tick);
        scoreText.tick();
        controller.tick();
        background.tick();
        hintText.tick();
        bestScoreText.tick();

        // 重新绘制画面，会触发 paintComponent 方法
        repaint();
    }

    /**
    * {@code @Description} 绘制组件的方法
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午3:19
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 每个组件各自执行各自的 render 方法
        background.render(g);
        player.render(g);
        lt.render(g);
        walls.forEach(w -> w.render(g));
        scoreText.render(g);
        hintText.render(g);
        bestScoreText.render(g);

        /*
         * Toolkit.getDefaultToolkit().sync() 这行代码的作用是在某些系统上同步图形输出，确保绘制的内容立即显示，特别是在使用双缓冲机制时。它可以减少图形卡和显示器之间的延迟，提高动画的流畅性。以下是一个详细的解释：
         * 详细解释
         * 双缓冲机制：
         * 在图形绘制中，双缓冲是一种常见的技术，用于避免屏幕上显示部分绘制过程，从而防止图像闪烁。它通过在后台缓冲区（或图像缓冲区）中进行所有的绘制操作，然后一次性将该缓冲区的内容拷贝到前台缓冲区（或屏幕）。
         * 同步问题：
         * 尽管双缓冲机制减少了图像闪烁，但在某些系统上，图形更新和显示设备之间的同步可能仍然存在问题。这种不同步可能导致动画不流畅或出现撕裂效果。
         * Toolkit.getDefaultToolkit().sync() 的作用：
         * 这行代码强制将所有挂起的绘制命令发送到显示设备，并等待其完成。这确保了在调用 sync() 之后，所有的绘制操作都已实际显示出来。这样做可以提高动画的平滑度，尤其是在某些特定的操作系统或图形硬件环境中。
         */
        Toolkit.getDefaultToolkit().sync();
    }

    /**
    * {@code @Description} 重写 KeyEventListener 中的方法
    * {@code @Param} KeyEvent e
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午3:19
    */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * {@code @Description} 重写 KeyEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 点击任意键开始游戏或跳跃
        if (Controller.getIsGaming()) {
            player.jump();
        } else {
            controller.startGame();
        }
    }

    /**
     * {@code @Description} 重写 KeyEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * {@code @Description} 重写 MouseEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    /**
     * {@code @Description} 重写 MouseEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // 点击屏幕开始游戏或跳跃
        if (Controller.getIsGaming()) {
            player.jump();
        } else {
            controller.startGame();
        }
    }

    /**
     * {@code @Description} 重写 MouseEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * {@code @Description} 重写 MouseEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * {@code @Description} 重写 MouseEventListener 中的方法
     * {@code @Param} KeyEvent e
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午3:19
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}