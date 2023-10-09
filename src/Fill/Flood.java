package Fill;

import Forms.Shape;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Flood {
    public static void apply(Shape shape){
        floodFill(shape.getXCenter(),shape.getYCenter(),shape);
    }
    private static void floodFill(int x, int y, Shape shape) {
        Color fillColor = shape.getColor();
        Line line = new Line(fillColor);
        BufferedImage img = shape.getBufferedImage();
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            int width = img.getWidth();
            int height = img.getHeight();

            int startColor = getRGB(x, y,img);
            if (startColor == fillColor.getRGB()) {
                return;
            }

            Stack<MyPoint> stack = new Stack<>();
            stack.push(new MyPoint(x, y));

            while (!stack.empty()) {
                MyPoint p = stack.pop();
                int px = p.getX();
                int py = p.getY();

                if (px >= 0 && px < width && py >= 0 && py < height && getRGB(px, py,img) == startColor) {
                    line.putPixel(px,py,img,fillColor);

                    stack.push(new MyPoint(px - 1, py));
                    stack.push(new MyPoint(px + 1, py));
                    stack.push(new MyPoint(px, py - 1));
                    stack.push(new MyPoint(px, py + 1));
                }
            }
        }

    }

    private static int getRGB(int x, int y, BufferedImage bufferedImage) {
        if ((x < bufferedImage.getWidth() && x > -1) && (y < bufferedImage.getHeight() && y > -1)) {
            return bufferedImage.getRGB(x, y);
        } else {
            return 0;
        }
    }


}
