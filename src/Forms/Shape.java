package Forms;

import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Shape {
    private final ArrayList<MyPoint>arrayList;
    private final BufferedImage bufferedImage;
    private final Graphics graphics;
    private int centerX;
    private int centerY;
    private final Color color;
    private final Figure figure;
    private int radius;
    public Shape(ArrayList<MyPoint> arrayList, int centerX, int centerY, BufferedImage bufferedImage, Graphics g, Color color,
                 Figure figure){
        this.arrayList = arrayList;
        this.figure = figure;
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

    public void setRadius(int radius){
        if (!figure.equals(Figure.RECTANGLE)){
            this.radius = radius;
        }
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
