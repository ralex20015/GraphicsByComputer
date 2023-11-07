package _3D.projection;


import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ParallelProjection extends JPanel {

    private final BufferedImage bufferedImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Parallel Oblique Projection");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ParallelProjection());
        frame.setVisible(true);
    }

    public ParallelProjection(){
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);
        Worm worm = new Worm(bufferedImage,new MyPoint(100,100));
        worm.changeColor(Color.BLACK);
        worm.draw();
    }
}
