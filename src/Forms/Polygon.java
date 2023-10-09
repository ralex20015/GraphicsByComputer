package Forms;

import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;


public class Polygon {

    private ArrayList<MyPoint> vertices;
    private final Line line;
    private Color c;
    private final BufferedImage bufferedImage;
    private final Graphics g;

    public Polygon(BufferedImage bufferedImage, Graphics g) {
        c = Color.RED;
        this.bufferedImage = bufferedImage;
        this.g = g;
        vertices = new ArrayList<>();
        line = new Line(c);
    }

    public void changeColor(Color color){
        if (color != null){
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
        int lenght = vertex.length;

        for (int i = 1; i < lenght; i++) {
            line.drawDDALine(vertex[i], vertex[i - 1], bufferedImage, g);
        }
        line.drawDDALine(vertex[0], vertex[lenght - 1], bufferedImage, g);
        MyPoint center = getMidPoint(vertex);
        return new Shape(vertices,center.getX(),center.getY(),bufferedImage,g,c, Shape.Figure.RECTANGLE);
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
//        for (MyPoint point:verts) {
//            System.out.println("x = "+point.getX()+", y = "+point.getY());
//        }
        setVerts(verts);
    }

    public void scaleWithMatrix(double sx, double sy){
        double [][]matrixOfScale= {
                {sx, 0, 0},
                {0, sy, 0},
                {0, 0, 1}
        };
        MyPoint[]vertex = getVerts();
        for (int i = 0; i < vertex.length; i++) {
            double[] currentPoint = {
                    vertex[i].getX(),
                    vertex[i].getY(),
                    1
            };
            for (int j = 3; j <= 9 ; j+=3) {
                int k= 0;
                int l= 1;
                int m = 2;
                if (j == 3){
                    int result = (int) (currentPoint[k] * matrixOfScale[k][k]);
                    vertex[i].setX(result);
                }else if(j == 6){
                    int result = (int) (currentPoint[l] * matrixOfScale[l][l]);
                    vertex[i].setY(result);
                }
            }
        }
        setVerts(vertex);
    }

     private MyPoint getMidPoint(MyPoint[] points) {
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

    public void rotate(double angle, MyPoint pivot) {
        MyPoint[] vertex = getVerts();
        MyPoint[] rotatedVertices = new MyPoint[vertex.length];

        double radians = Math.toRadians(angle);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        for (int i = 0; i < vertex.length; i++) {
            rotatedVertices[i] = new MyPoint(
                    vertex[i].getX() - pivot.getX(),
                    vertex[i].getY() - pivot.getY()
            );
        }

        for (MyPoint myPoint : rotatedVertices) {
            double x = myPoint.getX();
            double y = myPoint.getY();
            myPoint.setX((int) (x * cos - y * sin));
            myPoint.setY((int) (x * sin + y * cos));
        }

        for (MyPoint rotatedVertex : rotatedVertices) {
            rotatedVertex.setX(pivot.getX() + rotatedVertex.getX());
            rotatedVertex.setY(pivot.getY() + rotatedVertex.getY());
        }

        setVerts(rotatedVertices);
    }

    public void rotate(double angle){
        MyPoint[] vertex = getVerts();
        MyPoint[] rotatedVertices = new MyPoint[vertex.length];

        for (int i = 0; i < vertex.length; i++) {
            rotatedVertices[i] = new MyPoint(0,0);
        }

        double radians = Math.toRadians(angle);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        double[][] rotationMatrix = {
                {cos, -sin, 0},
                {sin , cos, 0},
                {0, 0, 1}
        };
        MyPoint center = getMidPoint(vertex);

        int xOfCurrentVertex = 0;
        int yOfCurrentVertex = 1;
        int one = 2;
        for (int i = 0; i < vertex.length; i++) {
            int [] pointToRotate = {
                    vertex[i].getX(),
                    vertex[i].getY(),
                    1
            };
            for (int j = 3; j <= 9; j+=3) {
                int k = 0;
                int l = 1;
                int m = 2;

                if (j == 3) {
                    int result =  (int)((pointToRotate[xOfCurrentVertex] - center.getX()) *  rotationMatrix[k][k]) +
                            (int)((pointToRotate[yOfCurrentVertex] - center.getY()) * rotationMatrix[k][l]) +
                            (int)(pointToRotate[one] * rotationMatrix[k][m]) + center.getX();
                    rotatedVertices[i].setX(result);
                }else if(j == 6){
                    int result =  (int)((pointToRotate[xOfCurrentVertex] - center.getX()) *  rotationMatrix[l][k]) +
                            (int)((pointToRotate[yOfCurrentVertex] - center.getY()) * rotationMatrix[l][l]) +
                            (int)(pointToRotate[one] * rotationMatrix[l][m]) + center.getY();
                    rotatedVertices[i].setY(result);
                }
            }
        }
        setVerts(rotatedVertices);
    }
}

