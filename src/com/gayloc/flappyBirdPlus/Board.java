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

    public Board() {
        int boardWidth = 1280;
        int boardHeight = 720;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(new Color(232, 232, 232));

        player = controller.getPlayer();
        lt = controller.getLocationText();
        scoreText = new ScoreText(player);
        background = controller.getBackground();

        int DELAY = 5;

        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // prevent the player from disappearing off the board
        player.tick();
        lt.tick();
        walls.forEach(Wall::tick);
        scoreText.tick();
        controller.tick();
        background.tick();
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
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

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
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
        ;
    }
}