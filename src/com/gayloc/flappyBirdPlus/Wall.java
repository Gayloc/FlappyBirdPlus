package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Wall extends Component{
    public Wall(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
        setIsPhysical(true);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    static Wall createTopWall(int height) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.TOP),
                new Dimension(150, height+200),
                new Vec(-5, 0),
                new Vec(0, 0)
        );
    }

    static Wall createBottomWall(int height) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.BOTTOM-height-80),
                new Dimension(150, height+80),
                new Vec(-5, 0),
                new Vec(0, 0)
        );
    }
}
