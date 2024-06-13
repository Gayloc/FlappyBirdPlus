package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Controller {
    private Player player;

    private final int TOP = -300;
    private final int BOTTOM = App.HEIGHT+80;

    private final int initialPlayX = 100;
    private final int initialPlayY = 100;
    private final double initialVelocityX = 0;
    private final double initialVelocityY = 0;
    private final double initialAccelerationX = 0;
    private final double initialAccelerationY = 0.5;

    private final Position PlayerPosition = new Position(initialPlayX, initialPlayY);
    private final Position PlayerVelocity = new Position(initialVelocityX, initialVelocityY);
    private final Position PlayerAcceleration = new Position(initialAccelerationX, initialAccelerationY);

    private final Dimension playerSize = new Dimension(20, 20);

    public Player getPlayer() {
        player = new Player(PlayerPosition, playerSize, PlayerVelocity, PlayerAcceleration);
        startGame();
        return player;
    }

    public void startGame() {
        player.setIsPhysical(true);
    }

    public void stopGame() {
        player.setIsPhysical(false);
    }

    public void requestNewGame() {
        player.setPosition(initialPlayX, initialPlayY);
        player.setAcceleration(initialAccelerationX, initialAccelerationY);
        player.setVelocity(initialVelocityX, initialVelocityY);
    }

    public void tick() {
        if (player.getBottomY() > BOTTOM || player.getPosition().y < TOP) {
            requestNewGame();
        }
    }
}
