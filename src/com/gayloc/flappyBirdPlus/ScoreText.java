/**
* @Description 计分器
* @Author 古佳乐
* @Date 2024/6/13-下午4:46
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 计分器
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午4:46
*/
public class ScoreText extends Text{

    // 用于获取玩家数据
    private final Player player;

    /**
    * {@code @Description} 构造函数，初始化样式和玩家数据
    * {@code @Param} Player player
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    public ScoreText(Player player) {
        super(new Vec(30,50), 30, "分数", Color.white, new Font("微软雅黑", Font.BOLD, 30));
        this.player = player;
    }

    /**
    * {@code @Description} 每个 tick 执行一次计分操作（性能优化空间）
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午4:46
    */
    @Override
    public void tick() {
        super.tick();
        content = "分数："+player.getScore();
    }
}
