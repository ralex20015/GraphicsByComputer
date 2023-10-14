package Forms;

import Fill.Flood;
import Fill.ScanLine;
import transforms.Transform;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TrianglesAndRhombus extends JPanel {
    private final BufferedImage bufferedImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Triangulos y rombos");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TrianglesAndRhombus());
        frame.setVisible(true);
    }

    public TrianglesAndRhombus(){
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);

        Polygon triangle = new Polygon(bufferedImage);
        triangle.changeColor(Color.green);
        triangle.addVert(new MyPoint(100,300));
        triangle.addVert(new MyPoint(300,300));
        triangle.addVert(new MyPoint(200,100));
        Transform.scaleWithMatrix(triangle, 0.5, 0.5);
//        Transform.translate(triangle, -10, -200);
        ScanLine.apply(triangle.getVerts(), triangle.drawPolygon());

        Polygon rhombus = new Polygon(bufferedImage);
        rhombus.addVert(new MyPoint(400,200));
        rhombus.addVert(new MyPoint(500,300));
        rhombus.addVert(new MyPoint(400,400));
        rhombus.addVert(new MyPoint(300,300));
        Flood.apply(rhombus.drawPolygon());

        Polygon rhomboid = new Polygon(bufferedImage);
        rhomboid.changeColor(Color.BLUE);
        rhomboid.addVert(new MyPoint(200,200));
        rhomboid.addVert(new MyPoint(250,100));
        rhomboid.addVert(new MyPoint(400,100));
        rhomboid.addVert(new MyPoint(350,200));
        Flood.apply(rhomboid.drawPolygon());
    }
}
