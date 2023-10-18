package Forms;

import Curve.Curve;
import Lines.Line;
import transforms.Transform;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;


public class Polygon {

    private final ArrayList<MyPoint> vertices;
    private final Line line;
    private Color c;
    private final BufferedImage bufferedImage;

    public Polygon(BufferedImage bufferedImage) {
        c = Color.RED;
        this.bufferedImage = bufferedImage;
        vertices = new ArrayList<>();
        line = new Line(c);
    }

    public void changeColor(Color color){
        if (color != null){
            c = color;
            line.changeColor(color);
        }
    }

    public void setVerts(MyPoint[] verts) {
        this.vertices.clear();
        Collections.addAll(vertices, verts);
    }

    public void addVert(MyPoint p) {
        vertices.add(p);
    }

    public MyPoint[] getVerts() {
        MyPoint[] verts = new MyPoint[vertices.size()];
        for (int i = 0; i < verts.length; i++) {
            verts[i] = vertices.get(i);
        }
        return verts;
    }

    public Shape drawPolygon() {
        MyPoint[] vertex = getVerts();
        int length = vertex.length;

        for (int i = 1; i < length; i++) {
            line.drawDDALine(vertex[i], vertex[i - 1], bufferedImage, bufferedImage.getGraphics());
        }
        line.drawDDALine(vertex[0], vertex[length - 1], bufferedImage, bufferedImage.getGraphics());
        MyPoint center = getMidPoint(vertex);
        return new Shape(vertices,center.getX(),center.getY(), bufferedImage, bufferedImage.getGraphics(),c, Shape.Figure.RECTANGLE);
    }

    public void scale(double sx, double sy) {
        MyPoint origin = vertices.get(0);
        MyPoint[] verts = getVerts();
        int i = 0;
        double scaleFactor = 0;

        if (sx != 1 || sy != 1){
            scaleFactor = sx - 1;
        }
        for (MyPoint vert : verts) {
            if (i != 0){
                vert.setX((int) (vert.getX()* sx) - (int)(origin.getX() * scaleFactor));
                vert.setY((int) (vert.getY() * sy) - (int)(origin.getY() * scaleFactor));
            }
          i++;
        }
        setVerts(verts);
    }
     public MyPoint getMidPoint(MyPoint[] points) {
         int totalX = 0;
         int totalY = 0;
         int numPoints = points.length;

         for (MyPoint p : points) {
             totalX += p.getX();
             totalY += p.getY();
         }

         int midX = totalX / numPoints;
         int midY = totalY / numPoints;

         return new MyPoint(midX, midY);
     }

    public MyPoint[] getPointsOfArc(int centerX, int centerY, int radiusX, int radiusY, double startAngle, double arcAngle) {
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(c);

        int x, y;
        int totalPoints =100; // Cantidad de puntos a lo largo del arco

        double angle = startAngle;
        double angleIncrement = arcAngle / totalPoints;

        MyPoint[] head = new MyPoint[100];
        for (int i = 0; i < totalPoints; i++) {
            x = centerX + (int) (radiusX * Math.cos(Math.toRadians(angle)));
            y = centerY + (int) (radiusY * Math.sin(Math.toRadians(angle)));
            head[i] = new MyPoint(x, y);
            angle += angleIncrement;
        }

//        Curve curve = new Curve(bufferedImage);
//        MyPoint[] pointsHand = curve.getPointsOfCurve(100,new MyPoint(25,125),10,20);
//        Polygon polygon = new Polygon(bufferedImage);
//        polygon.setVerts(pointsHand);
//        pointsHand = Transform.rotate(polygon,270);
//        MyPoint[]kirbyPoints =  Stream.concat(
//                Arrays.stream(head).limit(98),
//                Arrays.stream(pointsHand))
//                .toArray(MyPoint[]::new);
//        System.out.println(pointsHand[pointsHand.length-1]);
//        System.out.println(pointsHand[0]);

//        double starAngle = 0;
//
//        MyPoint[]head2 = new MyPoint[totalPoints];
//        for (int i = 0; i < totalPoints; i++) {
//            x = centerX + (int) (radiusX * Math.cos(Math.toRadians(starAngle)));
//            y = centerY + (int) (radiusY * -Math.sin(Math.toRadians(starAngle)));
//            head2[i] = new MyPoint(x, y);
//            startAngle += angleIncrement;
//        }
//
//        kirbyPoints =  Stream.concat(
//                        Arrays.stream(kirbyPoints),
//                        Arrays.stream(head2))
//                .toArray(MyPoint[]::new);
//        for (int i = 1; i < kirbyPoints.length; i++) {
//            line.drawDDALine(kirbyPoints[i], kirbyPoints[i - 1], bufferedImage, graphics);
//        }
//        System.out.println(kirbyPoints[0]+" "+kirbyPoints[kirbyPoints.length-1]);
////        line.drawDDALine(kirbyPoints[0], kirbyPoints[totalPoints - 1], bufferedImage, graphics);
//
//        MyPoint center2 = getMidPoint(kirbyPoints);

        return head;
    }
}

