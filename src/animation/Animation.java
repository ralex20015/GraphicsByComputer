package animation;

import Curve.Curve;
import Fill.Flood;
import Forms.Polygon;
import transforms.Transform;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

public class Animation extends JPanel implements Runnable {
    private BufferedImage bufferedImage;
    private int increment = 5;
    private int currentIncrement = 0;
    private Kirby kirby;
    private Thread anim;
    private Graphics graphics;
    private final int width;
    private final int height;
    private Sun sun;
    private int sunRotation = 15;
    public Animation(int width, int height){
        this.width = width;
        this.height = height;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        anim = new Thread(this);
        graphics = bufferedImage.createGraphics();

        kirby = new Kirby(200,150,BufferedImage.TYPE_INT_ARGB);
        kirby.setGraphics(graphics);
        kirby.drawKirby();
        sun = new Sun(100,100,BufferedImage.TYPE_INT_ARGB);
        sun.setGraphics(graphics);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//
//        p.drawPolygon();
//        p.changeColor(Color.PINK);
//        Flood.apply(p.drawPolygon());
//        p2.setVerts(headBottom);
//        p2.drawPolygon();

        landScape();
        reset(g);
    }
    private void landScape(){
        Polygon floor = new Polygon(bufferedImage);
        floor.changeColor(new Color(0,255,0,100));
        MyPoint[]floorPoints = {new MyPoint(0,400),
                new MyPoint(width,400),
                new MyPoint(width,600),
                new MyPoint(0,600)};
        floor.setVerts(floorPoints);
        Flood.apply(floor.drawPolygon());
    }

    private void reset(Graphics g) {
        g.drawImage(bufferedImage,0,0,null);
        bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
        kirby.setGraphics(graphics);
        kirby.drawKirby();
        sun.setGraphics(graphics);
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (currentIncrement < 200){
                currentIncrement += increment;
                Transform.translate(kirby.getPolygon(),5,0);
            } else if (currentIncrement < 300) {
                currentIncrement += increment;
                Transform.translate(kirby.getPolygon(),5,-5);
                //It is an interesting behavior on the buffer
//                Transform.rotate(kirby.getPolygon(),90);
            }

//            kirby.setPosition(new MyPoint(kirby.getPosition().getX() + increment,
//                    kirby.getPosition().getY()));

//            sun.setAngle(sun.getAngle() + sunRotation);
//            sun.rotateSun();
            repaint();
        }
    }

    public void startAnimation(){
        anim.start();
    }
}
