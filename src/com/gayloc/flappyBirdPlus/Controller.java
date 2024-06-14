package com.gayloc.flappyBirdPlus;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Controller {

    private Player player;
    private final Queue<Wall> walls = new LinkedList<>();
    private final Background background = new Background(new Vec(0, 0), new Dimension(Board.boardWidth, Board.boardHeight), new Vec(Controller.WALL_SPEED, 0), new Vec(0, 0));

    public static final int TOP = -200;
    public static final int BOTTOM = Board.boardHeight;
    public static final int LEFT = 0;
    public static final int RIGHT = Board.boardWidth;

    public static final int WALL_SPEED = -5;

    private final int initialPlayX = 200;
    private final int initialPlayY = (TOP+BOTTOM)/2;
    private final double initialVelocityX = 0;
    private final double initialVelocityY = 0;
    private final double initialAccelerationX = 0;
    private final double initialAccelerationY = 0.5;

    private final Vec PlayerPosition = new Vec(initialPlayX, initialPlayY);
    private final Vec PlayerVelocity = new Vec(initialVelocityX, initialVelocityY);
    private final Vec PlayerAcceleration = new Vec(initialAccelerationX, initialAccelerationY);

    private static Boolean isGaming = false;
    private static Boolean isGameOver = false;
    private Wall lastWall = null;

    private Clip gameoverClip;
    
    public Controller() {
        loadGameoverClip();
    }

    public void loadGameoverClip() {
        System.out.println("Loading game over clip");
        try {
            File soundFile = new File("src/resources/sounds/gameover.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            gameoverClip = AudioSystem.getClip();
            gameoverClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean getIsGameOver() {
        return isGameOver;
    }

    public Background getBackground() {
        return background;
    }

    public static Boolean getIsGaming() {
        return isGaming;
    }

    //必须先执行 getPlayer 才能执行此方法
    public LocationText getLocationText() {
        return new LocationText(new Vec(10, 20), 20, player);
    }

    public Player getPlayer() {
        player = new Player(PlayerPosition, PlayerVelocity, PlayerAcceleration);
        return player;
    }

    public void startGame() {
        if (isGaming) return;

        if (isGameOver) {
            requestNewGame();
        }

        walls.forEach(w->w.setIsPhysical(true));
        player.setIsPhysical(true);
        background.setIsPhysical(true);
        isGaming = true;
    }

    public void stopGame() {
        if (!isGaming) return;

        walls.forEach(w->w.setIsPhysical(false));
        player.setIsPhysical(false);
        background.setIsPhysical(false);
        isGaming = false;
    }

    public void requestNewGame() {
        isGameOver = false;

        player.setPosition(initialPlayX, initialPlayY);
        player.setAcceleration(initialAccelerationX, initialAccelerationY);
        player.setVelocity(initialVelocityX, initialVelocityY);
        player.resetScore();

        while (!walls.isEmpty()) {
            walls.poll();
        }
        lastWall = null;

        background.position.x = 0;
    }
    
    private void gameOver() {
        if (gameoverClip.isRunning()) {
            gameoverClip.stop();
        }
        gameoverClip.setFramePosition(0);
        gameoverClip.start();
        isGameOver = true;
        stopGame();
    }

    public void tick() {
        if (isGaming) {
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

            buildWalls();
            destroyWalls();
        }
    }

    private void buildWalls() {
        if (lastWall != null) {
            if (lastWall.getPosition().x < RIGHT-500-lastWall.getSize().width) {
                buildSingleWall();
            }
        } else {
            buildSingleWall();
        }
    }

    private void buildSingleWall() {
        int topWallHeight = 250;
        int bottomWallHeight = 250;
        int topWallWidth = 128;
        int bottomWallWidth = 128;

        lastWall = Wall.createTopWall(topWallHeight, topWallWidth);
        walls.add(lastWall);
        walls.add(Wall.createBottomWall(bottomWallHeight, bottomWallWidth));
    }

    private void destroyWalls() {
        if (walls.peek() != null) {
            if (walls.peek().getPosition().x < LEFT-walls.element().getSize().width) {
                walls.remove();
            }
        }
    }

    public Queue<Wall> getWalls() {
        return walls;
    }
}
