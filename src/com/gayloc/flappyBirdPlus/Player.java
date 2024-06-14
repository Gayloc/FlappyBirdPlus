package com.gayloc.flappyBirdPlus;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Player extends Component{

    private int score = 0;
    private int imageCounter = 0;
    private Clip clip;
    private BufferedImage spriteSheet;
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private final int totalFrames = 2;
    private final int frameWidth = 70;
    private final int frameHeight = 60;

    public Player(Vec position, Vec velocity, Vec acceleration) {
        super(position, new Dimension(40, 30), velocity, acceleration);
        loadSpriteSheet();
        loadJumpSound();
        extractFrames();
    }

    private void loadJumpSound() {
        System.out.println("Loading Jump Sound");
        try {
            File soundFile = new File("src/resources/sounds/jump.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/bird.png")));
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    private void extractFrames() {
        frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
    }

    public void jump() {
        if (clip.isRunning()) {
            clip.stop(); // 停止当前正在播放的音效
        }
        clip.setFramePosition(0); // 重置音效播放位置
        clip.start(); // 播放音效
        this.velocity.y = -10;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double rotationAngle = getRotationAngle();
        int centerX = (int) (position.x + (double) size.width / 2);
        int centerY = (int) (position.y + (double) size.height / 2);

        AffineTransform originalTransform = g2d.getTransform();

        g2d.rotate(rotationAngle, centerX, centerY);
        g2d.drawImage(frames[currentFrame], (int) position.x - size.width / 2, (int) position.y - size.height / 2, frameWidth, frameHeight, null);
        g2d.setTransform(originalTransform);

        if (App.showBBox) {
            super.render(g);
        }
    }

    private double getRotationAngle() {
        double maxVelocity = 10;
        return (velocity.y / maxVelocity) * Math.toRadians(45);
    }

    @Override
    public void tick() {
        if (velocity.y <= 0 && !Controller.getIsGameOver()) {
            imageCounter++;
            currentFrame = (imageCounter / 10) % totalFrames;
        } else {
            currentFrame = 0;
        }
        super.tick();
    }
}