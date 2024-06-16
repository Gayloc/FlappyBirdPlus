/**
* @Description 游戏组件
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 游戏组件类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public abstract class Component {

    // 组件位置
    protected Vec position;
    // 组件大小
    protected Dimension size;
    // 组件速度
    protected Vec velocity = new Vec(0, 0);
    // 组件加速度
    protected Vec acceleration = new Vec(0, 0);
    // 是否有物理效果，默认为否
    protected boolean isPhysical = false;

    /**
    * {@code @Description} 构造函数，初始化位置和大小
    * {@code @Param} Vec position, Dimension size
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public Component(Vec position, Dimension size) {
        this.position = position;
        this.size = size;
    }

    /**
     * {@code @Description} 构造函数，初始化位置、大小、速度和加速度
     * {@code @Param} Vec position, Dimension size
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午1:45
     */
    public Component(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    /**
    * {@code @Description} 设置组件是否有物理效果
    * {@code @Param} boolean isPhysical
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public void setIsPhysical(boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    /**
    * {@code @Description} 每个 tick 执行一次，这里是计算物理效果
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public void tick() {
        // 只有组件有物理效果时才计算
        if (isPhysical) {
            velocity.x += acceleration.x;
            velocity.y += acceleration.y;
            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

    /**
    * {@code @Description} 渲染方法，用于绘制组件样式，这里是默认样式
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int) position.x, (int) position.y, size.width, size.height);
    }

    /**
    * {@code @Description} 判断是否与另一个组件相撞
    * {@code @Param} Component component
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public boolean isCollision(Component component) {
        if (position.x+size.width > component.getPosition().x && position.x < component.getPosition().x+component.getSize().width) {
            return getBottomY() > component.getPosition().y && position.y < component.getPosition().y + component.getSize().height;
        }
        return false;
    }

    /**
    * {@code @Description} 设置组件速度
    * {@code @Param} double x, double y
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public void setVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    /**
     * {@code @Description} 设置组件加速度
     * {@code @Param} double x, double y
     * {@code @return} void
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午1:45
     */
    public void setAcceleration(double x, double y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    /**
    * {@code @Description} 获取组件速度
    * {@code @Param} void
    * {@code @return} Vec
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public Vec getVelocity() {
        return velocity;
    }

    /**
     * {@code @Description} 获取组件加速度
     * {@code @Param} void
     * {@code @return} Vec
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午1:45
     */
    public Vec getAcceleration() {
        return acceleration;
    }

    /**
     * {@code @Description} 获取组件位置
     * {@code @Param} void
     * {@code @return} Vec
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午1:45
     */
    public Vec getPosition() {
        return position;
    }

    /**
     * {@code @Description} 获取组件大小
     * {@code @Param} void
     * {@code @return} Dimension
     * {@code @Author} 古佳乐
     * {@code @Date} 2024/6/13-下午1:45
     */
    public Dimension getSize() {
        return size;
    }

    /**
    * {@code @Description} 获取组件物理状态
    * {@code @Param} void
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public boolean getIsPhysical() {
        return isPhysical;
    }

    /**
    * {@code @Description} 获取组件底部坐标
    * {@code @Param} void
    * {@code @return} double
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public double getBottomY() {
        return position.y + size.height;
    }

    /**
    * {@code @Description} 设置组件位置
    * {@code @Param} int x, int y
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }
}
