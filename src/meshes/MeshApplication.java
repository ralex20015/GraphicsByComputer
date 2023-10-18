package meshes;

import Fill.Flood;
import Forms.Polygon;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MeshApplication extends JPanel {
    private BufferedImage bufferedImage;
    private int width = 200;
    private int height = 200;
    private int x0 = 100;
    private int y0 = 100;
    private int interval = width/10;
    private int []y;
    private int []x;
    private MyPoint[]productCartesian;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Mallado");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MeshApplication());
        frame.setVisible(true);
    }

    public MeshApplication(){
        
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
        x = IntStream.range(1,10)
                .map(number -> number * interval)
                .toArray();
        y = IntStream.range(1,10)
                .map(number -> number * interval)
                .toArray();
        int size = x.length * y.length;
        productCartesian = Stream.generate(()->new MyPoint(0,0))
                .limit(size)
                .toArray(MyPoint[]::new);
//        MyPoint[]a = Arrays.stream(productCartesian).toArray();
//        System.out.println(Arrays.toString(productCartesian));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0,0,null);
        Polygon polygon = new Polygon(bufferedImage);
        polygon.addVert(new MyPoint(x0, y0));
        polygon.addVert(new MyPoint(x0 + width, y0));
        polygon.addVert(new MyPoint(x0 + width, y0 + height));
        polygon.addVert(new MyPoint(x0, y0 + height));
        Flood.apply(polygon.drawPolygon());
    }
}
