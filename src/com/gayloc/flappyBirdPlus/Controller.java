package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Controller {

    private Player player;
    private final Queue<Wall> walls = new LinkedList<>();

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

    private final Dimension playerSize = new Dimension(50, 50);

    private Boolean isGaming = false;
    private Boolean isGameOver = false;
    private Wall lastWall = null;

    public Player getPlayer() {
        player = new Player(PlayerPosition, playerSize, PlayerVelocity, PlayerAcceleration);
        return player;
    }

    public void startGame() {
        if (isGaming) return;

        if (isGameOver) {
            requestNewGame();
        }

        player.setIsPhysical(true);
        isGaming = true;
    }

    public void stopGame() {
        if (!isGaming) return;
        walls.forEach(w->w.setIsPhysical(false));
        player.setIsPhysical(false);
        isGaming = false;
    }

    public void requestNewGame() {
        isGameOver = false;

        player.setPosition(initialPlayX, initialPlayY);
        player.setAcceleration(initialAccelerationX, initialAccelerationY);
        player.setVelocity(initialVelocityX, initialVelocityY);

        while (!walls.isEmpty()) {
            walls.poll();
        }
        lastWall = null;
    }

    public void tick() {
        if (isGaming) {
            if (player.getBottomY() > BOTTOM || player.getPosition().y < TOP) {
                isGameOver = true;
                stopGame();
            }

            walls.forEach(w -> {
                if (player.getPosition().x+player.getSize().width > w.getPosition().x && player.getPosition().x < w.getPosition().x+w.getSize().width) {
                    if (player.getBottomY() > w.getPosition().y && player.getPosition().y < w.getPosition().y+w.getSize().height) {
                        isGameOver = true;
                        stopGame();
                    }
                }
            });

            buildWalls();
            destroyWalls();
        }
    }

    private void buildWalls() {
        int topWallHeight = 250;
        int bottomWallHeight = 250;
        int topWallWidth = 150;
        int bottomWallWidth = 150;

        if (lastWall != null) {
            if (lastWall.getPosition().x < RIGHT-500-lastWall.getSize().width) {
                lastWall = Wall.createTopWall(topWallHeight, topWallWidth);
                walls.add(lastWall);
                walls.add(Wall.createBottomWall(bottomWallHeight, bottomWallWidth));
            }
        } else {
            lastWall = Wall.createTopWall(topWallHeight, topWallWidth);
            walls.add(lastWall);
            walls.add(Wall.createBottomWall(bottomWallHeight, bottomWallWidth));
        }
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
