package Forms;

import Fill.Flood;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;


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

//    public void translate(Point dif) {
//        Point[] verts = getVerts();
//        for (Point vert : verts) {
//            vert.x = vert.x + dif.x;
//            vert.y = vert.y + dif.y;
//        }
//        setVerts(verts);
//    }

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
}

