package Fill;

import Forms.Shape;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {

    public static void apply(Shape shape) {
        Line line = new Line(shape.getColor());
        ArrayList<MyPoint> points = shape.getArrayList();

        int size = (points.size());
        int i = 0;
        int j = 1;
        while (i < size) {
            if (j + 1 < size) {
                line.drawDDALine(points.get(i), points.get(j),
                        shape.getBufferedImage(), shape.getGraphics());
            }
            i = j + 1;
            j = i + 1;
        }
    }

    public static void apply(MyPoint[]vertices, Shape shape) {
        Color fillColor = shape.getColor();
        BufferedImage img = shape.getBufferedImage();
        Line line = new Line(fillColor);
        int minY = img.getWidth();
        int maxY = 0;
        int minX = img.getWidth();
        int maxX = 0;

        for (MyPoint vert : vertices) {
            if (vert.getY() < minY) {
                minY = vert.getY();
            }
            if (vert.getY() > maxY) {
                maxY = vert.getY();
            }
            if (vert.getX() < minX) {
                minX = vert.getX();
            }
            if (vert.getX() > maxX) {
                maxX = vert.getX();
            }
        }

        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersections = new ArrayList<>();

            for (int i = 0; i < vertices.length; i++) {
                MyPoint p1 = vertices[i];
                MyPoint p2 = vertices[(i + 1) % vertices.length];

                if ((p1.getY() <= y && p2.getY() > y) || (p1.getY() > y && p2.getY() <= y)) {
                    double x = p1.getX() + (double) (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
                    intersections.add((int) Math.round(x));
                }
            }

            Collections.sort(intersections);

            for (int i = 0; i < intersections.size() - 1; i += 2) {
                int x1 = intersections.get(i);
                int x2 = intersections.get(i + 1);

                line.drawDDALine(new MyPoint(x1, y), new MyPoint(x2, y), img, img.getGraphics());
            }
        }
    }
}
