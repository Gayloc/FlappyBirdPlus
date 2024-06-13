package com.gayloc.flappyBirdPlus;

import java.awt.*;

public abstract class Component {
    protected Position position;
    protected Dimension size;
    protected Position velocity = new Position(0, 0);
    protected Position acceleration = new Position(0, 0);
    protected Boolean isPhysical = false;

    public Component(Position position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    public Component(Position position, Dimension size, Position velocity, Position acceleration) {
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void setIsPhysical(Boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    public void moveX(int x) {
        position.x += position.x;
    }

    public void moveY(int y) {
        position.y += position.y;
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

    public void setVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public void setAcceleration(double x, double y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    public Position getVelocity() {
        return velocity;
    }

    public Position getAcceleration() {
        return acceleration;
    }

    public Position getPosition() {
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
