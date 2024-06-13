package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Player extends Component{

    public Player(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
    }

    public void jump() {
        this.velocity.y = -10;
    }
}