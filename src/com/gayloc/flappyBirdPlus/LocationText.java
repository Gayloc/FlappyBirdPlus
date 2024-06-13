package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class LocationText extends Text {

    private final Player player;

    public LocationText(Position position, int size, Player player) {
        super(position, size, "位置", Color.BLACK, new Font("宋体", Font.PLAIN, 15));
        this.player = player;
    }

    @Override
    public void tick() {
        super.tick();
        content = "位置："+player.getPosition();
    }
}
