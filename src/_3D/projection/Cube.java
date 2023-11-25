package _3D.projection;

import Fill.ScanLine;
import Forms.Polygon;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Cube {
    private final BufferedImage bufferedImage;
    private MyPoint[]points;
    private Color lineColor;
    private Color backgroundColor;
    public Cube(BufferedImage bufferedImage, MyPoint[]myPoints){
        this.bufferedImage = bufferedImage;
        this.points = myPoints;
    }

    public void draw(int[]vectorOfProjection){
        MyPoint[] pointsOfProjection = new MyPoint[8];

        for (int i = 0; i < points.length; i++){
            double u =  (double) (points[i].getZ()) / vectorOfProjection[2];
            double x = points[i].getX() + (vectorOfProjection[0] * u)+100;
            double y = points[i].getY() + (vectorOfProjection[1] * u);

            pointsOfProjection[i] = new MyPoint((int) x,(int)y);
        }
//        System.out.println(Arrays.toString(pointsOfProjection));

        drawBackgroundOfCube(pointsOfProjection);
        drawOutlineOfCube(pointsOfProjection);
    }

    public void drawWithPerspective(int[]vectorOfProjection){
        MyPoint[] pointsOfProjection = new MyPoint[8];
        for (int i = 0; i < points.length; i++){
            double u =  (double) (points[i].getZ()) / vectorOfProjection[2];
            double x = vectorOfProjection[0] + ((points[i].getX() - vectorOfProjection[0]) * u)+100;
            double y = vectorOfProjection[1] + ((points[i].getY() - vectorOfProjection[1]) * u)+300;
            pointsOfProjection[i] = new MyPoint((int) x,(int)y);
        }

        System.out.println(Arrays.toString(pointsOfProjection));
        drawBackgroundOfCube(pointsOfProjection);
//        drawOutlineOfCube(pointsOfProjection);
    }

    private void fillPolygon(MyPoint...points){
        Polygon polygon = new Polygon(bufferedImage, points);
        polygon.changeColor(backgroundColor);
//        Flood.apply(polygon.drawPolygon());
        ScanLine.apply(points, polygon.drawPolygon());
    }

    public void setLineColor(Color color){
        if (color != null){
            this.lineColor = color;
        }
    }

    public void setBackgroundColor(Color color){
        if (color != null){
            this.backgroundColor = color;
        }
    }
    private void drawBackgroundOfCube(MyPoint[]pointsOfProjection){
        if (backgroundColor != null){
            //fill front
            fillPolygon(pointsOfProjection[6], pointsOfProjection[4], pointsOfProjection[5], pointsOfProjection[7]);
            //fill right
            fillPolygon(pointsOfProjection[7], pointsOfProjection[5], pointsOfProjection[1], pointsOfProjection[3]);
            //fill back
            fillPolygon(pointsOfProjection[2], pointsOfProjection[0], pointsOfProjection[1], pointsOfProjection[3]);
            //fil left
            fillPolygon(pointsOfProjection[6], pointsOfProjection[4], pointsOfProjection[0], pointsOfProjection[2]);
            //fill top
            fillPolygon(pointsOfProjection[4], pointsOfProjection[0], pointsOfProjection[1], pointsOfProjection[5]);
            // fill bottom
            fillPolygon(pointsOfProjection[6], pointsOfProjection[2], pointsOfProjection[3], pointsOfProjection[7]);
        }
    }
    private void drawOutlineOfCube(MyPoint[]pointsOfProjection){
        if (lineColor == null){
            lineColor = Color.BLUE;
        }

        Line line = new Line(lineColor);
        //back square (top-right-bottom-left is the sequence)
        line.drawDDALine(pointsOfProjection[4], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[4], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());

        //front square
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[1], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[1], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[2], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[2], bufferedImage, bufferedImage.getGraphics());

        line.drawDDALine(pointsOfProjection[1], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[4], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[2], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());
    }

    public MyPoint getCenter(){
        int xCenter = 0;
        int yCenter = 0;
        int zCenter = 0;
        for (int i = 0; i < points.length; i++) {
            xCenter = xCenter + points[i].getX();
            yCenter = yCenter + points[i].getY();
            zCenter = zCenter + points[i].getZ();
        }
        xCenter = xCenter/ points.length;
        yCenter = yCenter / points.length;
        zCenter = zCenter / points.length;

        return new MyPoint(xCenter, yCenter, zCenter);
    }

    public void setPoints(MyPoint[]points){
        this.points = points;
    }

    public double[][] getMatrixOfPoints(){
        return new double[][]{
                {points[0].getX(), points[0].getY(), points[0].getZ()},
                {points[1].getX(), points[1].getY(), points[1].getZ()},
                {points[2].getX(), points[2].getY(), points[2].getZ()},
                {points[3].getX(), points[3].getY(), points[3].getZ()},
                {points[4].getX(), points[4].getY(), points[4].getZ()},
                {points[5].getX(), points[5].getY(), points[5].getZ()},
                {points[6].getX(), points[6].getY(), points[6].getZ()},
                {points[7].getX(), points[7].getY(), points[7].getZ()},
        };
    }

    public Color applyAmbientLight(Color color, int ambientLightIntensity) {
        int red = (color.getRed() + ambientLightIntensity);
        int green = (color.getGreen() + ambientLightIntensity);
        int blue = (color.getBlue() + ambientLightIntensity);
//        System.out.println("before: "+"red = "+red+", blue = "+blue+", green = "+green);

        // Asegurarse de que los valores estén dentro del rango (0-255)
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);
//        System.out.println("after: "+"red = "+red+", blue = "+blue+", green = "+green);
        return new Color(red, green, blue);
    }
}
