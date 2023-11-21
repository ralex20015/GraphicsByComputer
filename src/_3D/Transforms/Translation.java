package _3D.Transforms;

import _3D.projection.Cube;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Translation extends JPanel implements Runnable{

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Translation3D");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Translation translation = new Translation();
        frame.add(translation);
        translation.startThread();
        frame.setVisible(true);
    }


    private BufferedImage bufferedImage;
    private final MyPoint[]pointsOfCube;
    private Thread updater;
    private int tx = 2;
    private int ty = 2;
    private int tz =  2;

    public Translation(){
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
        pointsOfCube = new MyPoint[]{new MyPoint(50,150,150), new MyPoint(150,150,150),
                new MyPoint(50, 250, 150), new MyPoint(150,250,150), new MyPoint(50,150,250),
                new MyPoint(150,150,250), new MyPoint(50,250,250), new MyPoint(150,250, 250)};
    }

    public void startThread(){
        updater = new Thread(this);
        updater.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Cube cube = new Cube(bufferedImage, pointsOfCube);
        cube.setLineColor(Color.BLACK);
        cube.setBackgroundColor(Color.BLUE);
        Transform.translate(cube,tx, ty, tz);
        cube.draw(new int[]{32, 28, 80});
        reset(g);
    }

    private void reset(Graphics g) {
        g.drawImage(bufferedImage,0,0,null);
        // I need to change this
        bufferedImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void run() {
        double FPS = 60.0;
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (tx < 200) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 100_000_000) {
                drawCount = 0;
                timer = 0;
                tx = tx + 2;
                ty = ty + 2;
                tz = tz + 1;
            }
        }
    }
}
