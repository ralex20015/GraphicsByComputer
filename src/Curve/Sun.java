package Curve;

import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sun extends JPanel {
    private final BufferedImage bufferedImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Sun and flower");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Sun());
        frame.setVisible(true);
    }

    public Sun(){
        bufferedImage =  new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);
        Curve curve = new Curve(bufferedImage);
        curve.drawFlower(new MyPoint(300,300), 50, Color.RED);
        curve.drawSun(new MyPoint(100,100), 4, Color.black);
    }
}
