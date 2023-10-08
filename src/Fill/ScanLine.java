package Fill;

import Forms.Shape;
import Lines.Line;
import utilities.MyPoint;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanLine {

    public static void apply(Shape shape) {
        Line line = new Line(shape.getColor());
        ArrayList<MyPoint> points = shape.getArrayList();

        int size = (points.size());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            int i = 0;
            int j = 1;
            while (i < size){
                if (j+ 1 < size){
                    line.drawLineWithBresenham(points.get(i).getX(), points.get(i).getY(),
                            points.get(j).getX(), points.get(j).getY(),
                            shape.getBufferedImage(), shape.getGraphics());
                }
                i = j + 1;
                j = i+ 1;
            }
        });
        executorService.shutdown();
    }
}
