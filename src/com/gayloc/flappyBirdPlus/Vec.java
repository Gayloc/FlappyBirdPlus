package com.gayloc.flappyBirdPlus;

public class Vec {
    public double x;
    public double y;

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}
