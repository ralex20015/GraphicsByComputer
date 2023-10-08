package Figuras_Terminado;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomGraphics{

    private final BufferedImage bufferedImage;
    private final JFrame frame;

    public CustomGraphics(JFrame frame) {
        this.frame = frame;
        bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(long x, long y, Color c) {
        bufferedImage.setRGB(0, 0, c.getRGB());
        //Establecemos las coordenadas del bufferedImage
        frame.getGraphics().drawImage(bufferedImage,(int)x,(int)y,frame);
    }

    public void drawRectLine(int x0, int y0, int x1, int y1, Color c) {
        // y = mx + b; b = x1
        // y - y1 = m(x - x1)
        //y = mx - mx1 + y1;
//        int dx = x1  x0;
        double y = 0;
        double m = ((double) (y1 - y0)) / ((double) (x1 - x0));

        if (x1 - x0 != 0) {
            int minX = Math.min(x0, x1);
            int maxX = Math.max(x0, x1);

            for (int i = minX; i < maxX; i++) {
                y = (m * i) - (m * x1) + y1;
                System.out.println("x = " + i);
                putPixel(i, (int) y, c);
            }
        } else {
            int size = Math.max(y0, y1) - Math.min(y0, y1);
            for (int i = Math.min(y0, y1); i < size; i++) {
                putPixel(x0, i, c);
            }
        }
        System.out.println(m);
    }

    public void drawRectangle(int x0, int y0, int width, int height, Color c) {
        int oppositeXCorner = x0 + width;
        int oppositeYCorner = y0 + height;

        for (int x = x0; x <= oppositeXCorner; x++) {
            putPixel(x, y0, c); // Lado superior
            putPixel(x, oppositeYCorner, c); // Lado inferior
        }

        for (int y = y0; y <= oppositeYCorner; y++) {
            putPixel(x0, y, c); // Lado izquierdo
            putPixel(oppositeXCorner, y, c); // Lado derecho
        }
    }

    public void drawDDALine(int x0, int y0, int x1, int y1, Color color) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = 0;
        double xinc = 0;
        double yinc = 0;

        steps = Math.max(Math.abs(dx), Math.abs(dy));

        xinc = (double) dx / steps;
        yinc = (double) dy / steps;
        double x = x0;
        double y = y0;
        putPixel(Math.round(x), Math.round(y), color);
        for (int i = 1; i <= steps; i++) {
            x = x + xinc;
            y = y + yinc;
            putPixel(Math.round(x), Math.round(y), color);
        }
    }

    public void drawBasicCircle(int x, int y, int centerX, int centerY, Color c) {
        //radio al cuadrado
        double rTo2 = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        int radius = (int) Math.sqrt(rTo2);
        double root = 0;
        double y1 = 0;
        double y2 = 0;
        for (int x1 = centerX - radius; x1 <= (centerX + radius); x1++) {
            root = Math.sqrt(rTo2 - Math.pow(x1 - centerX, 2));
            y1 = centerY + root;
//            System.out.println(x1);
//            System.out.println("y1 = "+y1);
            y2 = centerY - root;
//            System.out.println("y2 = "+y2);
            putPixel(x1, Math.round(y1), c);
            putPixel(x1, Math.round(y2), c);
        }
    }

    public void drawLineWithBresenham(int x0, int y0, int x1, int y1, Color c) {
//        /*case 1 pk < 0
//        pk + 1 = pk + a
//        xk + 1 = xk + 1;
//        yk + 1 = yk;
//        */
//
//        /*case 2 pk >= 0
//        pk + 1 = pk + b
//        xk + 1 = xk + 1;
//        yk + 1 = yk + 1;
//        */
        int dx = x1 - x0;
        int dy = y1 - y0;

        int x = x0;
        int y = y0;

        putPixel(x, y, c);    //this putpixel is for very first pixel of the line
        //this is the case when slope(m) < 1
        if (Math.abs(dx) > Math.abs(dy)) {
            int pk = (2 * Math.abs(dy)) - Math.abs(dx);

            for (int i = 0; i < Math.abs(dx); i++) {
                x = dx < 0 ? x - 1 : x + 1;
                if (pk < 0)
                    pk = pk + (2 * Math.abs(dy));
                else {
                    y = dy < 0 ? y - 1 : y + 1;
                    pk = pk + (2 * Math.abs(dy)) - (2 * Math.abs(dx));
                }
                putPixel(x, y, c);
            }
        } else {
            //this is the case when slope is greater than or equal to 1  i.e: m>=1
            //x and y are reversed
            int pk = (2 * Math.abs(dx)) - Math.abs(dy);

            for (int i = 0; i < Math.abs(dy); i++) {
                y = dy < 0 ? y - 1 : y + 1;
//                System.out.println(y);
                if (pk < 0)
                    pk = pk + (2 * Math.abs(dx));
                else {
                    x = dx < 0 ? x - 1 : x + 1;// resta para el cuadrante 1 y 3
                    pk = pk + (2 * Math.abs(dx)) - (2 * Math.abs(dy));
                }
                putPixel(x, y, c);
            }
        }
    }

    public void drawCircleWithPolarCoordinates(int centerX, int centerY, int radius, Color c) {
        //Es para ver que tan chicos seran los pasos a menor valor mas remarcado sera el circulo pero tardara mas en dibujarse
        double thetaStep = 0.02;
        for (double theta = 0; theta < 2 * Math.PI; theta += thetaStep) {
            int x = (int) (radius * Math.cos(theta)) + centerX;
            int y = (int) (radius * Math.sin(theta)) + centerY;
            putPixel(x, y, c);
        }
    }

    public void drawMidPointLine(int x0, int y0, int x1, int y1, Color c) {
//        a = dy;
//        b = -dx
//        M > 0 select xp+1, yp+1;
//        M < 0 select xp+1, yp;
        // calculate dx & dy
    }

    public void drawMidPointCircle(int xc, int yc, int radius, Color c) {
        int x = radius, y = 0;

        // Printing the initial point
        // on the axes after translation
        putPixel(xc, yc, c);

        // When radius is zero only a single
        // point will be printed
        if (radius > 0) {
            putPixel(x + xc, -y + yc, c);
            putPixel(y + xc, x + yc, c);
            putPixel(-y + xc, x + yc, c);
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
            putPixel(x + xc, y + yc, c);
            putPixel(-x + xc, y + yc, c);
            putPixel(x + xc, -y + yc, c);
            putPixel(-x + xc, -y + yc, c);

            // If the generated point is on the
            // line x = y then the perimeter points
            // have already been printed
            if (x != y) {
                putPixel(y + xc, x + yc, c);
                putPixel(-y + xc, x + yc, c);
                putPixel(y + xc, -x + yc, c);
                putPixel(-y + xc, -x + yc, c);
            }
        }
    }

    public void drawEllipse(int xc, int yc, int rx, int ry) {
        // x2/b2 + y2/a2 = 1 || x2/a2 + y2/b2 = 1
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
            putPixel((int) x + xc, (int) y + yc, Color.GREEN);
            putPixel((int) -x + xc, (int) y + yc, Color.GREEN);
            putPixel((int) x + xc, (int) -y + yc, Color.GREEN);
            putPixel((int) -x + xc, (int) -y + yc, Color.GREEN);

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
            putPixel((int) x + xc, (int) y + yc, Color.GREEN);
            putPixel((int) -x + xc, (int) y + yc, Color.GREEN);
            putPixel((int) x + xc, (int) -y + yc, Color.GREEN);
            putPixel((int) -x + xc, (int) -y + yc, Color.GREEN);

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
    }
}
