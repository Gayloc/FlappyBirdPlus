package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Player extends Component{

    public Player(Position position, Dimension size, Position velocity, Position acceleration) {
        super(position, size, velocity, acceleration);
    }

    public void jump() {
        this.velocity.y = -15;
    }
}