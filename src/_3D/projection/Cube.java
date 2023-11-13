package _3D.projection;

import Fill.ScanLine;
import Forms.Polygon;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        if (lineColor == null){
           lineColor = Color.BLUE;
        }
        Line line = new Line(lineColor);
        //fill top
        fillPolygon(pointsOfProjection[6], pointsOfProjection[4], pointsOfProjection[5], pointsOfProjection[7]);
        //fill right
        fillPolygon(pointsOfProjection[7], pointsOfProjection[5], pointsOfProjection[1], pointsOfProjection[3]);
        //fill back
        fillPolygon(pointsOfProjection[2], pointsOfProjection[0], pointsOfProjection[1], pointsOfProjection[3]);
        fillPolygon(pointsOfProjection[6], pointsOfProjection[4], pointsOfProjection[0], pointsOfProjection[2]);




        //front square
        line.drawDDALine(pointsOfProjection[4], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[4], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());

        //back square
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[1], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[1], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[2], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[2], bufferedImage, bufferedImage.getGraphics());

        line.drawDDALine(pointsOfProjection[1], pointsOfProjection[5], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[0], pointsOfProjection[4], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[7], pointsOfProjection[3], bufferedImage, bufferedImage.getGraphics());
        line.drawDDALine(pointsOfProjection[2], pointsOfProjection[6], bufferedImage, bufferedImage.getGraphics());




    }

    private void fillPolygon(MyPoint...points){
        Polygon polygon = new Polygon(bufferedImage, points);
        if (backgroundColor == null){
            backgroundColor = Color.BLUE;
        }
        polygon.changeColor(backgroundColor);
        polygon.getMidPoint(points);
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
}
