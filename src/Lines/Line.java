package Lines;

import interfaces.IPixel;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Line implements IPixel {
    private Color c = Color.RED;

    public Line(){}
    public Line(Color color){
        this. c = color;
    }

    public void drawLineWithBresenham(int x0, int y0, int x1, int y1, BufferedImage bufferedImage, Graphics g) {
        g.drawImage(bufferedImage,0,0,null);
        int dx = x1 - x0;
        int dy = y1 - y0;

        int x = x0;
        int y = y0;

        putPixel(x, y,bufferedImage ,c);    //this putpixel is for very first pixel of the line
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
                putPixel(x, y, bufferedImage,c);
            }
        } else {
            //this is the case when slope is greater than or equal to 1  i.e: m>=1
            //x and y are reversed
            int pk = (2 * Math.abs(dx)) - Math.abs(dy);

            for (int i = 0; i < Math.abs(dy); i++) {
                y = dy < 0 ? y - 1 : y + 1;
                if (pk < 0)
                    pk = pk + (2 * Math.abs(dx));
                else {
                    x = dx < 0 ? x - 1 : x + 1;// resta para el cuadrante 1 y 3
                    pk = pk + (2 * Math.abs(dx)) - (2 * Math.abs(dy));
                }
                putPixel(x, y,bufferedImage ,c);
            }
        }
    }

    public void drawDDALine(MyPoint origin, MyPoint destination, BufferedImage bufferedImage, Graphics g) {
        int x1 = origin.getX();
        int y1 = origin.getY();
        int x2 = destination.getX();
        int y2 = destination.getY();

        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        double xIncrement = dx / (double) steps;
        double yIncrement = dy / (double) steps;

        double x = x1;
        double y = y1;

        for (int i = 0; i < steps; i++) {
            int xi = (int) Math.round(x);
            int yi = (int) Math.round(y);
            putPixel(xi, yi, bufferedImage,c);

            x += xIncrement;
            y += yIncrement;
        }
    }

    public void drawRectLine(int x0, int y0, int x1, int y1, BufferedImage bufferedImage) {
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
                putPixel(i, (int) y,bufferedImage ,c);
            }
        } else {
            int size = Math.max(y0, y1) - Math.min(y0, y1);
            for (int i = Math.min(y0, y1); i < size; i++) {
                putPixel(x0, i,bufferedImage ,c);
            }
        }
    }

    @Override
    public void changeColor(Color color) {
        if (color != null){
            c = color;
        }
    }

}
