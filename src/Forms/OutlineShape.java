package Forms;

import interfaces.IPixel;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OutlineShape implements IPixel {

    private Color c = Color.RED;

//    public Shape drawBasicCircle(int x, int y, int centerX, int centerY, BufferedImage bufferedImage, Graphics g) {
//        ArrayList<MyPoint> arrayList = new ArrayList<>();
//        g.drawImage(bufferedImage, 0, 0, null);
//        //radio al cuadrado
//        double rTo2 = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
//        int radius = (int) Math.sqrt(rTo2);
//        double root = 0;
//        double y1 = 0;
//        double y2 = 0;
//        for (int x1 = centerX - radius; x1 <= (centerX + radius); x1++) {
//            root = Math.sqrt(rTo2 - Math.pow(x1 - centerX, 2));
//            y1 = centerY + root;
//            y2 = centerY - root;
//            putPixel(x1, (int) Math.round(y1), bufferedImage, c);
//            arrayList.add(new MyPoint(x,(int) Math.round(y1)));
//            putPixel(x1, (int) Math.round(y2), bufferedImage, c);
//            arrayList.add(new MyPoint(x1,(int) Math.round(y2)));
//        }
//        return new Shape(arrayList, centerX, centerY, bufferedImage, g, c, Shape.Figure.BASIC_CIRCLE);
//    }

    public Shape drawMidPointCircle(int xc, int yc, int radius, BufferedImage bufferedImage, Graphics g) {
        ArrayList<MyPoint> arrayList = new ArrayList<>();
        g.drawImage(bufferedImage, 0, 0, null);
        int x = radius, y = 0;

        // Printing the initial point
        // on the axes after translation
        arrayList.add(new MyPoint(-x + xc,xc));
        arrayList.add(new MyPoint(x + xc,xc));

//        putPixel(xc, yc, bufferedImage, c);

        // When radius is zero only a single
        // point will be printed
        if (radius > 0) {
            putPixel(x + xc, -y + yc, bufferedImage, c);
            putPixel(y + xc, x + yc, bufferedImage, c);
            putPixel(-y + xc, x + yc, bufferedImage, c);
        }

        // Initialising the value of P
        int P = 1 - radius;
        while (x > y) {
            y++;
            // Mid-point is inside or on the perimeter
            if (P <= 0) {
                P = P + 2 * y + 1;
            }// Mid-point is outside the perimeter
            else {
                x--;
                P = P + 2 * y - 2 * x + 1;
            }

            // All the perimeter points have already
            // been printed
            if (x < y)
                break;

            // Printing the generated point and its
            // reflection in the other octants after
            // translation
            arrayList.add(new MyPoint(x + xc, y + yc));
            arrayList.add(new MyPoint(-x + xc, y + yc));
            arrayList.add(new MyPoint(x + xc, -y + yc));
            arrayList.add(new MyPoint(x + xc, -y + yc));

            putPixel(x + xc, y + yc, bufferedImage, c);
            putPixel(-x + xc, y + yc, bufferedImage, c);
            putPixel(x + xc, -y + yc, bufferedImage, c);
            putPixel(-x + xc, -y + yc, bufferedImage, c);

            // If the generated point is on the
            // line x = y then the perimeter points
            // have already been printed
            if (x != y) {
                arrayList.add(new MyPoint(y + xc, x + yc));
                arrayList.add(new MyPoint(-y + xc, x + yc));
                arrayList.add(new MyPoint(y + xc, -x + yc));
                arrayList.add(new MyPoint(-y + xc, -x + yc));

                putPixel(y + xc, x + yc, bufferedImage, c);
                putPixel(-y + xc, x + yc, bufferedImage, c);
                putPixel(y + xc, -x + yc, bufferedImage, c);
                putPixel(-y + xc, -x + yc, bufferedImage, c);
            }
        }

        return new Shape(arrayList, xc, yc, bufferedImage, g, c, Shape.Figure.MID_POINT_CIRCLE);
    }

    public Shape drawCircleWithPolarCoordinates(int centerX, int centerY,
                                                int radius, BufferedImage bufferedImage, Graphics g) {
        double theta = 0;
        double dTheta = 1.0 / radius;
        ArrayList<MyPoint> points = new ArrayList<>();
        int x, y;

        while (theta <= Math.PI / 4) {
            x = (int) Math.round(radius * Math.cos(theta));
            y = (int) Math.round(radius * Math.sin(theta));

            points.add(new MyPoint(centerX + x,centerY + y));
            points.add(new MyPoint(centerX - x,centerY + y));
            points.add(new MyPoint(centerX + y,centerY + x));
            points.add(new MyPoint(centerX - y,centerY + x));
            points.add(new MyPoint(centerX + x, centerY - y));
            points.add(new MyPoint(centerX - x, centerY - y));
            points.add(new MyPoint(centerX - y, centerY - x));
            points.add(new MyPoint(centerX + y, centerY - x));


            putPixel(centerX + x, centerY + y, bufferedImage, c);
            putPixel(centerX + y, centerY + x, bufferedImage, c);
            putPixel(centerX - y, centerY + x, bufferedImage, c);
            putPixel(centerX - x, centerY + y, bufferedImage , c);
            putPixel(centerX - x, centerY - y, bufferedImage, c);
            putPixel(centerX - y, centerY - x, bufferedImage, c);
            putPixel(centerX + y, centerY - x, bufferedImage, c);
            putPixel(centerX + x, centerY - y, bufferedImage, c);

            theta += dTheta;
        }
        return new Shape(points, centerX, centerY, bufferedImage, g, c, Shape.Figure.POLAR_CIRCLE);
    }

    public Shape drawEllipse(int xc, int yc, int rx, int ry, BufferedImage bufferedImage, Graphics g) {
        ArrayList<MyPoint> arrayList = new ArrayList<>();
        // x2/b2 + y2/a2 = 1 || x2/a2 + y2/b2 = 1
        //a =
        double dx, dy, d1, d2, x, y;
        x = 0;
        y = ry;

        // Initial decision parameter of region 1
        d1 = (ry * ry) - (rx * rx * ry) +
                (0.25 * rx * rx);
        dx = 2 * ry * ry * x;
        dy = 2 * rx * rx * y;

        // For region 1
        while (dx < dy) {

            // Print points based on 4-way symmetry
            arrayList.add(new MyPoint((int) x + xc,(int) y + yc));
            arrayList.add(new MyPoint((int) -x + xc, (int) y + yc));
            arrayList.add(new MyPoint((int) x + xc,(int) -y + yc ));
            arrayList.add(new MyPoint((int) -x + xc, (int) -y + yc));

            putPixel((int) x + xc, (int) y + yc, bufferedImage, c);
            putPixel((int) -x + xc, (int) y + yc, bufferedImage, c);
            putPixel((int) x + xc, (int) -y + yc, bufferedImage, c);
            putPixel((int) -x + xc, (int) -y + yc, bufferedImage, c);
            // Checking and updating value of
            // decision parameter based on algorithm
            if (d1 < 0) {
                x++;
                dx = dx + (2 * ry * ry);
                d1 = d1 + dx + (ry * ry);
            } else {
                x++;
                y--;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d1 = d1 + dx - dy + (ry * ry);
            }
        }

        // Decision parameter of region 2
        d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) +
                ((rx * rx) * ((y - 1) * (y - 1))) -
                (rx * rx * ry * ry);

        // Plotting points of region 2
        while (y >= 0) {

            // Print points based on 4-way symmetry

            arrayList.add(new MyPoint((int) x + xc,(int) y + yc));
            arrayList.add(new MyPoint((int) -x + xc, (int) y + yc));
            arrayList.add(new MyPoint((int) x + xc,(int) -y + yc ));
            arrayList.add(new MyPoint((int) -x + xc, (int) -y + yc));

            putPixel((int) x + xc, (int) y + yc, bufferedImage, c);
            putPixel((int) -x + xc, (int) y + yc, bufferedImage, c);
            putPixel((int) x + xc, (int) -y + yc, bufferedImage, c);
            putPixel((int) -x + xc, (int) -y + yc, bufferedImage, c);

            // Checking and updating parameter
            // value based on algorithm
            if (d2 > 0) {
                y--;
                dy = dy - (2 * rx * rx);
                d2 = d2 + (rx * rx) - dy;
            } else {
                y--;
                x++;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d2 = d2 + dx - dy + (rx * rx);
            }
        }
        return new Shape(arrayList, xc, yc, bufferedImage, g, c, Shape.Figure.ELLIPSE);
    }

    public Shape drawRectangle(int x0, int y0, int width, int height, BufferedImage bufferedImage, Graphics g) {
        ArrayList<MyPoint> arrayList = new ArrayList<>();
        int oppositeXCorner = x0 + width;
        int oppositeYCorner = y0 + height;


        for (int x = x0; x <= oppositeXCorner; x++) {
            arrayList.add(new MyPoint(x,y0));
            arrayList.add(new MyPoint(x,oppositeYCorner));
            putPixel(x, y0, bufferedImage, c); // Lado superior
            putPixel(x, oppositeYCorner, bufferedImage, c); // Lado inferior
        }
        for (int y = y0; y <= oppositeYCorner; y++) {
            putPixel(x0, y, bufferedImage, c); // Lado izquierdo
            putPixel(oppositeXCorner, y, bufferedImage, c); // Lado derecho
        }
        return new Shape(arrayList, x0 + (width / 2), y0 + (height / 2), bufferedImage, g, c, Shape.Figure.RECTANGLE);
    }

    public Shape drawBasicCircle(int xc, int yc, int radius, BufferedImage bufferedImage, Graphics g) {
        ArrayList<MyPoint>arrayList = new ArrayList<>();
        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y) {
            arrayList.add(new MyPoint(xc + x, yc + y));
            arrayList.add(new MyPoint(xc - x,yc + y));
            arrayList.add(new MyPoint(xc + y, yc + x));
            arrayList.add(new MyPoint(xc - y, yc + x));
            arrayList.add(new MyPoint(xc + x,yc - y));
            arrayList.add(new MyPoint(xc - x, yc - y));
            arrayList.add(new MyPoint(xc - y,yc - x));
            arrayList.add(new MyPoint(xc + y, yc - x));

            putPixel(xc + x, yc + y, bufferedImage, c);//
            putPixel(xc + y, yc + x, bufferedImage, c);//
            putPixel(xc - y, yc + x, bufferedImage, c);
            putPixel(xc - x, yc + y, bufferedImage, c);
            putPixel(xc - x, yc - y, bufferedImage, c);
            putPixel(xc - y, yc - x, bufferedImage, c);
            putPixel(xc + y, yc - x, bufferedImage, c);
            putPixel(xc + x, yc - y, bufferedImage, c);
            y += 1;
            err += 1 + 2 * y;
            if (2 * (err - x) + 1 > 0) {
                x -= 1;
                err += 1 - 2 * x;
            }
        }
        return new Shape(arrayList, xc, yc, bufferedImage, g, c, Shape.Figure.BASIC_CIRCLE);
    }

    @Override
    public void putPixel(int x, int y, BufferedImage bufferedImage, Color c) {
        if ((x < bufferedImage.getWidth() && x > -1) && (y < bufferedImage.getHeight() && y > -1)) {
            IPixel.super.putPixel(x, y, bufferedImage, c);
        }
    }

    @Override
    public void changeColor(Color color) {
        if (color != null) {
            c = color;
        }
    }
}
