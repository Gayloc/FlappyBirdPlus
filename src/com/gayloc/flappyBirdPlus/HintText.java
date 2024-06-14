package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class HintText extends Text {

public HintText() {
        super(new Vec((double) (Board.boardWidth - 12 * 30) /2,Board.boardHeight-300), 30, "点击屏幕或任意键开始游戏", Color.white, new Font("微软雅黑", Font.BOLD, 30));
    }

    @Override
    public void tick() {
        if (Controller.getIsGaming()) {
            content = "点击屏幕或任意键重新开始";
        }
    }

    @Override
    public void render(Graphics g) {
        if (!Controller.getIsGaming()) {
            super.render(g);
        }
    }
}
