package _3D.Transforms;

import _3D.projection.Cube;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Escalation extends JPanel implements Runnable {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.setTitle("Escalation3D");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Escalation escalation = new Escalation();
        frame.add(escalation);
        escalation.startThread();
        frame.setVisible(true);
    }

    private BufferedImage bufferedImage;
    private final MyPoint[]pointsOfCube;
    private Thread updater;
    private double scale = 1.2;
    private boolean enteredToThat = false;

    public Escalation(){
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
        Transform.scale(cube, scale, scale, scale);
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

        while (updater != null) {
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

                if (scale >= 3){
                    enteredToThat = true;
                }
                if (!enteredToThat){
                    scale = scale + 0.1;
                }else{
                    scale = scale - 0.1;
                    if (scale <= 0.8){
                        enteredToThat = false;
                    }
                }
            }
        }
    }
}
