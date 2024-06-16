/**
* @Description 文字组件
* @Author 古佳乐
* @Date 2024/6/13-下午1:45
*/

package com.gayloc.flappyBirdPlus;

import java.awt.*;

/**
* {@code @Description} 文字组件
* {@code @Author} 古佳乐
* {@code @Date} 2024/6/13-下午1:45
*/
public class Text extends Component{
    protected String content; // 显示的内容
    protected Color color; // 文字颜色
    protected Font font; // 文字字体

    /**
    * {@code @Description} 构造函数，初始化文字的样式和组件大小
    * {@code @Param}
    * {@code @return}
    * {@code @Author} 古佳乐
    * {@code @Date} -
    */
    public Text(Vec position, int size, String content, Color color, Font font) {
        super(position, new Dimension(content.length()*size, size));
        this.content = content;
        this.color = color;
        this.font = font;
    }

    /**
    * {@code @Description} 绘制文字组件
    * {@code @Param} Graphics g
    * {@code @return} void
    * {@code @Author} 古佳乐
    * {@code @Date} 2024/6/13-下午5:30
    */
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // 转换为 g2d 以使用坑锯齿功能
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(content, (int) position.x, (int) position.y);
    }
}
