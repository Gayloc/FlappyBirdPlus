/**
* @Description 玩家对象
* @Author 古佳乐
* @Date 2024/6/13-下午5:30
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

/**
* {@code @Description} 玩家对象
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午5:30
*/
public class Player extends Component{

    // 玩家的数据
    private int score = 0; // 本局游戏的分数
    private int imageCounter = 0; // 用于切换贴图的计时器
    private Clip clip; // 跳跃音效
    private BufferedImage spriteSheet; // 贴图（精灵图）
    private BufferedImage[] frames; // 贴图
    private int currentFrame = 0; // 当前的贴图
    private final int totalFrames = 2; // 贴图数量
    private final int frameWidth = 70; // 贴图宽度
    private final int frameHeight = 60; // 贴图高度

    /**
    * {@code @Description} 构造函数，初始化玩家的组件数据并加载相关资源
    * {@code @Param} Vec position, Vec velocity, Vec acceleration
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    public Player(Vec position, Vec velocity, Vec acceleration) {
        super(position, new Dimension(40, 30), velocity, acceleration);
        loadSpriteSheet();
        loadJumpSound();
        extractFrames(); // 切割精灵图
    }

    /**
    * {@code @Description} 加载跳跃音效
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    private void loadJumpSound() {
        System.out.println("Loading Jump Sound");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/resources/sounds/jump.wav")));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * {@code @Description} 加载精灵图
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    private void loadSpriteSheet() {
        System.out.println("Loading Sprite Sheet");
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/bird.png")));
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    /**
    * {@code @Description} 切割精灵图
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    private void extractFrames() {
        frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
    }

    /**
    * {@code @Description} 跳跃
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午2:24
    */
    public void jump() {
        if (clip.isRunning()) {
            clip.stop(); // 停止当前正在播放的音效
        }
        clip.setFramePosition(0); // 重置音效播放位置
        clip.start(); // 播放音效
        this.velocity.y = -10;
    }

    /**
    * {@code @Description} 获取分数，为实际值的1/2
    * {@code @Param} void
    * {@code @return} int
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    public int getScore() {
        return score/2;
    }

    /**
    * {@code @Description} 增加分数
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    public void addScore() {
        this.score++;
    }

    /**
    * {@code @Description} 重置分数
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    public void resetScore() {
        this.score = 0;
    }

    /**
    * {@code @Description} 渲染方法
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // 转换为 Graphics2d
        double rotationAngle = getRotationAngle(); // 获取当前的旋转角

        // 计算中心点
        int centerX = (int) (position.x + (double) size.width / 2);
        int centerY = (int) (position.y + (double) size.height / 2);

        // 保存原本状态
        AffineTransform originalTransform = g2d.getTransform();

        // 旋转
        g2d.rotate(rotationAngle, centerX, centerY);
        g2d.drawImage(frames[currentFrame], (int) position.x - size.width / 2, (int) position.y - size.height / 2, frameWidth, frameHeight, null);

        // 恢复原本状态
        g2d.setTransform(originalTransform);

        // 如果显示碰撞箱就执行 super.render(g)
        if (App.showBBox) {
            super.render(g);
        }
    }

    /**
    * {@code @Description} 基于 Y 轴方向的速度计算旋转角
    * {@code @Param} void
    * {@code @return} double
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    private double getRotationAngle() {
        double maxVelocity = 10; // 限制最大旋转范围
        return (velocity.y / maxVelocity) * Math.toRadians(45);
    }

    /**
    * {@code @Description} 切换贴图实现动画效果
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    @Override
    public void tick() {
        if (velocity.y <= 0 && !Controller.getIsGameOver()) {
            imageCounter++;
            currentFrame = (imageCounter / 10) % totalFrames;
        } else {
            // 当下落时固定贴图为第一张
            currentFrame = 0;
        }
        super.tick();
    }
}