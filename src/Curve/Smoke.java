package Curve;

import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Smoke extends JPanel {
    private final BufferedImage bufferedImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Smoke");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Smoke());
        frame.setVisible(true);
    }

    public Smoke(){
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);
        Curve curve = new Curve(bufferedImage);
//        curve.drawSmoke(new MyPoint(275, 275), new Dimension(250, 250), Color.BLACK);
//        curve.drawInfinite(100, new MyPoint(300, 400));
        curve.drawParticleMovement(new MyPoint(50,50),15,300);
    }
}
