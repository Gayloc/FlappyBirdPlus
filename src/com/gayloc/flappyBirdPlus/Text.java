package com.gayloc.flappyBirdPlus;

import java.awt.*;

public class Text extends Component{

    protected String content;
    protected Color color;
    protected Font font;

    public Text(Position position, int size, String content, Color color, Font font) {
        super(position, new Dimension(content.length()*size, size));
        this.content = content;
        this.color = color;
        this.font = font;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(content, (int) position.x, (int) position.y);
    }
}
