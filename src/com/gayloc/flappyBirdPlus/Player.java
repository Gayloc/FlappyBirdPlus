package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Player extends Component{
    private int score = 0;

    public Player(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
    }

    public void jump() {
        this.velocity.y = -10;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }
}