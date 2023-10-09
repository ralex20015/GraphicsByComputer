package transforms;

import Forms.Polygon;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Escalation extends JPanel {
    private BufferedImage bufferedImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new Escalation());
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Escalation(){
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);
        Polygon polygonWithoutScale = new Polygon(bufferedImage, g);
        polygonWithoutScale.addVert(new MyPoint(100,100));
        polygonWithoutScale.addVert(new MyPoint(200,100));
        polygonWithoutScale.addVert(new MyPoint(200,150));
        polygonWithoutScale.addVert(new MyPoint(100,150));
        polygonWithoutScale.changeColor(Color.BLUE);
        polygonWithoutScale.drawPolygon();

        Polygon polygonScaled = new Polygon(bufferedImage, g);
        polygonScaled.addVert(new MyPoint(100,100));
        polygonScaled.addVert(new MyPoint(200,100));
        polygonScaled.addVert(new MyPoint(200,150));
        polygonScaled.addVert(new MyPoint(100,150));
        polygonScaled.scaleWithMatrix(2,2);
        polygonScaled.drawPolygon();
    }
}
