package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Controller {

    private Player player;
    private final Queue<Wall> walls = new LinkedList<>();

    public static final int TOP = -200;
    public static final int BOTTOM = App.HEIGHT+80;
    public static final int LEFT = 0;
    public static final int RIGHT = App.WIDTH;

    private final int initialPlayX = 100;
    private final int initialPlayY = 100;
    private final double initialVelocityX = 0;
    private final double initialVelocityY = 0;
    private final double initialAccelerationX = 0;
    private final double initialAccelerationY = 0.5;

    private final Vec PlayerPosition = new Vec(initialPlayX, initialPlayY);
    private final Vec PlayerVelocity = new Vec(initialVelocityX, initialVelocityY);
    private final Vec PlayerAcceleration = new Vec(initialAccelerationX, initialAccelerationY);

    private final Dimension playerSize = new Dimension(50, 50);

    private Boolean isGaming = false;
    private Wall lastWall = null;

    public Player getPlayer() {
        player = new Player(PlayerPosition, playerSize, PlayerVelocity, PlayerAcceleration);
        startGame();
        return player;
    }

    public void startGame() {
        player.setIsPhysical(true);
        isGaming = true;
    }

    public void stopGame() {
        player.setIsPhysical(false);
        isGaming = false;
    }

    public void requestNewGame() {
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
                requestNewGame();
            }

            buildWalls();
            destroyWalls();
        }
    }

    private void buildWalls() {
        int topWallHeight = 270;
        int bottomWallHeight = 270;

        if (lastWall != null) {
            if (lastWall.getPosition().x < RIGHT-500) {
                lastWall = Wall.createTopWall(topWallHeight);
                walls.add(lastWall);
                walls.add(Wall.createBottomWall(bottomWallHeight));
            }
        } else {
            lastWall = Wall.createTopWall(topWallHeight);
            walls.add(lastWall);
            walls.add(Wall.createBottomWall(bottomWallHeight));
        }
    }

    private void destroyWalls() {
        if (walls.peek() != null) {
            if (walls.peek().getPosition().x < LEFT-150) {
                walls.remove();
            }
        }
    }

    public Queue<Wall> getWalls() {
        return walls;
    }
}
