package animation;

import Fill.Flood;
import Fill.ScanLine;
import Forms.Polygon;
import animation.elements.Kirby;
import animation.elements.Sun;
import transforms.Transform;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    private int seconds = 0;
    private int secondsMillis = 0;
    private Polygon canvasOfAnimation;
    public Animation(int width, int height){
        this.width = width;
        this.height = height;
        bufferedImage = new BufferedImage(5000, 1200, BufferedImage.TYPE_INT_ARGB);
        anim = new Thread(this);
        graphics = bufferedImage.createGraphics();

        kirby = new Kirby(200,150,BufferedImage.TYPE_INT_ARGB);
        kirby.setGraphics(graphics);
        kirby.drawKirby();
        sun = new Sun(100,100,BufferedImage.TYPE_INT_ARGB);
        sun.setGraphics(graphics);
        canvasOfAnimation = new Polygon(bufferedImage);
        canvasOfAnimation.addVert(new MyPoint(0,0));
        canvasOfAnimation.addVert(new MyPoint(width,0));
        canvasOfAnimation.addVert(new MyPoint(width,height));
        canvasOfAnimation.addVert(new MyPoint(0,height));
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
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(seconds),500,100);
        reset(g);
    }
    private void landScape(){
        Polygon floor = new Polygon(bufferedImage);
        floor.changeColor(new Color(0,255,0,100));
        MyPoint[]floorPoints = {new MyPoint(0,400),
                new MyPoint(5000,400),
                new MyPoint(5000,600),
                new MyPoint(0,600)};
        floor.setVerts(floorPoints);
        ScanLine.apply(floor.getVerts(),floor.drawPolygon());
        Polygon plateau = new Polygon(bufferedImage);
        plateau.changeColor(new Color(0,255,0,100));
        plateau.setVerts(new MyPoint[]{new MyPoint(610,300),
                new MyPoint(750,300), new MyPoint(750,400), new MyPoint(610,400)});
        Flood.apply(plateau.drawPolygon());
//        plateau.drawPolygon();
//        Polygon grass = new Polygon(bufferedImage);
//        grass
    }

    private void reset(Graphics g) {
        MyPoint[]canvas = canvasOfAnimation.getVerts();
        g.drawImage(bufferedImage,canvas[0].getX(),canvas[0].getY(),null);
        bufferedImage = new BufferedImage(5000,1200,BufferedImage.TYPE_INT_ARGB);
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
            currentIncrement += increment;
            kirby.moveRight(currentIncrement,0,270);
            sun.moveRight(currentIncrement,0,435);
            kirby.jumpRight(currentIncrement,270,405, kirby.getPolygon());
            kirby.moveRight(currentIncrement,440,480);

            kirby.moveRight(currentIncrement,550,670);
            sun.moveRight(currentIncrement,545,645);
            kirby.fall(currentIncrement,650,720,kirby.getPolygon());

            sun.moveRight(currentIncrement,715,765);
            kirby.moveRight(currentIncrement,720,770);

            if (currentIncrement < 270){
                //Caminar hacia adelante
//                Transform.translate(kirby.getPolygon(),5,0);
                Transform.translate(canvasOfAnimation,-5,0);
            } else if (currentIncrement < 405) {
                //Brincar hacia adelante
                Transform.translate(canvasOfAnimation,-5,0);
                //It is an interesting behavior on the buffer
//                Transform.rotate(kirby.getPolygon(),90);
            } else if (currentIncrement < 440) {
                //la caida de cuando brinca
                Transform.translate(kirby.getPolygon(),7,+5);
                Transform.translate(canvasOfAnimation,-5,0);
            } else if (currentIncrement>= 550 && currentIncrement < 650) {
//                Transform.translate(kirby.getPolygon(),5,0);
                Transform.translate(canvasOfAnimation,-5,0);
            } else if (currentIncrement>= 720 && currentIncrement < 770) {
                Transform.translate(canvasOfAnimation,-5,0);
            }
            secondsMillis += 100;
            if (secondsMillis % 1000 == 0){
                seconds++;
            }
            repaint();
        }
    }

    public void startAnimation(){
        anim.start();
    }
}
