package _3D.projection;

import Lines.Line;
import interfaces.IPixel;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Worm implements IPixel {
    private Color color;
    private final BufferedImage bufferedImage;
    private final MyPoint startPoint;

    public Worm(BufferedImage bufferedImage, MyPoint startPoint){
        this.bufferedImage = bufferedImage;
        this.startPoint = startPoint;
    }

    public void draw(){
        int numberOfPoints = 100;
        double[] pointZ = new double[numberOfPoints];
        double t = (8 * Math.PI) / numberOfPoints;
        double[] x = new double[numberOfPoints];
        double[] y = new double[numberOfPoints];
        MyPoint[]points = new MyPoint[numberOfPoints];


        for (int i = 0; i < numberOfPoints; i++) {
            pointZ[i] = t * i;
            x[i] = Math.cos(pointZ[i]);
            y[i] = Math.sin(pointZ[i]);
            points[i] = new MyPoint();
        }

        double[] vectorOfProjection = {0.1, 6, 75};
        double[] u = new double[numberOfPoints];

        for (int i = 0; i < numberOfPoints - 1; i++) {
            u[i] = -(pointZ[i] / vectorOfProjection[2]);
            points[i].setX((int)((x[i] + (vectorOfProjection[0] * u[i])) * 50 + startPoint.getX()));
            points[i].setY((int)((y[i] + (vectorOfProjection[1] * u[i])) * 25 + startPoint.getY()));

            putPixel(points[i].getX(), points[i].getY(), bufferedImage, Color.red);
        }
        drawPolyLine(points);
    }

    private void drawPolyLine(MyPoint[]points) {
        int nNums = points.length;
        if (color == null){
            color = Color.red;
        }
        Line line = new Line(color);
        for (int i = 1; i < nNums-1; i++) {
            line.drawDDALine(points[i],points[i-1], bufferedImage, bufferedImage.getGraphics());
        }
    }

    @Override
    public void changeColor(Color color) {
        IPixel.super.changeColor(color);
        this.color = color;
    }
}
