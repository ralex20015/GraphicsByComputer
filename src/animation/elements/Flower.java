package animation.elements;

import Curve.Curve;
import Fill.ScanLine;
import Forms.OutlineShape;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class Flower extends BufferedImage {
    private Graphics graphics;
    private final int x;
    private final int y;
    private final Color[]colors = {Color.RED, Color.PINK, Color.BLUE, Color.YELLOW, new Color(102,0,204),
        new Color(204,255,153), new Color(255, 229, 180), Color.ORANGE};
    private int limit;

    public Flower(int width, int height, int imageType, int x, int y) {
        super(width, height, imageType);
        this.x = x;
        this.y =y;
        SecureRandom secureRandom = new SecureRandom();
        int randomColor = secureRandom.nextInt(colors.length);
        Color color = new Color(colors[randomColor].getRGB());

        Curve curve = new Curve(this);
        curve.drawFlower(new MyPoint(width/2,height/4),10, color);
        OutlineShape shape = new OutlineShape();
        shape.changeColor(Color.green);
        ScanLine.apply(shape.drawRectangle((width/2)-2,(height/2)-10,4,56,this,graphics));
    }
    public void setGraphicsWhile(Graphics graphics, int current, int limit){
        if (current < limit){
            this.graphics = graphics;
            this.limit = limit;
            graphics.drawImage(this,x,y,null);
        }
    }

    public int getLimit() {
        return limit;
    }
}
