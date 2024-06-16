/**
* @Description 游戏背景组件 
* @Author 古佳乐
* @Date 2024/6/14-上午11:36
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} Background 类，继承自 Component 类
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/14-上午11:36
*/
public class Background extends Component{

    // 背景图片
    BufferedImage background;

    /**
    * {@code @Description} Background 构造函数 
    * {@code @Param} Vec position, Dimension size, Vec velocity, Vec acceleration
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-上午11:36
    */
    public Background(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
        loadImage();
        setIsPhysical(true);
    }

    /**
    * {@code @Description} 重写 Component 类的 tick 方法
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-上午11:36
    */
    @Override
    public void tick() {
        super.tick();

        // 使背景随时间移动时如果到最左边就恢复初始位置
        if (position.x < -Board.boardWidth) {
            // 留一个 tick 的时间放置出现空隙
            position.x = this.getVelocity().x;
        }
    }

    /**
    * {@code @Description} 加载图片文件
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:28
    */
    private void loadImage() {
        System.out.println("Loading background image");
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * {@code @Description} 渲重写染逻辑
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-上午11:36
    */
    @Override
    public void render(Graphics g) {
        // 使两张图片拼接在一起
        g.drawImage(background, (int) position.x, (int) position.y, size.width, size.height, null);
        g.drawImage(background, (int) position.x+Board.boardWidth, (int) position.y, size.width, size.height, null);
    }
}
