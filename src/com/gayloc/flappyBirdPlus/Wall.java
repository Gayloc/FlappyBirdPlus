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

    static Wall createTopWall(int height, int width) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.TOP),
                new Dimension(width, height+200),
                new Vec(Controller.WALL_SPEED, 0),
                new Vec(0, 0)
        );
    }

    static Wall createBottomWall(int height, int width) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.BOTTOM-height),
                new Dimension(width, height),
                new Vec(Controller.WALL_SPEED, 0),
                new Vec(0, 0)
        );
    }
}
