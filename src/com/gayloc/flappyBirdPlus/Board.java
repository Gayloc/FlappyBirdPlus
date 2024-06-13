package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final Controller controller = new Controller();

    private final Player player;
    private final LocationText lt;
    private final java.util.Queue<Wall> walls = controller.getWalls();

    public Board() {
        int boardWidth = App.WIDTH;
        int boardHeight = App.HEIGHT;
        setSize(boardWidth, boardHeight);
        setBackground(new Color(232, 232, 232));

        player = controller.getPlayer();
        lt = new LocationText(new Vec(0,20 ), 100, player);

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

        controller.tick();
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw our graphics.
        drawBackground(g);
        player.render(g);
        lt.render(g);
        walls.forEach(w -> w.render(g));

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
    }
}