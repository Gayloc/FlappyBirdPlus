package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wall extends Component{

    private Boolean scored = false;
    private static BufferedImage wallImage;

    public Wall(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
        setIsPhysical(true);
    }

    public void scored() {
        scored = true;
    }

    public boolean isScored() {
        return scored;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (wallImage != null && !App.showBBox) {
            //画面外的柱子材质干扰其他UI组件，这里限制它的绘制范围
            int topOutside = Math.max(-(int) position.y, 0);

            int x = (int) position.x;
            int y = (int) position.y+topOutside;
            int width = size.width;
            int height = size.height-topOutside;

            Shape originalClip = g2d.getClip();
            g2d.setClip(new Rectangle(x, y, width, height));

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

    //墙的数量较多，加载图片的步骤放在 Controller
    public static void loadImage() {
        System.out.println("Loading wall image");
        try {
            wallImage = ImageIO.read(Objects.requireNonNull(Wall.class.getResource("/images/brick.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
