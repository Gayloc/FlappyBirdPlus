package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener, MouseListener {

    private final Controller controller = new Controller();

    public static final int boardWidth = 1280;
    public static final int boardHeight = 720;

    private final Player player;
    private final LocationText lt;
    private final java.util.Queue<Wall> walls = controller.getWalls();
    private final ScoreText scoreText;
    private final Background background;
    private final HintText hintText;

    public Board() {

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(new Color(232, 232, 232));

        player = controller.getPlayer();
        lt = controller.getLocationText();
        scoreText = new ScoreText(player);
        background = controller.getBackground();
        hintText = new HintText();

        int DELAY = 5;

        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        player.tick();
        lt.tick();
        walls.forEach(Wall::tick);
        scoreText.tick();
        controller.tick();
        background.tick();
        hintText.tick();

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        background.render(g);
        player.render(g);
        lt.render(g);
        walls.forEach(w -> w.render(g));
        scoreText.render(g);
        hintText.render(g);

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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Controller.getIsGaming()) {
            player.jump();
        } else {
            controller.startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (Controller.getIsGaming()) {
            player.jump();
        } else {
            controller.startGame();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}