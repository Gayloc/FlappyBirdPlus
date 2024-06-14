package com.gayloc.flappyBirdPlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Background extends Component{

    BufferedImage background;

    public Background(Vec position, Dimension size, Vec velocity, Vec acceleration) {
        super(position, size, velocity, acceleration);
        loadImage();
        setIsPhysical(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (position.x < -Board.boardWidth) {
            position.x = -5;
        }
    }

    private void loadImage() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/background.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, (int) position.x, (int) position.y, size.width, size.height, null);
        g.drawImage(background, (int) position.x+Board.boardWidth, (int) position.y, size.width, size.height, null);
    }
}
