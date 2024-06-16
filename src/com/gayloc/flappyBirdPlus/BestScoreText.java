/**
* @Description 最佳记录文本 
* @Author 古佳乐
* @Date 2024/6/16-下午1:05
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 最佳记录文本，继承自 Text 组件
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/15-下午9:08
*/
public class BestScoreText extends Text {
    // 储存目标用户
    User user;

    /**
    * {@code @Description} 构造函数
    * {@code @Param} User user
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午9:08
    */
    public BestScoreText(User user) {
        super(new Vec(30,100), 30, "分数", Color.white, new Font("微软雅黑", Font.BOLD, 30));
        this.user = user;
    }

    /**
    * {@code @Description} 更新用户信息，当修改用户信息时调用
    * {@code @Param} User user
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午9:08
    */
    public void updateUser(User user) {
        this.user = user;
    }

    /**
    * {@code @Description} 重写 tick 方法 
    * {@code @Param} void 
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午9:08
    */
    @Override
    public void tick() {
        super.tick();
        // 每个 tick 更新最高分数（性能优化空间）
        content = user.getName()+"的最高分数："+user.getScore();
    }

    /**
    * {@code @Description} 重写渲染方法 
    * {@code @Param} Graphics g 
    * {@code @return} void 
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/15-下午6:15
    */
    @Override
    public void render(Graphics g) {
        // 如果游戏开始则隐藏
        if (!Controller.getIsGaming()) {
            super.render(g);
        }
    }
}
