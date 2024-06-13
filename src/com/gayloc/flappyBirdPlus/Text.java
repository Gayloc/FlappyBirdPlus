package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Text extends Component{

    protected String content;
    protected Color color;
    protected Font font;

    public Text(Vec position, int size, String content, Color color, Font font) {
        super(position, new Dimension(content.length()*size, size));
        this.content = content;
        this.color = color;
        this.font = font;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(content, (int) position.x, (int) position.y);
    }
}
