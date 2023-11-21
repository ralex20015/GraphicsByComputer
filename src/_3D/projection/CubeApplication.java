package _3D.projection;

import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CubeApplication extends JPanel {
    private final BufferedImage bufferedImage;
    private final MyPoint[]pointsOfCube;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Cube");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CubeApplication());
        frame.setVisible(true);
    }

    public CubeApplication(){
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
        pointsOfCube = new MyPoint[]{new MyPoint(50,150,150), new MyPoint(150,150,150),
                new MyPoint(50, 250, 150), new MyPoint(150,250,150), new MyPoint(50,150,250),
                new MyPoint(150,150,250), new MyPoint(50,250,250), new MyPoint(150,250, 250)};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);
        Cube cube = new Cube(bufferedImage, pointsOfCube);
        cube.setLineColor(Color.BLACK);
        cube.setBackgroundColor(Color.BLUE);
        cube.draw(new int[]{-32, 28, 80});
//        cube.drawWithPerspective(new int[]{100, -300, 300});
    }
}
