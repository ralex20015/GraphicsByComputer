package Curve;

import Forms.Polygon;
import Lines.Line;
import interfaces.IPixel;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Curve {
    private final BufferedImage bufferedImage;
    private Color color;
    public Curve(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    public void drawSun(MyPoint p, double scale, Color color) {
        double t, x, y;
        double dt = 0.005;

        for (t = 0; t <= 14 * Math.PI; t += dt) {
            x = 17 * Math.cos(t) + 7 * Math.cos((17.0 / 7.0) * t);
            y = 17 * Math.sin(t) - 7 * Math.sin((17.0 / 7.0) * t);
            int pixelX = (int) Math.round(x * scale);
            int pixelY = (int) Math.round(y * scale);
            pixel().putPixel(p.getX() + pixelX, p.getY() + pixelY, bufferedImage, color);
        }
    }

    public void drawFlower(MyPoint center, int scale, Color c) {
        double t, x, y;
        double dt = 0.002;

        for (t = 0.0; t <= 2 * Math.PI; t += dt) {
            x = Math.cos(t) + 0.5 * Math.cos(7 * t) + (1.0 / 3.0) * Math.sin(17 * t);
            y = Math.sin(t) + 0.5 * Math.sin(7 * t) + (1.0 / 3.0) * Math.cos(17 * t);
            int pixelX = (int) Math.round(x * scale);
            int pixelY = (int) Math.round(y * scale);
            pixel().putPixel(center.getX() + pixelX, center.getY() + pixelY, bufferedImage,c);
        }
    }

    public void drawSmoke(MyPoint origin, Dimension size, Color c) {
        double escalaY = (double) size.height / (2 * Math.PI);
        double escalaX = (double) size.width / (2 * Math.PI);
        double y, x;

        for (double i = 0; i < size.width; i = i + 0.01) {
            y = i / escalaX + .5;
            x = y * Math.cos(4 * y);
            int j = (int) (x * escalaY);
            pixel().putPixel(origin.getX() + j, (int) (origin.getY() + i * -1), bufferedImage,c);
        }
    }

    public void drawParable(int numberOfPoints, MyPoint startPoint, int scaleX, int scaleY) {
        drawPolyLine(getPointsOfCurve(numberOfPoints,startPoint,scaleX,scaleY));
    }

    public MyPoint[] getPointsOfCurve(int numberOfPoints, MyPoint startPoint, int scaleX, int scaleY){
        MyPoint[]points = new MyPoint[numberOfPoints];
        for (int i = 0; i < points.length; i++) {
            points[i] = new MyPoint(0, 0);
        }

        for (int i = 0; i < numberOfPoints; i++) {
            double angle = (Math.PI / (numberOfPoints - 1)) * i;
            points[i].setX((int)((angle * scaleX)+ startPoint.getX()));
            points[i].setY((int)(-Math.sin(angle) * scaleY)+startPoint.getY());
        }
        return points;
    }

    private void drawPolyLine(MyPoint[]points) {
        int nNums = points.length;
        if (color == null){
            color = Color.red;
        }
        Line line = new Line(color);
        for (int i = 1; i < nNums; i++) {
            line.drawDDALine(points[i],points[i-1], bufferedImage, bufferedImage.getGraphics());
        }
    }

    public void drawInfinite(int radius, MyPoint center){
        for (double theta = 0; theta < 2 * Math.PI; theta+= 0.01) {
            int x = (int)((radius * Math.cos(theta))) + center.getX();
            int y = (int) ((radius * Math.sin(2 * theta)/2) + center.getY());
            if (color == null){
                color = Color.BLUE;
            }
            pixel().putPixel(x,y,bufferedImage, color);
        }
    }

    public void drawParticleMovement(int tMax){
        //x = t - 3 sent
        //y = 4 - 3cost
        int counter = 0;
        MyPoint[]lines = new MyPoint[tMax];
        for (double t = 0.0; t < tMax/10.0; t += 0.1) {
            int x = (int)((t - (3 * Math.sin(t))) * 15) + 50;
            int y = (int)((4 - (3 * -Math.cos(t))) * 15) + 50;

            if (counter < tMax){
                MyPoint point = new MyPoint(x, y);
                lines[counter] = point;
            }
            counter++;
        }
        drawPolyLine(lines);
    }

    private IPixel pixel(){
        return new IPixel() {
            @Override
            public void putPixel(int x, int y, BufferedImage bufferedImage, Color c) {
                IPixel.super.putPixel(x, y, bufferedImage, c);
            }
        };
    }

    public void setColor(Color color) {
        if (color != null){
            this.color = color;
        }
    }
}
