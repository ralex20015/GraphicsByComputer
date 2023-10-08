package Forms;

import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Shape {
    private final ArrayList<MyPoint>arrayList;
    private final BufferedImage bufferedImage;
    private final Graphics graphics;
    private final int centerX;
    private final int centerY;
    private final Color color;
    private final Figure figure;
    public Shape(ArrayList<MyPoint> arrayList, int centerX, int centerY, BufferedImage bufferedImage, Graphics g, Color color,
                 Figure figure){
        this.arrayList = arrayList;
        this.figure =figure;
        this.bufferedImage = bufferedImage;
        graphics = g;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }

    public ArrayList<MyPoint> getArrayList() {
        return arrayList;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public int getXCenter() {
        return centerX;
    }

    public int getYCenter(){
        return centerY;
    }

    public Color getColor() {
        return color;
    }
    public enum Figure{
        BASIC_CIRCLE, POLAR_CIRCLE, RECTANGLE, ELLIPSE, MID_POINT_CIRCLE
    }

    public Figure getFigure(){
        return figure;
    }
}
