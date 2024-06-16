/**
* @Description 显示玩家位置的组件
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 显示玩家位置的组件
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public class LocationText extends Text {

    // 用于获取玩家的数据
    private final Player player;

    /**
    * {@code @Description} 构造函数，初始化玩家和文字样式
    * {@code @Param} Vec position, int size, Player player
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    public LocationText(Vec position, int size, Player player) {
        super(position, size, "位置", Color.BLACK, new Font("宋体", Font.PLAIN, size));
        this.player = player;
    }

    /**
    * {@code @Description} 更新位置信息（性能优化空间）
    * {@code @Param} void
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    @Override
    public void tick() {
        super.tick();
        content = "位置："+player.getPosition();
    }

    /**
    * {@code @Description} 渲染的方法
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午1:45
    */
    @Override
    public void render(Graphics g) {
        // 只在选择显示位置时才渲染
        if (!App.showLocation) return;
        super.render(g);
    }
}
