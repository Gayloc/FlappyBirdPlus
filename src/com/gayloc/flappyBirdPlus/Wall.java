/**
* @Description 墙组件
* @Author 古佳乐
* @Date 2024/6/13-下午2:39
*/

package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 墙
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午2:39
*/
public class Wall extends Component{

    private Boolean scored = false; // 是否已经算分
    private static BufferedImage wallImage; // 储存贴图

    /**
    * {@code @Description} 构造函数，初始化墙的尺寸和速度
    * {@code @Param} Vec position, Dimension size, Vec velocity, Vec acceleration
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    public Wall(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
        setIsPhysical(true);
    }

    /**
    * {@code @Description} 设置算分状态
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    public void scored() {
        scored = true;
    }

    /**
    * {@code @Description} 获取算分状态
    * {@code @Param} void
    * {@code @return} boolean
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    public boolean isScored() {
        return scored;
    }

    /**
    * {@code @Description} 墙的渲染
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午12:39
    */
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (wallImage != null && !App.showBBox) {
            // 画面外的柱子材质干扰其他UI组件，这里限制它的绘制范围
            int topOutside = Math.max(-(int) position.y, 0);

            int x = (int) position.x;
            int y = (int) position.y+topOutside;
            int width = size.width;
            int height = size.height-topOutside;

            Shape originalClip = g2d.getClip();
            g2d.setClip(new Rectangle(x, y, width, height));

            // 使用平铺效果绘制，超出范围就剪掉
            for (int i = 0; i < width; i += wallImage.getWidth()) {
                for (int j = 0; j < height; j += wallImage.getHeight()) {
                    g2d.drawImage(wallImage, x + i, y + j, null);
                }
            }

            g2d.setClip(originalClip);
        } else {
            super.render(g);
        }
    }

    /**
    * {@code @Description} 加载墙的贴图
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午12:39
    */
    //墙的数量较多，加载图片的步骤放在 Controller
    public static void loadImage() {
        System.out.println("Loading wall image");
        try {
            wallImage = ImageIO.read(Objects.requireNonNull(Wall.class.getResource("/images/brick.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * {@code @Description} 绘制屏幕顶端的墙
    * {@code @Param} int height, int width
    * {@code @return} Wall
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    static Wall createTopWall(int height, int width) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.TOP),
                new Dimension(width, height+200),
                new Vec(Controller.WALL_SPEED, 0),
                new Vec(0, 0)
        );
    }

    /**
    * {@code @Description} 绘制屏幕底端的墙
    * {@code @Param} int height, int width
    * {@code @return} Wall
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午2:39
    */
    static Wall createBottomWall(int height, int width) {
        return new Wall(
                new Vec(Controller.RIGHT, Controller.BOTTOM-height),
                new Dimension(width, height),
                new Vec(Controller.WALL_SPEED, 0),
                new Vec(0, 0)
        );
    }
}
