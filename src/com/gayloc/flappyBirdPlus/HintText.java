/**
* @Description 提示开始游戏和重新开始游戏的组件
* @Author 古佳乐
* @Date 2024/6/14-下午1:05
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 提示开始游戏和重新开始游戏的组件
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/14-下午1:05
*/
public class HintText extends Text {

    /**
    * {@code @Description} 构造函数，初始化文本样式
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午1:05
    */
    public HintText() {
        super(new Vec((double) (Board.boardWidth - 12 * 30) /2,Board.boardHeight-300), 30, "点击屏幕或任意键开始游戏", Color.white, new Font("微软雅黑", Font.BOLD, 30));
    }

    /**
    * {@code @Description} 更新文本内容
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午1:05
    */
    @Override
    public void tick() {
        if (Controller.getIsGaming()) {
            content = "点击屏幕或任意键重新开始";
        }
    }

    /**
    * {@code @Description} 只在游戏停止的时候显示提示
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/14-下午1:05
    */
    @Override
    public void render(Graphics g) {
        if (!Controller.getIsGaming()) {
            super.render(g);
        }
    }
}
