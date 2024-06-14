package com.gayloc.flappyBirdPlus;

import java.awt.*;

public abstract class Component {

    protected Vec position;
    protected Dimension size;
    protected Vec velocity = new Vec(0, 0);
    protected Vec acceleration = new Vec(0, 0);
    protected Boolean isPhysical = false;

    public Component(Vec position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    public Component(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void setIsPhysical(Boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    public void tick() {
        if (isPhysical) {
            velocity.x += acceleration.x;
            velocity.y += acceleration.y;
            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int) position.x, (int) position.y, size.width, size.height);
    }

    public Boolean isCollision(Component component) {
        if (position.x+size.width > component.getPosition().x && position.x < component.getPosition().x+component.getSize().width) {
            return getBottomY() > component.getPosition().y && position.y < component.getPosition().y + component.getSize().height;
        }
        return false;
    }

    public void setVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public void setAcceleration(double x, double y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    public Vec getVelocity() {
        return velocity;
    }

    public Vec getAcceleration() {
        return acceleration;
    }

    public Vec getPosition() {
        return position;
    }

    public Dimension getSize() {
        return size;
    }

    public Boolean getIsPhysical() {
        return isPhysical;
    }

    public double getBottomY() {
        return position.y + size.height;
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }
}
