package Curve;

import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Curves extends JPanel {
    private final BufferedImage buffer;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Curves");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Curves());
        frame.setVisible(true);
    }

    public Curves(){
        buffer = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer,0,0,null);
        Curve curve =  new Curve(buffer);
        curve.drawParable(8, new MyPoint(30,450),80,400);
        curve.drawParable(100, new MyPoint(300,450),80,400);
    }
}
