package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class BestScoreText extends Text {
    User user;

    public BestScoreText(User user) {
        super(new Vec(30,100), 30, "分数", Color.white, new Font("微软雅黑", Font.BOLD, 30));
        this.user = user;
    }

    @Override
    public void tick() {
        super.tick();
        content = user.getName()+"的最高分数："+user.getScore();
    }

    @Override
    public void render(Graphics g) {
        if (!Controller.getIsGaming()) {
            super.render(g);
        }
    }
}
