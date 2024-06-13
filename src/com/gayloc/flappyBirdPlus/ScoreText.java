package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class ScoreText extends Text{

    private final Player player;

    public ScoreText(Player player) {
        super(new Vec(30,50), 30, "分数", Color.gray, new Font("微软雅黑", Font.BOLD, 30));
        this.player = player;
    }

    @Override
    public void tick() {
        super.tick();
        content = "分数："+player.getScore();
    }
}
