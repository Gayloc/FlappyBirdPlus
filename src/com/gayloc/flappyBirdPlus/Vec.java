/**
* @Description 一个二维向量
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

/**
* {@code @Description} 二维向量
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
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
