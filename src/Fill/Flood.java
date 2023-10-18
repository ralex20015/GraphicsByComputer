package Fill;

import Forms.Shape;
import Lines.Line;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Stack;
import java.util.function.IntPredicate;

public class Flood {
    private static  Color c;
    public static void apply(Shape shape){
        floodFill(shape.getXCenter(),shape.getYCenter(),shape);
    }
    public static void apply(Shape shape, int[]colorToCompare){
        floodFill(shape.getXCenter(),shape.getYCenter(),shape,colorToCompare);
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

                if (validCoordinates(px,py,width,height) && getRGB(px, py,img) == startColor) {
                    line.putPixel(px,py,img,fillColor);

                    stack.push(new MyPoint(px - 1, py));
                    stack.push(new MyPoint(px + 1, py));
                    stack.push(new MyPoint(px, py - 1));
                    stack.push(new MyPoint(px, py + 1));
                }
            }
        }
    }

    private static void floodFill(int x, int y, Shape shape,int[]colorsToCompare){
        Color fillColor = shape.getColor();
        Line line = new Line(fillColor);
        BufferedImage img = shape.getBufferedImage();
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            int width = img.getWidth();
            int height = img.getHeight();

            if (isNotTheSameColor(fillColor.getRGB(), colorsToCompare)) {
                return;
            }

            Stack<MyPoint> stack = new Stack<>();
            stack.push(new MyPoint(x, y));

            while (!stack.empty()) {
                MyPoint p = stack.pop();
                int px = p.getX();
                int py = p.getY();
                //Make a predicate
                if (isValid(px,py,width,height,colorsToCompare,img)) {
                    line.putPixel(px,py,img,fillColor);
                    stack.push(new MyPoint(px - 1, py));
                    stack.push(new MyPoint(px + 1, py));
                    stack.push(new MyPoint(px, py - 1));
                    stack.push(new MyPoint(px, py + 1));
                }
            }
        }
    }
    public static void setColor(Color color){
        c = color;
    }

    private static int getRGB(int x, int y, BufferedImage bufferedImage) {
        if ((x < bufferedImage.getWidth() && x > -1) && (y < bufferedImage.getHeight() && y > -1)) {
            return bufferedImage.getRGB(x, y);
        } else {
            return 0;
        }
    }

    private static boolean validCoordinates(int px, int py, int width, int height){
        IntPredicate xLessThanWidth = x -> x < width;
        IntPredicate xGreaterThan = x -> x > -1;
        IntPredicate yLessThanHeight = y -> y < height;
        IntPredicate yGreaterThan = y -> y > -1;
        return xLessThanWidth.and(xGreaterThan).test(px) && yLessThanHeight.and(yGreaterThan).test(py);
    }

    private static boolean isValid(int px, int py, int width, int height,
                                   int[] colorsToCompare, BufferedImage bufferedImage) {
        return validCoordinates(px, py, width, height) &&
                validColors(px,py,colorsToCompare,bufferedImage);
    }

    private static boolean validColors(int px, int py, int[]colorsToCompare,
                                       BufferedImage bufferedImage){
        IntPredicate valid = color -> getRGB(px, py, bufferedImage) == color;
        return  Arrays.stream(colorsToCompare).anyMatch(valid);
    }

    private static boolean isNotTheSameColor(int fillColor, int []colorsToCompare){
        IntPredicate valid = color -> fillColor == color;
        return Arrays.stream(colorsToCompare).anyMatch(valid);
    }
}
